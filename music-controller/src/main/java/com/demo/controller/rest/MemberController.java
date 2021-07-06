package com.demo.controller.rest;


import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.common.ApiResponseCode;
import com.demo.common.ApiResponseWrapper;
import com.demo.common.Constants;
import com.demo.common.Result;
import com.demo.controller.annotation.Token;
import com.demo.entity.MemberToken;
import com.demo.interfaceService.MemberService;
import com.demo.param.MemberLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "v1", tags = "用户入口")
@RequestMapping("/api/v1")
public class MemberController {

    @Reference
    private MemberService memberService;

    @PostMapping("/member/login")
    @ApiOperation(value = "账号密码登录接口", notes = "返回token")
    public Result<String> login(@RequestBody @Valid MemberLoginParam memberLoginParam) {

        String loginResult = memberService.login(memberLoginParam.getLoginName(), memberLoginParam.getPassword());

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            return ApiResponseWrapper.wrap(loginResult);
        }
        //登录失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "登录失败");
    }

    @PostMapping("/member/exit")
    @ApiOperation(value = "账号退出", notes = "返回退出结果")
    public Result<String> login(@Token MemberToken memberToken) {

        boolean result = memberService.exit(memberToken);

        //退出成功
        if (result) {
            return ApiResponseWrapper.wrap("退出成功");
        }
        //退出失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "退出失败");
    }
}
