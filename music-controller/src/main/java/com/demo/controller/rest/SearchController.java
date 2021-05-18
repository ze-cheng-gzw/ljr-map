package com.demo.controller.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.entity.Member;
import com.demo.interfaceService.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "v1", tags = "搜索入口")
@RequestMapping("/api/v1")
public class SearchController {

    // dubbo用@Reference 调用服务
    @Reference
    private SearchService searchService;

    @GetMapping("/order/getMemberById")
    @ApiOperation(value = "测试dubbo之间的链接", notes = "测试")
    public Member getMemberById() {

        return searchService.findMemberById();
    }
}
