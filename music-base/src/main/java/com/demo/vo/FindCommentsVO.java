package com.demo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("一级评论")
@Data
public class FindCommentsVO implements Serializable {

    @ApiModelProperty("评论id")
    private Long commentsId;

    @ApiModelProperty("评论者id")
    private Long memberId;

    @ApiModelProperty("评论者名称")
    private String memberName;

    @ApiModelProperty("评论者头像")
    private String memberUrl;

    @ApiModelProperty("评论内容")
    private String commentsContent;

    @ApiModelProperty("点赞数量")
    private Long thumbUpNumber;

    @ApiModelProperty(notes = "评论时间")
    @JsonFormat(pattern = "MM月dd日 HH:mm", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("子回复总数量")
    private Long replyNumber;

    @ApiModelProperty("一级评论下的子回复集合")
    private List<ReplyCommentsVO> replyCommentsVOList;
}
