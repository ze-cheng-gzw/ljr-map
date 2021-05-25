package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("单曲列表 --> 单曲信息")
@Data
public class SingleInfoVO implements Serializable {

    @ApiModelProperty("音乐名称")
    private String musicName;

    @ApiModelProperty("歌手Id")
    private String singerId;

    @ApiModelProperty("歌手名称")
    private String singerName;

    @ApiModelProperty("专辑Id")
    private String albumId;

    @ApiModelProperty("专辑名称")
    private String albumName;

    @ApiModelProperty("音乐时长")
    private String musicTimeLength;

    @ApiModelProperty("歌曲封面链接")
    private String coverUrl;

    @ApiModelProperty("音乐链接")
    private String musicUrl;

    @ApiModelProperty("歌词")
    private String musicLyrics;
}
