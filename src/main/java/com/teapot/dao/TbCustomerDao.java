package com.teapot.dao;

import com.teapot.pojo.TbCustomer;
import com.teapot.pojo.TbCustomerQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCustomerDao {
    int countByExample(TbCustomerQuery example);

    int deleteByExample(TbCustomerQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCustomer record);

    int insertSelective(TbCustomer record);

    List<TbCustomer> selectByExample(TbCustomerQuery example);

    TbCustomer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCustomer record, @Param("example") TbCustomerQuery example);

    int updateByExample(@Param("record") TbCustomer record, @Param("example") TbCustomerQuery example);

    int updateByPrimaryKeySelective(TbCustomer record);

    int updateByPrimaryKey(TbCustomer record);
}