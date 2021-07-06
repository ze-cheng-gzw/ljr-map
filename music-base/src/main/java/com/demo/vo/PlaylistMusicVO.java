package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("专辑音乐列表vo")
@Data
public class PlaylistMusicVO implements Serializable {

    @ApiModelProperty("歌单与音乐的关联id")
    private String id;

    @ApiModelProperty("音乐名称")
    private String musicName;

    @ApiModelProperty("歌手名称")
    private String singerName;

    @ApiModelProperty("音乐时长")
    private String musicTimeLength;

}
