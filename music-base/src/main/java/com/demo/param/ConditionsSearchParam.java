package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("搜索框搜索参数")
@Data
public class ConditionsSearchParam extends PageParam implements Serializable {

    @ApiModelProperty("类型：0表示直接搜索，1表示歌曲加歌手搜索，2表示歌手搜索，3表示专辑搜索")
    private Integer type;

    @ApiModelProperty("上传内容")
    private String uploadContent;
}
