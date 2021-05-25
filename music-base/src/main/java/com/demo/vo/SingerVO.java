package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("搜索框 --> 歌手信息")
@Data
public class SingerVO implements Serializable {

    @ApiModelProperty("歌手名称")
    private String singerName;

    @ApiModelProperty("歌手图片")
    private String singerImg;
}
