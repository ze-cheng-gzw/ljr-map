package com.demo.dao;

import com.demo.entity.PlaylistMusic;
import com.demo.vo.PlaylistMusicVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface PlaylistMusicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlaylistMusic record);

    int insertSelective(PlaylistMusic record);

    PlaylistMusic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlaylistMusic record);

    int updateByPrimaryKey(PlaylistMusic record);

    /**
     * 拉取下属歌单音乐数据
     * @param playlistId
     * @return
     */
    List<PlaylistMusicVO> findPlaylistMusicByPlaylistId(@Param("playlistId") Long playlistId);

    /**
     * 删除歌单时同时删除关联数据
     * @param playlistId
     */
    void deleteByPlaylistId(Long playlistId);
}