package com.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel("用户注册上传信息")
@Data
public class MemberRegisteredParam implements Serializable {

    @ApiModelProperty("用户名称")
    @NotEmpty(message = "用户名称不允许为空")
    private String memberName;

    @ApiModelProperty("用户手机号")
    @NotEmpty(message = "用户手机号不允许为空")
    private String memberMobile;

    @ApiModelProperty("用户密码，不要明文上传，用base64加密上传")
    @NotEmpty(message = "用户密码不允许为空")
    private String memberPassword;

    @ApiModelProperty("用户头像")
    @NotEmpty(message = "用户头像不允许为空")
    private String memberUrl;


}
