package com.teapot.service.impl;

import com.teapot.dao.TbTemplateMessageDao;
import com.teapot.pojo.TbTemplateMessage;
import com.teapot.service.TemplateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/13.
 */
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class TemplateMessageServiceImpl implements TemplateMessageService {

    @Autowired
    private TbTemplateMessageDao templateMessageDao;

    @Override
    public void createTemplateMessage(TbTemplateMessage templateMessage) {

        templateMessage.setCreated(new Date());
        templateMessageDao.insert(templateMessage);
    }
}
