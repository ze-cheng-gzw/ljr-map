package com.demo.interfaceService;

import com.demo.param.AddMusicParam;

public interface PlaylistMusicService {

    /**
     * 歌单添加音乐
     * @param memberId
     * @param addMusicParam
     * @return
     */
    boolean addMusic(Long memberId, AddMusicParam addMusicParam);

    /**
     * 歌单删除音乐
     * @param id
     * @return
     */
    boolean delMusic(Long id);
}
