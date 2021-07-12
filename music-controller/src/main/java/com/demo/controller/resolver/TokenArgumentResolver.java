package com.demo.controller.resolver;

import com.demo.common.ApiResponseCode;
import com.demo.common.BizException;
import com.demo.common.Constants;
import com.demo.controller.annotation.Token;
import com.demo.dao.MemberTokenMapper;
import com.demo.entity.MemberToken;
import com.demo.enums.MemberTypeEnum;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

@Component
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private MemberTokenMapper memberTokenMapper;

    public TokenArgumentResolver() {

    }

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(Token.class)) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getParameterAnnotation(Token.class) instanceof Token) {
            String token = webRequest.getHeader("token");
            String memberType = webRequest.getHeader("memberType");
            System.out.println("token为:" + token + ";memberType为：" + memberType);
            System.out.println("获取的type:" + MemberTypeEnum.getMemberTypeEnumByType(memberType).getMemberType());
            if (validToken(token, memberType)) {
                MemberToken memberToken = memberTokenMapper.selectByTokenAndMemberType(token, MemberTypeEnum.getMemberTypeEnumByType(memberType).getMemberType());
                if (memberToken == null || memberToken.getExpireTime().getTime() <= System.currentTimeMillis()) {
                    BizException.fail(ApiResponseCode.TOKEN_INVALID);//令牌无效
                }
                return memberToken;
            } else {
                BizException.fail(ApiResponseCode.TOKEN_INVALID);
            }
        }
        return null;
    }

    /**
     * Token的基本验证
     *
     * @param token
     * @param memberType
     * @return
     */
    private Boolean validToken(String token, String memberType) {
        if (null != token && !"".equals(token) && token.length() == Constants.TOKEN_LENGTH && null != memberType && !"".equals(memberType) && MemberTypeEnum.getMemberTypeEnumByType(memberType) != MemberTypeEnum.DEFAULT) {
            return true;
        }
        return false;
    }
}
