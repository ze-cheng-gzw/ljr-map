package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel("点赞上传内容")
@Data
public class ThumbComments implements Serializable {

    @ApiModelProperty("被点赞的评论Id，可以是评论id或者回复id")
    @NotEmpty(message = "评论id不允许为空")
    private Long id;

    @ApiModelProperty("评论类型:0表示评论，1表示回复评论")
    @NotEmpty(message = "评论类型不允许为空")
    private int type;
}
