package com.teapot.dao;

import com.teapot.pojo.TbCouponTemplate;
import com.teapot.pojo.TbCouponTemplateQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCouponTemplateDao {
    int countByExample(TbCouponTemplateQuery example);

    int deleteByExample(TbCouponTemplateQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCouponTemplate record);

    int insertSelective(TbCouponTemplate record);

    List<TbCouponTemplate> selectByExample(TbCouponTemplateQuery example);

    TbCouponTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCouponTemplate record, @Param("example") TbCouponTemplateQuery example);

    int updateByExample(@Param("record") TbCouponTemplate record, @Param("example") TbCouponTemplateQuery example);

    int updateByPrimaryKeySelective(TbCouponTemplate record);

    int updateByPrimaryKey(TbCouponTemplate record);
}