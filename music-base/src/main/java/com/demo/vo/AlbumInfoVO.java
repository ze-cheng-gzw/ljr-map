package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("专辑信息")
@Data
public class AlbumInfoVO implements Serializable {

    @ApiModelProperty("专辑名称")
    private String albumName;

    @ApiModelProperty("发行时间")
    private String releaseTime;

    @ApiModelProperty("专辑图片")
    private String albumImg;

    @ApiModelProperty("专辑简介")
    private String albumIntroduction;
}
