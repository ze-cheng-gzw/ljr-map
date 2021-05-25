package com.demo.controller.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.common.ApiResponseCode;
import com.demo.common.ApiResponseWrapper;
import com.demo.common.Result;
import com.demo.entity.Member;
import com.demo.interfaceService.SearchService;
import com.demo.param.ConditionsSearchParam;
import com.demo.vo.SearchBoxChangeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @GetMapping("/search/searchBoxChange")
    @ApiOperation(value = "搜索框改变搜索", notes = "返回结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchString", value = "查询值", required = true, paramType = "query", dataType = "String")
    })
    public Result<SearchBoxChangeVO> searchBoxChange(String searchString) {

        SearchBoxChangeVO searchBoxChangeVO = searchService.searchBoxChange(searchString);

        return ApiResponseWrapper.wrap(ApiResponseCode.SUCCESS, searchBoxChangeVO);
    }

    @GetMapping("/search/conditionsSearch")
    @ApiOperation(value = "搜索框搜索", notes = "返回结果")
    public Result<SearchBoxChangeVO> conditionsSearch(ConditionsSearchParam conditionsSearchParam) {

        SearchBoxChangeVO searchBoxChangeVO = searchService.conditionsSearch(conditionsSearchParam);

        return ApiResponseWrapper.wrap(ApiResponseCode.SUCCESS, searchBoxChangeVO);
    }
}
