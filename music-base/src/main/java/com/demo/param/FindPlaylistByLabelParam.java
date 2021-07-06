package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel("根据标签查询歌单列表上传信息")
@Data
public class FindPlaylistByLabelParam extends PageParam implements Serializable {

    @ApiModelProperty("标签名称")
    @NotEmpty(message = "标签不允许为空")
    private String labelName;
}
