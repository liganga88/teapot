package com.teapot.dao;

import com.teapot.pojo.TbTemplateMessage;
import com.teapot.pojo.TbTemplateMessageQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbTemplateMessageDao {
    int countByExample(TbTemplateMessageQuery example);

    int deleteByExample(TbTemplateMessageQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbTemplateMessage record);

    int insertSelective(TbTemplateMessage record);

    List<TbTemplateMessage> selectByExample(TbTemplateMessageQuery example);

    TbTemplateMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbTemplateMessage record, @Param("example") TbTemplateMessageQuery example);

    int updateByExample(@Param("record") TbTemplateMessage record, @Param("example") TbTemplateMessageQuery example);

    int updateByPrimaryKeySelective(TbTemplateMessage record);

    int updateByPrimaryKey(TbTemplateMessage record);
}