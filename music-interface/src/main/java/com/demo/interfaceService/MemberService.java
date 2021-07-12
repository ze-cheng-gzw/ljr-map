package com.demo.interfaceService;

import com.demo.entity.MemberToken;
import com.demo.param.MemberRegisteredParam;
import com.demo.vo.MemberInfoVO;

public interface MemberService {

    /**
     * 账号密码登录
     * @param loginName
     * @param password
     * @return
     */
    String login(String loginName, String password);

    /**
     * 账号退出
     * @param memberToken
     * @return
     */
    boolean exit(MemberToken memberToken);

    /**
     * 用户注册
     * @param memberRegisteredParam
     * @return
     */
    String memberRegistered(MemberRegisteredParam memberRegisteredParam);

    /**
     * 获取用户信息
     * @param memberToken
     * @return
     */
    MemberInfoVO getInfo(MemberToken memberToken);
}
