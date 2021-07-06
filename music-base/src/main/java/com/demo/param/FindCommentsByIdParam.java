package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("查询评论")
@Data
public class FindCommentsByIdParam extends PageParam implements Serializable {

    @ApiParam(value = "分页数量，一级评论固定分页为5")
    @NotNull(message = "pageSize不能为空")
    @Max(5)
    @Min(5)
    private Integer pageSize;

    @NotNull(message = "pageNo不能为空")
    @ApiParam(value = "页码，不能小于-1")
    @Min(-1)
    private Integer pageNo;

    @ApiModelProperty("歌曲或者歌单id")
    @NotEmpty(message = "歌曲或者歌单不允许为空")
    private Long id;

    @ApiModelProperty("类型:0表示音乐，1表示歌曲，默认为0")
    @Min(0)
    @Max(1)
    private Integer type;

    @ApiModelProperty("排序类型:0表示最热，1表示最新，默认为0")
    @Min(0)
    @Max(1)
    private Integer sortingType;
}
