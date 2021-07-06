package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel("修改创建添加信息")
@Data
public class UpdatePlaylistParam implements Serializable {

    @ApiModelProperty("歌单id")
    @NotEmpty(message = "歌单Id不能为空")
    private Long playlistId;

    @ApiModelProperty("歌单标题")
    private String playlistTitle;

    @ApiModelProperty("歌单标签")
    private String playlistLabel;

    @ApiModelProperty("歌单封面")
    private String playlistCover;
}
