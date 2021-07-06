package com.demo.interfaceService;

import com.demo.entity.MemberToken;

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
}
