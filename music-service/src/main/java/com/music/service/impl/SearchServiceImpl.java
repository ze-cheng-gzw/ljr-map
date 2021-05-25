package com.music.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.common.BizException;
import com.demo.dao.MemberMapper;
import com.demo.entity.Member;
import com.demo.interfaceService.SearchService;
import com.demo.param.ConditionsSearchParam;
import com.demo.vo.*;
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

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service(accesslog = "searchService")
public class SearchServiceImpl implements SearchService {

    @Resource
    private MemberMapper memberMapper;

    //第二种连接方法与在ymi配置一样
    public JestClient getJestCline() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://127.0.0.1:9200")
                .multiThreaded(true)
                .build());
        return factory.getObject();
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
    public SearchBoxChangeVO conditionsSearch(ConditionsSearchParam conditionsSearchParam) {
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
            boolQueryBuilder.filter(QueryBuilders.termQuery("singerName", conditionsSearchParam.getUploadContent()));
        } else if (conditionsSearchParam.getType() == 3) { //表示专辑的筛选
            boolQueryBuilder.filter(QueryBuilders.termQuery("albumName", conditionsSearchParam.getUploadContent()));
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
            conditionsSearchVO.setTotalNumber(searchResult.getTotal());
        } catch (IOException e) {
            BizException.error("IO异常");
        }
        return null;
    }

    //获取歌手的信息
    public void getSingleInfo (String singerId) {

    }
}
