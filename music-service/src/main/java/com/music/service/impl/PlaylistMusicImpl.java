package com.music.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.dao.PlaylistMusicMapper;
import com.demo.entity.PlaylistMusic;
import com.demo.interfaceService.PlaylistMusicService;
import com.demo.param.AddMusicParam;

import javax.annotation.Resource;

@Service
public class PlaylistMusicImpl implements PlaylistMusicService {

    @Resource
    private PlaylistMusicMapper playlistMusicMapper;

    //歌单添加音乐
    public boolean addMusic(Long memberId, AddMusicParam addMusicParam) {
        PlaylistMusic playlistMusic = new PlaylistMusic ();
        playlistMusic.setPlaylistId(addMusicParam.getPlaylistId());
        playlistMusic.setMusicId(addMusicParam.getMusicId());
        playlistMusic.setMusicName(addMusicParam.getMusicName());
        playlistMusic.setSingerName(addMusicParam.getSingerName());
        playlistMusic.setMusicTimeLength(addMusicParam.getMusicTimeLength());
        return playlistMusicMapper.insertSelective(playlistMusic) == 1;
    }

    //歌单删除音乐
    public boolean delMusic(Long id) {
        return playlistMusicMapper.deleteByPrimaryKey(id) == 1;
    }
}
