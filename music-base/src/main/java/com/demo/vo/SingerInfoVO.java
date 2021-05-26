package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("歌手信息")
@Data
public class SingerInfoVO implements Serializable {

    @ApiModelProperty("歌手信息")
    private String singerName;

    @ApiModelProperty("歌手图片")
    private String singerImg;

    @ApiModelProperty("国籍")
    private String nationality;

    @ApiModelProperty("职业")
    private String professional;

    @ApiModelProperty("代表作")
    private String magnumOpus;

    @ApiModelProperty("个人介绍")
    private String personalIntroduce;

    @ApiModelProperty("从艺生涯")
    private String soloCareer;
}
