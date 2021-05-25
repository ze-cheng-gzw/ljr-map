package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("搜索框返回信息VO")
@Data
public class SearchBoxChangeVO implements Serializable {

    @ApiModelProperty("歌曲信息集合")
    private List<SingleVO> singleVOList;

    @ApiModelProperty("专辑信息集合")
    private List<AlbumVO> albumVOList;

    @ApiModelProperty("歌手信息集合")
    private List<SingerVO> singerVOList;
}
