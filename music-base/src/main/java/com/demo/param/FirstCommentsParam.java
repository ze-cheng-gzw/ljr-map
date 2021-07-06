package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("一级评论Param")
@Data
public class FirstCommentsParam implements Serializable {

    @ApiModelProperty("音乐或者歌单id")
    private Long articleId;

    @ApiModelProperty("类型：0表示音乐，1表示歌单")
    private int articleType;

    @ApiModelProperty("评论内容")
    private String commentsContent;
}
