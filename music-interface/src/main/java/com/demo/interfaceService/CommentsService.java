package com.demo.interfaceService;

import com.demo.entity.MemberToken;
import com.demo.param.*;
import com.demo.util.PageResult;
import com.demo.vo.FindCommentsVO;
import com.demo.vo.ReplyCommentsVO;

public interface CommentsService {

    /**
     * 拉取评论信息
     * @param findCommentsByIdParam
     * @return
     */
    PageResult<FindCommentsVO> findCommentsById(FindCommentsByIdParam findCommentsByIdParam);

    /**
     * 继续拉取回复评论
     * @param findReplyCommentsByCommentsIdParam
     * @return
     */
    PageResult<ReplyCommentsVO> findReplyCommentsByCommentsId(FindReplyCommentsByCommentsIdParam findReplyCommentsByCommentsIdParam);

    /**
     * 添加一级评论
     * @param memberToken
     * @param firstCommentsParam
     * @return
     */
    boolean firstComments(MemberToken memberToken, FirstCommentsParam firstCommentsParam);

    /**
     * 添加回复评论
     * @param memberToken
     * @param replyCommentsParam
     * @return
     */
    boolean replyComments(MemberToken memberToken, ReplyCommentsParam replyCommentsParam);

    /**
     * 点赞评论
     * @param memberToken
     * @param thumbComments
     * @return
     */
    boolean thumbComments(MemberToken memberToken, ThumbComments thumbComments);
}
