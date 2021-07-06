package com.music.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.common.ApiResponseCode;
import com.demo.common.BizException;
import com.demo.dao.MemberMapper;
import com.demo.dao.MemberTokenMapper;
import com.demo.entity.Member;
import com.demo.entity.MemberToken;
import com.demo.enums.MemberTypeEnum;
import com.demo.interfaceService.MemberService;
import com.demo.util.Base64Utils;
import com.demo.util.CommonUtils;
import com.demo.util.TokenUtil;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private MemberTokenMapper memberTokenMapper;

    //账号密码登录
    public String login(String loginName, String password) {
        password = Base64Utils.decode(password);

        Member member = memberMapper.getMemberByMobile(loginName);

        if (member == null) {
            BizException.fail(ApiResponseCode.PARA_ERR, "用户名密码不正确");
        } else {
            if (CommonUtils.genMd5Password(password, member.getPasswordSalt()).equals(member.getPassword())) {

                //登录后即执行修改token的操作
                String token = TokenUtil.getNewToken(System.currentTimeMillis() + "", member.getMemberId());
                MemberToken memberToken = memberTokenMapper.selectByMemberIdAndMemberType(member.getMemberId(), MemberTypeEnum.COMMON_MEMBER.getMemberType());
                //当前时间
                Date now = new Date();
                //过期时间
                Date expireTime = new Date(now.getTime() + 7 * 24 * 3600 * 1000);//过期时间 7 天
                if (memberToken == null) {
                    memberToken = new MemberToken();
                    memberToken.setMemberId(member.getMemberId());
                    memberToken.setToken(token);
                    memberToken.setMemberType(MemberTypeEnum.COMMON_MEMBER.getMemberType());
                    memberToken.setUpdateTime(now);
                    memberToken.setExpireTime(expireTime);
                    //新增一条token数据
                    if (memberTokenMapper.insertSelective(memberToken) > 0) {
                        //新增成功后返回
                        return token;
                    }
                } else {
                    //允许多地登录就不更改token
                    memberToken.setToken(token);
                    memberToken.setUpdateTime(now);
                    memberToken.setExpireTime(expireTime);
                    //更新
                    if (memberTokenMapper.updateByPrimaryKeySelective(memberToken) > 0) {
                        //修改成功后返回
                        return memberToken.getToken();
                    }
                }
            } else {
                BizException.fail(ApiResponseCode.PARA_ERR, "用户名密码不正确");
            }
        }
        return "error";
    }

    //账号退出
    public boolean exit(MemberToken memberToken) {

        int count = memberTokenMapper.deleteByPrimaryKey(memberToken.getId());

        return count == 1 ? true : false;
    }
}
