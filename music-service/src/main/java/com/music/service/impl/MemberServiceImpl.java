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
import com.demo.param.MemberRegisteredParam;
import com.demo.util.Base64Utils;
import com.demo.util.CommonUtils;
import com.demo.util.TokenUtil;
import com.demo.vo.MemberInfoVO;
import org.springframework.transaction.annotation.Transactional;

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

    //用户注册
    @Transactional(rollbackFor = Exception.class)
    public String memberRegistered(MemberRegisteredParam memberRegisteredParam) {
        //判断该手机号是否注册
        Member member = memberMapper.getMemberByMobile(memberRegisteredParam.getMemberMobile());
        if (member != null) {
            BizException.fail("改手机号已注册");
        }
        String password = Base64Utils.decode(memberRegisteredParam.getMemberPassword());
        String salt = CommonUtils.genSalt();
        password = CommonUtils.genMd5Password(password, salt);
        Member insertMember = new Member();
        insertMember.setMemberName(memberRegisteredParam.getMemberName());
        insertMember.setMemberMobile(memberRegisteredParam.getMemberMobile());
        insertMember.setPasswordSalt(salt);
        insertMember.setPassword(password);
        insertMember.setMemberUrl(memberRegisteredParam.getMemberUrl());
        int addCount = memberMapper.insertSelective(insertMember);
        if (addCount != 1) {
            BizException.fail("添加用户信息失败，注册失败");
        }
        //设置改用户的token
        String token = TokenUtil.getNewToken(System.currentTimeMillis() + "", insertMember.getMemberId());
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + 7 * 24 * 3600 * 1000);//过期时间 7 天
        MemberToken memberToken = new MemberToken();
        memberToken.setMemberId(insertMember.getMemberId());
        memberToken.setToken(token);
        memberToken.setMemberType(MemberTypeEnum.COMMON_MEMBER.getMemberType());
        memberToken.setUpdateTime(now);
        memberToken.setExpireTime(expireTime);
        addCount = memberTokenMapper.insertSelective(memberToken);
        if (addCount != 1) {
            BizException.fail("添加token失败，注册失败");
        }
        return token;
    }

    //获取用户信息
    public MemberInfoVO getInfo(MemberToken memberToken) {
        Member member = memberMapper.selectByPrimaryKey(memberToken.getMemberId());
        MemberInfoVO memberInfoVO = new MemberInfoVO();
        memberInfoVO.setMemberMobile(member.getMemberMobile());
        memberInfoVO.setMemberName(member.getMemberName());
        memberInfoVO.setMemberUrl(member.getMemberUrl());
        memberInfoVO.setVipLevel(member.getVipLevel());
        return memberInfoVO;
    }
}
