package com.teapot.dao;

import com.teapot.pojo.TbPv;
import com.teapot.pojo.TbPvQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbPvDao {
    int countByExample(TbPvQuery example);

    int deleteByExample(TbPvQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbPv record);

    int insertSelective(TbPv record);

    List<TbPv> selectByExample(TbPvQuery example);

    TbPv selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbPv record, @Param("example") TbPvQuery example);

    int updateByExample(@Param("record") TbPv record, @Param("example") TbPvQuery example);

    int updateByPrimaryKeySelective(TbPv record);

    int updateByPrimaryKey(TbPv record);
}