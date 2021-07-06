package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel("歌单创建添加信息")
@Data
public class CreatePlaylistParam implements Serializable {

    @ApiModelProperty("歌单标题")
    @NotEmpty(message = "歌单标题不能为空")
    private String playlistTitle;
}
