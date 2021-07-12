package com.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("获取用户信息")
@Data
public class MemberInfoVO implements Serializable {

    @ApiModelProperty("用户名称")
    private String memberName;

    @ApiModelProperty("用户手机号")
    private String memberMobile;

    @ApiModelProperty("用户头像")
    private String memberUrl;

    @ApiModelProperty("用户vip等级")
    private Integer vipLevel;
}
