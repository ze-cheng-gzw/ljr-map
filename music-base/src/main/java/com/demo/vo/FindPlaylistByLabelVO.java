package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("歌单简介VO")
@Data
public class FindPlaylistByLabelVO implements Serializable {

    @ApiModelProperty("歌单Id")
    private Long playlistId;

    @ApiModelProperty("歌单标题")
    private String playlistTitle;

    @ApiModelProperty("歌单封面")
    private String playlistCover;

    @ApiModelProperty("播放量")
    private String amountOfPlay;
}
