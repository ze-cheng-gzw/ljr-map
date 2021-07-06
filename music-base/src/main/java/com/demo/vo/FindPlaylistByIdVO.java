package com.demo.vo;

import com.demo.util.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("根据歌单ID查询歌单列表vo")
@Data
public class FindPlaylistByIdVO implements Serializable {

    @ApiModelProperty("歌单封面")
    private String playlistCover;

    @ApiModelProperty("歌单标题")
    private String playlistTitle;

    @ApiModelProperty("歌单标签")
    private String playlistLabel;

    @ApiModelProperty("歌单简介")
    private String playlistIntroduction;

    @ApiModelProperty("所属人id")
    private Long memberId;

    @ApiModelProperty("所属人名称")
    private String memberName;

    @ApiModelProperty("所属人头像")
    private String memberUrl;

    @ApiModelProperty("歌单下相应信息列表")
    private PageResult<PlaylistMusicVO> playlistMusicVOPageResult;
}
