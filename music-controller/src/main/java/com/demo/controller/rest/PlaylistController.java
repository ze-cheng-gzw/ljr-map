package com.demo.controller.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.common.ApiResponseCode;
import com.demo.common.ApiResponseWrapper;
import com.demo.common.Result;
import com.demo.controller.annotation.Token;
import com.demo.entity.MemberToken;
import com.demo.interfaceService.PlaylistService;
import com.demo.param.CreatePlaylistParam;
import com.demo.param.FindPlaylistByLabelParam;
import com.demo.param.UpdatePlaylistParam;
import com.demo.util.PageResult;
import com.demo.vo.FindPlaylistByIdVO;
import com.demo.vo.FindPlaylistByLabelVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "v1", tags = "歌单入口")
@RequestMapping("/api/v1")
public class PlaylistController {

    @Reference
    private PlaylistService playlistService;

    @PostMapping("/playlist/createPlaylist")
    @ApiOperation(value = "创建歌单", notes = "返回创建结果")
    public Result<String> createPlaylist (@Token MemberToken memberToken, @RequestBody CreatePlaylistParam createPlaylistParam) {

        boolean result = playlistService.createPlaylist(memberToken.getMemberId(), createPlaylistParam);

        //创建成功
        if (result) {
            return ApiResponseWrapper.wrap("创建成功");
        }
        //创建失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "创建失败");
    }

    @PostMapping("/playlist/updatePlaylist")
    @ApiOperation(value = "修改歌单", notes = "返回修改结果")
    public Result<String> updatePlaylist (@Token MemberToken memberToken, @RequestBody UpdatePlaylistParam updatePlaylistParam) {

        boolean result = playlistService.updatePlaylist(memberToken.getMemberId(), updatePlaylistParam);

        //修改成功
        if (result) {
            return ApiResponseWrapper.wrap("修改歌单信息成功");
        }
        //修改失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "修改歌单信息失败");
    }

    @PostMapping("/playlist/deletePlaylist/{playlistId}")
    @ApiOperation(value = "删除歌单", notes = "返回删除结果")
    public Result<String> deletePlaylist (@Token MemberToken memberToken, @PathVariable Long playlistId) {

        boolean result = playlistService.deletePlaylist(memberToken.getMemberId(), playlistId);

        //删除成功
        if (result) {
            return ApiResponseWrapper.wrap("删除歌单信息成功");
        }
        //删除失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "删除歌单信息失败");
    }

    @GetMapping("/playlist/findPlaylistByLabel")
    @ApiOperation(value = "根据标签查询歌单简略列表", notes = "返回歌单简略列表")
    public Result<PageResult<FindPlaylistByLabelVO>> findPlaylistByLabel (FindPlaylistByLabelParam findPlaylistByLabelParam) {

        PageResult<FindPlaylistByLabelVO> pageResult = playlistService.findPlaylistByLabel(findPlaylistByLabelParam);

        return ApiResponseWrapper.wrap(pageResult);
    }

    @GetMapping("/playlist/findPlaylistById")
    @ApiOperation(value = "根据歌单ID查询歌单列表", notes = "返回歌单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的数量", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "playlistId", value = "图片类型 0=职位轮播图 1=发现轮播图", required = true, paramType = "query", dataType = "long")
    })
    public Result<FindPlaylistByIdVO> findPlaylistById (int pageNo, int pageSize, Long playlistId) {

        FindPlaylistByIdVO findPlaylistByIdVO = playlistService.findPlaylistById(pageNo, pageSize, playlistId);

        return ApiResponseWrapper.wrap(findPlaylistByIdVO);
    }
}
