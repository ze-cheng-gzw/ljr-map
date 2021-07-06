package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("歌单添加音乐上传字段")
@Data
public class AddMusicParam implements Serializable {

    @ApiModelProperty("歌单id")
    private Long playlistId;

    @ApiModelProperty("音乐Id")
    private Long musicId;

    @ApiModelProperty("音乐名称")
    private String musicName;

    @ApiModelProperty("歌手名称")
    private String singerName;

    @ApiModelProperty("音乐时长")
    private String musicTimeLength;
}
