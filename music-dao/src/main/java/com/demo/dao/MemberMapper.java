package com.demo.dao;

import com.demo.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;

public interface MemberMapper {
    int deleteByPrimaryKey(Long memberId);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long memberId);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    /**
     * 根据手机号进行查找
     * @param loginName
     * @return
     */
    Member getMemberByMobile(@Param("loginName") String loginName);
}