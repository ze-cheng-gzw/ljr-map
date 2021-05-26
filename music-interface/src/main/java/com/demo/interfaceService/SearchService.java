package com.demo.interfaceService;

import com.demo.entity.Member;
import com.demo.param.ConditionsSearchParam;
import com.demo.vo.ConditionsSearchVO;
import com.demo.vo.SearchBoxChangeVO;

public interface SearchService {

    Member findMemberById();

    /**
     * 搜索框改变搜索
     * @param searchString
     * @return
     */
    SearchBoxChangeVO searchBoxChange(String searchString);

    /**
     * 搜索框搜索
     * @param conditionsSearchParam
     * @return
     */
    ConditionsSearchVO conditionsSearch(ConditionsSearchParam conditionsSearchParam);
}
