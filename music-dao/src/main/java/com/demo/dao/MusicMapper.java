package com.demo.dao;

import com.demo.entity.Music;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface MusicMapper {
    int deleteByPrimaryKey(Long musicId);

    int insert(Music record);

    int insertSelective(Music record);

    Music selectByPrimaryKey(Long musicId);

    int updateByPrimaryKeySelective(Music record);

    int updateByPrimaryKey(Music record);

    /**
     * 批量添加音乐
     * @param musics
     */
    void addMusicList(@Param("musics") List<Music> musics);
}