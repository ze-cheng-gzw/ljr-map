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

    @ApiModelProperty("歌手信息对象")
    private SingerInfoVO singerInfoVO;

    @ApiModelProperty("专辑信息对象")
    private AlbumInfoVO albumInfoVO;

    @ApiModelProperty("专辑简略信息")
    private List<AlbumInfoBriefVO> albumInfoBriefVOList;

    @ApiModelProperty("专辑总数")
    private Long albumTotalNumber;
}
