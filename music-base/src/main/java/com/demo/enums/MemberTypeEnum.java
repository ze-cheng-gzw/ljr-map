package com.demo.enums;

/**
 * 用户类型枚举 0=普通用户
 */
public enum MemberTypeEnum {

    DEFAULT(-1, "ERROR"),
    COMMON_MEMBER(0, "COMMON_MEMBER"),
    ADMIN_MEMBER(1, "ADMIN_MEMBER");

    private int memberType;

    private String name;

    MemberTypeEnum(int memberType, String name) {
        this.memberType = memberType;
        this.name = name;
    }

    public static MemberTypeEnum getMemberTypeEnumByType(int memberType) {
        for (MemberTypeEnum memberTypeEnum : MemberTypeEnum.values()) {
            if (memberTypeEnum.getMemberType() == memberType) {
                return memberTypeEnum;
            }
        }
        return DEFAULT;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
