package com.teapot.dao;

import com.teapot.pojo.TbCoupon;
import com.teapot.pojo.TbCouponQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCouponDao {
    int countByExample(TbCouponQuery example);

    int deleteByExample(TbCouponQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCoupon record);

    int insertSelective(TbCoupon record);

    List<TbCoupon> selectByExample(TbCouponQuery example);

    TbCoupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCoupon record, @Param("example") TbCouponQuery example);

    int updateByExample(@Param("record") TbCoupon record, @Param("example") TbCouponQuery example);

    int updateByPrimaryKeySelective(TbCoupon record);

    int updateByPrimaryKey(TbCoupon record);

    List<String> selectToken();
}