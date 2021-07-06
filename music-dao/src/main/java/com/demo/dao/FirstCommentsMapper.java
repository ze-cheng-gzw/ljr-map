package com.demo.dao;

import com.demo.entity.FirstComments;
import com.demo.vo.FindCommentsVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface FirstCommentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FirstComments record);

    int insertSelective(FirstComments record);

    FirstComments selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FirstComments record);

    int updateByPrimaryKey(FirstComments record);

    /**
     * 拉取一级评论
     * @param id
     * @param type
     * @param sortingType
     * @return
     */
    List<FindCommentsVO> findCommentsById(@Param("id") Long id, @Param("type") Integer type, @Param("sortingType") Integer sortingType);
}