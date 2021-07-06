package com.demo.vo;

import com.demo.entity.Album;
import com.demo.entity.Music;
import com.demo.entity.Singer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("读取的excel信息")
@Data
public class ReadExcelVO implements Serializable {

    @ApiModelProperty("音乐信息的集合")
    private List<Music> musicList;

    @ApiModelProperty("歌手信息的集合")
    private List<Singer> singerList;

    @ApiModelProperty("专辑信息的集合")
    private List<Album> albumList;
}
