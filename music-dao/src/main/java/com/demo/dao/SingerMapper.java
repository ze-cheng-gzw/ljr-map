package com.demo.dao;

import com.demo.entity.Singer;
import com.demo.vo.SingerInfoVO;
import io.lettuce.core.dynamic.annotation.Param;

public interface SingerMapper {
    int deleteByPrimaryKey(Long singerId);

    int insert(Singer record);

    int insertSelective(Singer record);

    Singer selectByPrimaryKey(Long singerId);

    int updateByPrimaryKeySelective(Singer record);

    int updateByPrimaryKey(Singer record);

    /**
     * 根据歌手Id获取歌手的信息
     * @param singerId
     * @return
     */
    SingerInfoVO getSingerInfoBySingerId(@Param("singerId") String singerId);
}