package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("搜索框 --> 专辑信息")
@Data
public class AlbumVO implements Serializable {

    @ApiModelProperty("专辑名称")
    private String albumName;

    @ApiModelProperty("专辑封面")
    private String albumImg;

    @ApiModelProperty("专辑歌手")
    private String singerName;
}
