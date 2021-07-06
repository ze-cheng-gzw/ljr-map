package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel("回复评论的param")
@Data
public class ReplyCommentsParam implements Serializable {

    @ApiModelProperty("评论Id")
    @NotEmpty(message = "评论Id")
    private Long commentsId;

    @ApiModelProperty("回复评论")
    @NotEmpty(message = "回复评论不允许为空")
    private String replyComments;

    @ApiModelProperty("被回复者Id")
    @NotEmpty(message = "被回复者Id不允许为空")
    private Long byReplyMemberId;

    @ApiModelProperty("被回复者名称")
    @NotEmpty(message = "被回复者名称不允许为空")
    private String byReplyMemberName;

    @ApiModelProperty("被回复者评论内容")
    @NotEmpty(message = "被回复者评论内容不允许为空")
    private String byReplyMemberComments;
}
