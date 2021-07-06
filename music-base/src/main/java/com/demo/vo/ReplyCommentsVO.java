package com.demo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("回复评论")
@Data
public class ReplyCommentsVO implements Serializable {

    @ApiModelProperty("回复id")
    private Long id;

    @ApiModelProperty("回复者id")
    private Long replyMemberId;

    @ApiModelProperty("回复者名称")
    private String replyMemberName;

    @ApiModelProperty("回复者头像链接")
    private String replyMemberUrl;

    @ApiModelProperty("回复内容")
    private String replyContent;

    @ApiModelProperty("点赞次数")
    private Long thumbUpNumber;

    @ApiModelProperty(notes = "评论时间")
    @JsonFormat(pattern = "MM月dd日 HH:mm", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("回复级别(回复一级评论为，回复回复评论为2)")
    private int replyLevel;

    @ApiModelProperty("被回复者Id")
    private Long byReplyMemberId;

    @ApiModelProperty("被回复者名称")
    private String byReplyMemberName;

    @ApiModelProperty("被回复者评论内容")
    private String byReplyMemberComments;
}
