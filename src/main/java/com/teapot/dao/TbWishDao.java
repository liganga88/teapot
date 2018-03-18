package com.teapot.dao;

import com.teapot.pojo.TbWish;
import com.teapot.pojo.TbWishQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbWishDao {
    int countByExample(TbWishQuery example);

    int deleteByExample(TbWishQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbWish record);

    int insertSelective(TbWish record);

    List<TbWish> selectByExample(TbWishQuery example);

    TbWish selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbWish record, @Param("example") TbWishQuery example);

    int updateByExample(@Param("record") TbWish record, @Param("example") TbWishQuery example);

    int updateByPrimaryKeySelective(TbWish record);

    int updateByPrimaryKey(TbWish record);
}