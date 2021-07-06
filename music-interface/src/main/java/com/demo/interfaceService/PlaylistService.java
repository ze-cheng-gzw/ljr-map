package com.demo.interfaceService;

import com.demo.param.CreatePlaylistParam;
import com.demo.param.FindPlaylistByLabelParam;
import com.demo.param.UpdatePlaylistParam;
import com.demo.util.PageResult;
import com.demo.vo.FindPlaylistByIdVO;
import com.demo.vo.FindPlaylistByLabelVO;

/**
 * 歌单接口
 */
public interface PlaylistService {

    /**
     * 创建歌单
     * @param memberId
     * @param createPlaylistParam
     * @return
     */
    boolean createPlaylist(Long memberId, CreatePlaylistParam createPlaylistParam);

    /**
     * 修改歌单
     * @param memberId
     * @param updatePlaylistParam
     * @return
     */
    boolean updatePlaylist(Long memberId, UpdatePlaylistParam updatePlaylistParam);

    /**
     * 删除歌单
     * @param memberId
     * @param playlistId
     * @return
     */
    boolean deletePlaylist(Long memberId, Long playlistId);

    /**
     * 根据标签查询歌单列表
     * @param findPlaylistByLabelParam
     * @return
     */
    PageResult<FindPlaylistByLabelVO> findPlaylistByLabel(FindPlaylistByLabelParam findPlaylistByLabelParam);

    /**
     * 根据歌单ID查询歌单列表
     * @param playlistId
     * @return
     */
    FindPlaylistByIdVO findPlaylistById(int pageNo, int pageSize, Long playlistId);
}
