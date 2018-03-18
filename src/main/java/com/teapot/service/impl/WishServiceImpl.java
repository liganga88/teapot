package com.teapot.service.impl;

import com.teapot.dao.TbWishDao;
import com.teapot.pojo.TbWish;
import com.teapot.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/17.
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class WishServiceImpl implements WishService {
    @Autowired
    private TbWishDao wishDao;
    public Integer addWish(String wish, String hoper, String tempId) {
        TbWish tbWish = new TbWish();
        tbWish.setWish(wish);
        tbWish.setHoper(hoper);
        tbWish.setTempid(tempId);
        tbWish.setCreated(new Date());
        wishDao.insert(tbWish);
        return tbWish.getId();
    }

    public TbWish selectById(Integer id) {
        TbWish tbWish = wishDao.selectByPrimaryKey(id);
        return tbWish;
    }
}