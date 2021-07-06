package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel("查询回复评论")
@Data
public class FindReplyCommentsByCommentsIdParam extends PageParam implements Serializable {

    @ApiModelProperty("评论Id")
    @NotEmpty(message = "评论id不允许为空")
    private Long commentsId;

    @ApiModelProperty("排序类型:0表示最热，1表示最新，默认为0")
    @Min(0)
    @Max(1)
    private Integer sortingType;
}
