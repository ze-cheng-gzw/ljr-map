package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("分页内容")
@Data
@NoArgsConstructor
public class PageParam implements Serializable {

    @ApiParam(value = "页面大小 最大不超过50")
    @NotNull(message = "pageSize不能为空")
    @Max(50)
    @Min(1)
    private Integer pageSize;

    @NotNull(message = "pageNo不能为空")
    @ApiParam(value = "页码，不能小于-1")
    @Min(-1)
    private Integer pageNo;
}