package com.demo.dao;

import com.demo.entity.ReplyComments;
import com.demo.vo.ReplyCommentsVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface ReplyCommentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReplyComments record);

    int insertSelective(ReplyComments record);

    ReplyComments selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReplyComments record);

    int updateByPrimaryKey(ReplyComments record);

    /**
     * 拉取回复列表
     * @param commentsId
     * @return
     */
    List<ReplyCommentsVO> findReplyCommentsByCommentsIdFixed(@Param("commentsId") Long commentsId,@Param("fixedNumber") Integer fixedNumber);

    /**
     * 拉取回复列表
     * @param commentsId
     * @param sortingType
     * @return
     */
    List<ReplyCommentsVO> findReplyCommentsByCommentsId(@Param("commentsId") Long commentsId, @Param("sortingType") Integer sortingType);
}