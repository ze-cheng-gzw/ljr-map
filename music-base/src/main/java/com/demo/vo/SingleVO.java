package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("搜索框 --> 单曲信息")
@Data
public class SingleVO implements Serializable {

    @ApiModelProperty("音乐名称")
    private String musicName;

    @ApiModelProperty("歌手名称")
    private String singerName;
}
