package com.music.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.BizException;
import com.demo.dao.PlaylistMapper;
import com.demo.dao.PlaylistMusicMapper;
import com.demo.entity.Playlist;
import com.demo.interfaceService.PlaylistService;
import com.demo.param.CreatePlaylistParam;
import com.demo.param.FindPlaylistByLabelParam;
import com.demo.param.UpdatePlaylistParam;
import com.demo.util.ESUtil;
import com.demo.util.PageResult;
import com.demo.util.RedisSonUtil;
import com.demo.vo.FindPlaylistByIdVO;
import com.demo.vo.FindPlaylistByLabelVO;
import com.demo.vo.PlaylistMusicVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 歌单实现类
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Resource
    private PlaylistMapper playlistMapper;

    @Resource
    private RedisTemplate redisCacheTemplate;

    @Resource
    private PlaylistMusicMapper playlistMusicMapper;

    //创建歌单
    public boolean createPlaylist(Long memberId, CreatePlaylistParam createPlaylistParam) {

        Playlist playlist = new Playlist();
        playlist.setPlaylistTitle(createPlaylistParam.getPlaylistTitle());
        playlist.setPlaylistCover("");  //封面，设置默认图片
        playlist.setMemberId(memberId);

        return playlistMapper.insertSelective(playlist) == 1;
    }

    //修改歌单
    public boolean updatePlaylist(Long memberId, UpdatePlaylistParam updatePlaylistParam) {
        Playlist playlist = new Playlist();
        playlist.setId(updatePlaylistParam.getPlaylistId());

        //标题
        if (updatePlaylistParam.getPlaylistTitle() != null && "".equals(updatePlaylistParam.getPlaylistTitle().trim())) {
            playlist.setPlaylistTitle(updatePlaylistParam.getPlaylistTitle());
        }

        //封面
        if (updatePlaylistParam.getPlaylistCover() != null && "".equals(updatePlaylistParam.getPlaylistCover().trim())) {
            playlist.setPlaylistCover(updatePlaylistParam.getPlaylistCover());
        }

        //标签
        if (updatePlaylistParam.getPlaylistLabel() != null && "".equals(updatePlaylistParam.getPlaylistLabel().trim())) {
            playlist.setPlaylistLabel(updatePlaylistParam.getPlaylistLabel());
        }

        return playlistMapper.updateByPrimaryKeySelective(playlist) == 1;
    }

    //删除歌单
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePlaylist(Long memberId, Long playlistId) {
        Playlist playlist = playlistMapper.selectByPrimaryKey(playlistId);
        if (!playlist.getMemberId().toString().equals(memberId.toString())) {
            BizException.fail("非法删除，你不是该歌单的主人");
        }
        //同时删除歌单下的关联数据
        playlistMusicMapper.deleteByPlaylistId(playlistId);
        return playlistMapper.deleteByPrimaryKey(playlistId) == 1;
    }

    //根据标签查询歌单列表
    public PageResult<FindPlaylistByLabelVO> findPlaylistByLabel(FindPlaylistByLabelParam findPlaylistByLabelParam) {

        int totalCount = 0;

        //参考kibana写出来的语句，一样一样的去塞     -----  获取的是单曲信息
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //通过query得到bool
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //根据上传值查询，加and表示该字段必须包含上传得所有值
        boolQueryBuilder.must(QueryBuilders.matchQuery(ESUtil.PLAYLIST_TITLE_NAME, findPlaylistByLabelParam.getLabelName()).operator(Operator.AND));
        //将拼接得query放入
        searchSourceBuilder.query(boolQueryBuilder);

        //es的分页，与query平级
        searchSourceBuilder.from((findPlaylistByLabelParam.getPageNo() - 1) * findPlaylistByLabelParam.getPageSize());   //表示起始行（从0开始）
        searchSourceBuilder.size(findPlaylistByLabelParam.getPageSize());   //表示每页的数量

        //创建访问具体的index
        Search.Builder searchBuilder = new Search.Builder(searchSourceBuilder.toString());
        Search search = searchBuilder.addIndex(ESUtil.PLAYLIST_INDEX).build();

        List<FindPlaylistByLabelVO> findPlaylistByLabelVOS = null;
        try {
            SearchResult searchResult = ESUtil.getJestCline().execute(search);
            List<SearchResult.Hit<FindPlaylistByLabelVO, Void>> hits = searchResult.getHits(FindPlaylistByLabelVO.class);
            for (SearchResult.Hit<FindPlaylistByLabelVO, Void> hit : hits) {
                findPlaylistByLabelVOS.add(hit.source);
            }
            JsonObject jsonObject = searchResult.getJsonObject();
            JsonElement jsonElement = jsonObject.get("_shards").getAsJsonObject().get("total");
            totalCount = jsonElement.getAsInt();
        } catch (IOException e) {
            BizException.error("连接es异常");
        }

        PageResult<FindPlaylistByLabelVO> pageResult = new PageResult(findPlaylistByLabelVOS, totalCount, findPlaylistByLabelParam.getPageSize(), findPlaylistByLabelParam.getPageNo());

        return pageResult;
    }

    //根据歌单ID查询歌单列表
    public FindPlaylistByIdVO findPlaylistById(int pageNo, int pageSize, Long playlistId) {

        FindPlaylistByIdVO findPlaylistByIdVO = null;
        ValueOperations<Serializable, Object> operations = redisCacheTemplate.opsForValue();
        String key = RedisSonUtil.PLAYLIST_INFO_PREFIX + playlistId + RedisSonUtil.PLAYLIST_INFO_SUFFIX;

        if (redisCacheTemplate.hasKey(key)) { //存在时直接拉取
            String result = operations.get(key).toString();
            findPlaylistByIdVO = JSONObject.parseObject(result, FindPlaylistByIdVO.class);
        } else {
            RedissonClient redisSon = Redisson.create(RedisSonUtil.getRedisSonConfig());
            RLock lock = redisSon.getLock(RedisSonUtil.PLAYLIST_INFO_PREFIX + playlistId + RedisSonUtil.PLAYLIST_INFO_LOCK);
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
                findPlaylistByIdVO = JSONObject.parseObject(result, FindPlaylistByIdVO.class);
            }

            //去数据库拉取
            if (res) {
                findPlaylistByIdVO = playlistMapper.findPlaylistById(playlistId);
                if (findPlaylistByIdVO != null) {
                    //redis保存时间为1天
                    operations.set(key, JSONObject.toJSONString(findPlaylistByIdVO), 1, TimeUnit.DAYS);
                }
            } else {
                BizException.fail("加锁失败");
            }
        }

        if (pageNo != -1) {
            PageHelper.startPage(pageNo, pageSize);
        }

        //拉取下属歌单音乐数据
        List<PlaylistMusicVO> playlistMusicVOS = playlistMusicMapper.findPlaylistMusicByPlaylistId(playlistId);
        PageInfo pageInfo = new PageInfo(playlistMusicVOS);
        if(pageInfo.getPageNum() < pageNo){
            pageInfo.setList(new ArrayList());
        }
        PageResult<PlaylistMusicVO> playlistMusicVOPageResult = new PageResult<PlaylistMusicVO>(pageInfo.getList(), new Long(pageInfo.getTotal()).intValue(), pageInfo.getPageSize(), pageNo);
        findPlaylistByIdVO.setPlaylistMusicVOPageResult(playlistMusicVOPageResult);

        return findPlaylistByIdVO;
    }
}
