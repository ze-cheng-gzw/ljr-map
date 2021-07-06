package com.demo.dao;

import com.demo.entity.Member;
import com.demo.entity.MemberToken;
import io.lettuce.core.dynamic.annotation.Param;

public interface MemberTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberToken record);

    int insertSelective(MemberToken record);

    MemberToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberToken record);

    int updateByPrimaryKey(MemberToken record);

    /**
     * 根据token和类型查找相应记录条
     * @param token
     * @param memberType
     * @return
     */
    MemberToken selectByTokenAndMemberType(@Param("token") String token, @Param("memberType") Integer memberType);

    /**
     * 根据用户id和类型查找相应记录条
     * @param memberId
     * @param memberType
     * @return
     */
    MemberToken selectByMemberIdAndMemberType(@Param("memberId") Long memberId,@Param("memberType") Integer memberType);
}