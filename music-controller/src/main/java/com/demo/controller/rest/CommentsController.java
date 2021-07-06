package com.demo.controller.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.common.ApiResponseCode;
import com.demo.common.ApiResponseWrapper;
import com.demo.common.Result;
import com.demo.controller.annotation.Token;
import com.demo.entity.Member;
import com.demo.entity.MemberToken;
import com.demo.interfaceService.CommentsService;
import com.demo.param.*;
import com.demo.util.PageResult;
import com.demo.vo.FindCommentsVO;
import com.demo.vo.ReplyCommentsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "v1", tags = "评论入口")
@RequestMapping("/api/v1")
public class CommentsController {

    @Reference
    private CommentsService commentsService;

    @GetMapping("/comments/findCommentsById")
    @ApiOperation(value = "拉取评论信息", notes = "返回评论列表")
    public Result<PageResult<FindCommentsVO>> findCommentsById (@Valid FindCommentsByIdParam findCommentsByIdParam) {

        PageResult<FindCommentsVO> pageResult = commentsService.findCommentsById(findCommentsByIdParam);

        return ApiResponseWrapper.wrap(pageResult);
    }

    @GetMapping("/comments/findReplyCommentsByCommentsId")
    @ApiOperation(value = "继续拉取回复评论", notes = "返回回复评论")
    public Result<PageResult<ReplyCommentsVO>> findReplyCommentsByCommentsId (@Valid FindReplyCommentsByCommentsIdParam findReplyCommentsByCommentsIdParam) {

        PageResult<ReplyCommentsVO> pageResult = commentsService.findReplyCommentsByCommentsId(findReplyCommentsByCommentsIdParam);

        return ApiResponseWrapper.wrap(pageResult);
    }

    @PostMapping("/comments/firstComments")
    @ApiOperation(value = "添加一级评论", notes = "返回评论结果")
    public Result<Boolean> firstComments (@Token MemberToken memberToken, @RequestBody @Valid FirstCommentsParam firstCommentsParam) {

        boolean result = commentsService.firstComments(memberToken, firstCommentsParam);

        //评论成功
        if (result) {
            return ApiResponseWrapper.wrap(ApiResponseCode.SUCCESS, true, "评论成功");
        }
        //评论失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, false, "评论失败");
    }

    @PostMapping("/comments/replyComments")
    @ApiOperation(value = "添加回复评论", notes = "返回评论结果")
    public Result<Boolean> replyComments (@Token MemberToken memberToken, @RequestBody @Valid ReplyCommentsParam replyCommentsParam) {

        boolean result = commentsService.replyComments(memberToken, replyCommentsParam);

        //评论成功
        if (result) {
            return ApiResponseWrapper.wrap(ApiResponseCode.SUCCESS, true, "评论成功");
        }
        //评论失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, false, "评论失败");
    }

    @PostMapping("/comments/thumbComments")
    @ApiOperation(value = "点赞评论", notes = "返回点赞结果")
    public Result<Boolean> thumbComments (@Token MemberToken memberToken, @RequestBody @Valid ThumbComments thumbComments) {

        boolean result = commentsService.thumbComments(memberToken, thumbComments);

        //点赞成功
        if (result) {
            return ApiResponseWrapper.wrap(ApiResponseCode.SUCCESS, true, "点赞成功");
        }
        //点赞失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, false, "点赞失败");
    }
}
