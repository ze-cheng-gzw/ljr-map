package com.music.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.dao.MemberMapper;
import com.demo.entity.Member;
import com.demo.interfaceService.SearchService;

import javax.annotation.Resource;

@Service(accesslog = "searchService")
public class SearchServiceImpl implements SearchService {

    @Resource
    private MemberMapper memberMapper;

    public Member findMemberById() {
        return memberMapper.selectByPrimaryKey(1L);
    }
}
