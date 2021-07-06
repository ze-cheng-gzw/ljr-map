package com.demo.dao;

import com.demo.entity.Album;
import com.demo.vo.AlbumInfoVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface AlbumMapper {
    int deleteByPrimaryKey(Long albumId);

    int insert(Album record);

    int insertSelective(Album record);

    Album selectByPrimaryKey(Long albumId);

    int updateByPrimaryKeySelective(Album record);

    int updateByPrimaryKey(Album record);

    /**
     * 获取专辑信息
     * @param albumId
     * @return
     */
    AlbumInfoVO getAlbumInfoVOByAlbumId(@Param("albumId") String albumId);

    /**
     * 批量添加专辑信息
     * @param albums
     */
    void addAlbumList(@Param("albums") List<Album> albums);
}