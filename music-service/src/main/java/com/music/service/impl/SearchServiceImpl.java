package com.music.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.BizException;
import com.demo.dao.AlbumMapper;
import com.demo.dao.MemberMapper;
import com.demo.dao.SingerMapper;
import com.demo.entity.Member;
import com.demo.interfaceService.SearchService;
import com.demo.param.ConditionsSearchParam;
import com.demo.vo.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service(accesslog = "searchService")
public class SearchServiceImpl implements SearchService {

    private static final String SINGER_INFO_PREFIX = "singer:"; //歌手存放在redis的前缀

    private static final String SINGER_INFO_SUFFIX = ":info"; //歌手存放在redis的前缀

    private static final String SINGER_INFO_LOCK = ":lock"; //歌手存放在redis的锁

    private static final String ALBUM_INFO_PREFIX = "album:"; //专辑存放在redis的前缀

    private static final String ALBUM_INFO_SUFFIX = ":info"; //专辑存放在redis的前缀

    private static final String ALBUM_INFO_LOCK = ":lock"; //专辑存放在redis的锁

    private static Long albumTotalNumber; //专辑的总数

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private RedisTemplate redisCacheTemplate;

    @Resource
    private AlbumMapper albumMapper;

    //第二种连接方法与在ymi配置一样
    public JestClient getJestCline() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://127.0.0.1:9200")
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }

    //配置参考redisSon
    public Config getRedisSonConfig() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return config;
    }


    public Member findMemberById() {
        return memberMapper.selectByPrimaryKey(1L);
    }

    public SearchBoxChangeVO searchBoxChange(String searchString) {

        SearchBoxChangeVO searchBoxChangeVO = new SearchBoxChangeVO();
        //参考kibana写出来的语句，一样一样的去塞     -----  获取的是单曲信息
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //通过query得到bool
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //根据上传值查询，加and表示该字段必须包含上传得所有值
        boolQueryBuilder.must(QueryBuilders.matchQuery("musicName", searchString).operator(Operator.AND));
        //将拼接得query放入
        searchSourceBuilder.query(boolQueryBuilder);

        //es的分页，与query平级
        searchSourceBuilder.from(0);   //表示起始行（从0开始）
        searchSourceBuilder.size(4);   //表示每页的数量

        //创建访问具体的index
        Search.Builder searchBuilder = new Search.Builder(searchSourceBuilder.toString());
        Search search = searchBuilder.addIndex("music_index").build();

        List<SingleVO> singleVOS = new ArrayList<SingleVO>();
        try {
            SearchResult searchResult = getJestCline().execute(search);
            List<SearchResult.Hit<SingleVO, Void>> hits = searchResult.getHits(SingleVO.class);
            for (SearchResult.Hit<SingleVO, Void> hit : hits) {
                singleVOS.add(hit.source);
            }
        } catch (IOException e) {
            BizException.error("连接es异常");
        }
        searchBoxChangeVO.setSingleVOList(singleVOS);

        // -----  获取的是歌手信息
        SearchSourceBuilder searchSourceBuilderSinger = new SearchSourceBuilder();
        //去除重复值
        CollapseBuilder collapseBuilderSinger = new CollapseBuilder("singerId");
        searchSourceBuilderSinger.collapse(collapseBuilderSinger);

        BoolQueryBuilder boolQueryBuilderSinger = QueryBuilders.boolQuery();
        //根据上传值查询
        boolQueryBuilderSinger.must(QueryBuilders.matchQuery("singerName", searchString).operator(Operator.AND));
        //将拼好的查询放入search中
        searchSourceBuilderSinger.query(boolQueryBuilderSinger);

        //es的分页，与query平级
        searchSourceBuilderSinger.from(0);   //表示起始行（从0开始）
        searchSourceBuilderSinger.size(2);   //表示每页的数量

        //创建访问具体的index
        Search.Builder searchBuilderSinger = new Search.Builder(searchSourceBuilderSinger.toString());
        Search searchSinger = searchBuilderSinger.addIndex("music_index").build();

        List<SingerVO> singerVOS = new ArrayList<SingerVO>();
        try {
            SearchResult searchResult = getJestCline().execute(searchSinger);
            List<SearchResult.Hit<SingerVO, Void>> hits = searchResult.getHits(SingerVO.class);
            for (SearchResult.Hit<SingerVO, Void> hit : hits) {
                singerVOS.add(hit.source);
            }
        } catch (IOException e) {
            BizException.error("连接es异常");
        }
        searchBoxChangeVO.setSingerVOList(singerVOS);

        // -----  获取的是专辑信息
        SearchSourceBuilder searchSourceBuilderAlbum = new SearchSourceBuilder();
        //去除重复值
        CollapseBuilder collapseBuilderAlbum = new CollapseBuilder("albumId");
        searchSourceBuilderAlbum.collapse(collapseBuilderAlbum);

        //查询封装
        BoolQueryBuilder boolQueryBuilderAlbum = QueryBuilders.boolQuery();
        //根据上传值查询,加了and表示该字段必须包含上传的所有值
        boolQueryBuilderAlbum.must(QueryBuilders.matchQuery("albumName", searchString).operator(Operator.AND));
        searchSourceBuilderAlbum.query(boolQueryBuilderAlbum);

        //es的分页，与query平级
        searchSourceBuilderAlbum.from(0);   //表示起始行（从0开始）
        searchSourceBuilderAlbum.size(2);   //表示每页的数量

        //创建访问具体的index
        Search.Builder searchBuilderAlbum = new Search.Builder(searchSourceBuilderAlbum.toString());
        Search searchAlbum = searchBuilderAlbum.addIndex("music_index").build();

        List<AlbumVO> albumVOS = new ArrayList<AlbumVO>();
        try {
            SearchResult searchResult = getJestCline().execute(searchAlbum);
            List<SearchResult.Hit<AlbumVO, Void>> hits = searchResult.getHits(AlbumVO.class);
            for (SearchResult.Hit<AlbumVO, Void> hit : hits) {
                albumVOS.add(hit.source);
            }
        } catch (IOException e) {
            BizException.error("连接es异常");
        }
        searchBoxChangeVO.setAlbumVOList(albumVOS);

        return searchBoxChangeVO;
    }

    //搜索框搜索
    public ConditionsSearchVO conditionsSearch(ConditionsSearchParam conditionsSearchParam) {
        ConditionsSearchVO conditionsSearchVO = new ConditionsSearchVO();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //通过query得到bool
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (conditionsSearchParam.getType() == 0) { //表示直接搜索，只要歌曲名称、歌手名称、专辑名称含有上传值即可
            //此表示or
            boolQueryBuilder.should(QueryBuilders.termQuery("musicName", conditionsSearchParam.getUploadContent()));
            boolQueryBuilder.should(QueryBuilders.termQuery("singerName", conditionsSearchParam.getUploadContent()));
            boolQueryBuilder.should(QueryBuilders.termQuery("albumName", conditionsSearchParam.getUploadContent()));
        } else if (conditionsSearchParam.getType() == 1) { //表示的是歌曲与歌手的共同查询,满足期中一个条件即可
            String[] strings = conditionsSearchParam.getUploadContent().split(" ");
            boolQueryBuilder.should(QueryBuilders.termQuery("musicName", strings[0]));
            boolQueryBuilder.should(QueryBuilders.termQuery("singerName", strings[1]));
        } else if (conditionsSearchParam.getType() == 2) { //表示的是歌手筛选
            boolQueryBuilder.must(QueryBuilders.matchQuery("singerName", conditionsSearchParam.getUploadContent()).operator(Operator.AND));
        } else if (conditionsSearchParam.getType() == 3) { //表示专辑的筛选
            boolQueryBuilder.must(QueryBuilders.matchQuery("albumName", conditionsSearchParam.getUploadContent()).operator(Operator.AND));
        }

        searchSourceBuilder.query(boolQueryBuilder);

        //es的分页，与query平级
        searchSourceBuilder.from((conditionsSearchParam.getPageNo() - 1) * conditionsSearchParam.getPageSize());   //表示起始行（从0开始）
        searchSourceBuilder.size(conditionsSearchParam.getPageSize());   //表示每页的数量

        //创建访问具体的index
        Search.Builder searchBuilder = new Search.Builder(searchSourceBuilder.toString());
        Search search = searchBuilder.addIndex("music_index").build();

        try {
            SearchResult searchResult = getJestCline().execute(search);
            List<SearchResult.Hit<SingleInfoVO, Void>> hits = searchResult.getHits(SingleInfoVO.class);
            List<SingleInfoVO> singleInfoVOS = new ArrayList<>();
            for (SearchResult.Hit<SingleInfoVO, Void> hit:hits) {
                singleInfoVOS.add(hit.source);
            }
            conditionsSearchVO.setSingleInfoVOList(singleInfoVOS);
            JsonObject jsonObject = searchResult.getJsonObject();
            JsonElement jsonElement = jsonObject.get("hits").getAsJsonObject().get("total").getAsJsonObject().get("value");
            conditionsSearchVO.setTotalNumber(jsonElement.getAsLong());
        } catch (IOException e) {
            BizException.error("IO异常");
        }

        if (conditionsSearchParam.getType() == 2) {

            //拉取歌手信息进行填入
            SingerInfoVO singerInfoVO = this.getSingerInfo(conditionsSearchVO.getSingleInfoVOList().get(0).getSingerId());
            conditionsSearchVO.setSingerInfoVO(singerInfoVO);
            conditionsSearchVO.setAlbumInfoBriefVOList(this.getAlbumBySingerId(conditionsSearchVO.getSingleInfoVOList().get(0).getSingerId(), 1, 10));
            conditionsSearchVO.setAlbumTotalNumber(albumTotalNumber);
        }

        if (conditionsSearchParam.getType() == 3) {
            //拉取专辑信息进行填入
            AlbumInfoVO albumInfoVO = this.getAlbumInfoVO(conditionsSearchVO.getSingleInfoVOList().get(0).getAlbumId());
            conditionsSearchVO.setAlbumInfoVO(albumInfoVO);
        }
        return conditionsSearchVO;
    }

    //获取歌手的信息
    public SingerInfoVO getSingerInfo (String singerId) {
        //配置使用方法
        ValueOperations<Serializable, Object> operations = redisCacheTemplate.opsForValue();
        SingerInfoVO singerInfoVO = null;
        String key = SINGER_INFO_PREFIX + singerId + SINGER_INFO_SUFFIX;
        if (redisCacheTemplate.hasKey(key)) { //存在时,直接从缓存拉取
            String result = operations.get(key).toString();
            singerInfoVO = JSONObject.parseObject(result, SingerInfoVO.class);
        } else {
            //配置参考redisSon
            RedissonClient redisSon = Redisson.create(getRedisSonConfig());
            RLock lock = redisSon.getLock(SINGER_INFO_PREFIX + singerId + SINGER_INFO_LOCK);
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = false;
            try {
                res = lock.tryLock(100, 10, TimeUnit.SECONDS);
            } catch (Exception e) {
                BizException.fail("调用redisSon失败");
            } finally {
                lock.unlock();
            }

            if (redisCacheTemplate.hasKey(key)) { //在拉取一次，避免其他已经拉取的情况
                String result = operations.get(key).toString();
                singerInfoVO = JSONObject.parseObject(result, SingerInfoVO.class);
            }

            //去数据库拉取
            if (res) {
                singerInfoVO = singerMapper.getSingerInfoBySingerId(singerId);
                if (singerInfoVO != null) {
                    //存入redis，1天
                    operations.set(key, JSONObject.toJSONString(singerInfoVO), 1, TimeUnit.DAYS);
                }
            } else {
                BizException.fail("加锁失败");
            }
        }
        return singerInfoVO;
    }

    //获取专辑信息
    public AlbumInfoVO getAlbumInfoVO (String albumId) {
        AlbumInfoVO albumInfoVO = null;
        ValueOperations<Serializable, Object> operations = redisCacheTemplate.opsForValue();
        String key = ALBUM_INFO_PREFIX + albumId + ALBUM_INFO_SUFFIX;
        if (redisCacheTemplate.hasKey(key)) { //存在时直接拉取
            String result = operations.get(key).toString();
            albumInfoVO = JSONObject.parseObject(result, AlbumInfoVO.class);
        } else {
            RedissonClient redisSon = Redisson.create(getRedisSonConfig());
            RLock lock = redisSon.getLock(ALBUM_INFO_PREFIX + albumId + ALBUM_INFO_LOCK);
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = false;
            try {
                res = lock.tryLock(100, 10, TimeUnit.SECONDS);
            } catch (Exception e) {
                BizException.fail("调用redisSon失败");
            } finally {
                lock.unlock();
            }

            if (redisCacheTemplate.hasKey(key)) { //在拉取一次，避免其他已经拉取的情况
                String result = operations.get(key).toString();
                albumInfoVO = JSONObject.parseObject(result, AlbumInfoVO.class);
            }

            //去数据库拉取
            if (res) {
                albumInfoVO = albumMapper.getAlbumInfoVOByAlbumId(albumId);
                if (albumInfoVO != null) {
                    operations.set(key, JSONObject.toJSONString(albumInfoVO), 1, TimeUnit.DAYS);
                }
            } else {
                BizException.fail("加锁失败");
            }
        }
        return albumInfoVO;
    }

    //前往es拉取个人专辑总数和专辑分页数据
    public List<AlbumInfoBriefVO> getAlbumBySingerId (String singerId, Integer pageNo, Integer size) {
        List<AlbumInfoBriefVO> albumInfoBriefVOList = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //根据专辑Id去除重复
        CollapseBuilder collapseBuilder = new CollapseBuilder("albumId");
        searchSourceBuilder.collapse(collapseBuilder);
        //通过query得到bool
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.termQuery("singerId", singerId));
        searchSourceBuilder.query(boolQueryBuilder);

        searchSourceBuilder.from((pageNo - 1) * size);
        searchSourceBuilder.size(size);

        //创建访问具体的index
        Search.Builder searchBuilder = new Search.Builder(searchSourceBuilder.toString());
        Search search = searchBuilder.addIndex("music_index").build();

        try {
            SearchResult searchResult = getJestCline().execute(search);
            List<SearchResult.Hit<AlbumInfoBriefVO, Void>> hits = searchResult.getHits(AlbumInfoBriefVO.class);
            for (SearchResult.Hit<AlbumInfoBriefVO, Void> hit:hits) {
                albumInfoBriefVOList.add(hit.source);
            }
            JsonObject jsonObject = searchResult.getJsonObject();
            JsonElement jsonElement = jsonObject.get("_shards").getAsJsonObject().get("total");
            SearchServiceImpl.albumTotalNumber = jsonElement.getAsLong();
        } catch (IOException e) {
            BizException.error("es链接异常");
        }
        return  albumInfoBriefVOList;
    }
}
