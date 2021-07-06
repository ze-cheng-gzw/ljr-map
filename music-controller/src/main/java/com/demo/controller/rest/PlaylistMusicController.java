package com.demo.controller.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.common.ApiResponseCode;
import com.demo.common.ApiResponseWrapper;
import com.demo.common.Result;
import com.demo.controller.annotation.Token;
import com.demo.entity.MemberToken;
import com.demo.interfaceService.PlaylistMusicService;
import com.demo.param.AddMusicParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "v1", tags = "歌单与音乐关联相关接口")
@RequestMapping("/api/v1")
public class PlaylistMusicController {

    @Reference
    private PlaylistMusicService playlistMusicService;

    @PostMapping("/playlistMusic/addMusic")
    @ApiOperation(value = "歌单添加音乐", notes = "返回添加结果")
    public Result<Boolean> addMusic (@Token MemberToken memberToken, AddMusicParam addMusicParam) {
        boolean result = playlistMusicService.addMusic(memberToken.getMemberId(), addMusicParam);

        //添加成功
        if (result) {
            return ApiResponseWrapper.wrap(ApiResponseCode.SUCCESS, true, "添加成功");
        }
        //添加失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, false, "添加失败");
    }

    @PostMapping("/playlistMusic/delMusic/{id}")
    @ApiOperation(value = "歌单删除音乐", notes = "返回删除结果")
    public Result<Boolean> delMusic (@Token MemberToken memberToken, @PathVariable Long id) {
        boolean result = playlistMusicService.delMusic(id);

        //删除成功
        if (result) {
            return ApiResponseWrapper.wrap(ApiResponseCode.SUCCESS, true, "删除成功");
        }
        //删除失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, false, "删除失败");
    }
}
