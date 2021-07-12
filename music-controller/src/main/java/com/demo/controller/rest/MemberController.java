package com.demo.controller.rest;


import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.common.*;
import com.demo.controller.annotation.Token;
import com.demo.entity.Member;
import com.demo.entity.MemberToken;
import com.demo.interfaceService.MemberService;
import com.demo.param.MemberLoginParam;
import com.demo.param.MemberRegisteredParam;
import com.demo.util.NumberUtil;
import com.demo.vo.MemberInfoVO;
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

    @PostMapping("/member/memberRegistered")
    @ApiOperation(value = "用户注册", notes = "返回token")
    public Result<String> memberRegistered(@RequestBody @Valid MemberRegisteredParam memberRegisteredParam) {

        boolean boo = NumberUtil.isPhone(memberRegisteredParam.getMemberMobile());
        if (!boo) {
            BizException.fail("手机号不符合规范");
        }

        String loginResult = memberService.memberRegistered(memberRegisteredParam);

        //注册成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            return ApiResponseWrapper.wrap(loginResult);
        }
        //登录失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "注册失败");
    }

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

    @PostMapping("/member/getInfo")
    @ApiOperation(value = "获取用户信息", notes = "返回用户信息结果")
    public Result<MemberInfoVO> getInfo(@Token MemberToken memberToken) {

        MemberInfoVO memberInfoVO = memberService.getInfo(memberToken);

        //退出成功
        if (memberInfoVO != null) {
            return ApiResponseWrapper.wrap(memberInfoVO);
        }
        //退出失败
        return ApiResponseWrapper.wrap(ApiResponseCode.FAILURE, "获取用户信息失败");
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
