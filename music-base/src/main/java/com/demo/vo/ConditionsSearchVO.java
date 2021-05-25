package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("搜索框搜索结果返回对象")
@Data
public class ConditionsSearchVO implements Serializable {

    @ApiModelProperty("返回的单曲集合")
    private List<SingleInfoVO> singleInfoVOList;

    @ApiModelProperty("音乐总数")
    private Long totalNumber;
}
