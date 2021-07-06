package com.demo.dao;

import com.demo.entity.Playlist;
import com.demo.vo.FindPlaylistByIdVO;
import io.lettuce.core.dynamic.annotation.Param;

public interface PlaylistMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Playlist record);

    int insertSelective(Playlist record);

    Playlist selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Playlist record);

    int updateByPrimaryKey(Playlist record);

    /**
     * 根据歌单Id查询相应信息
     * @param playlistId
     * @return
     */
    FindPlaylistByIdVO findPlaylistById(@Param("playlistId") Long playlistId);
}