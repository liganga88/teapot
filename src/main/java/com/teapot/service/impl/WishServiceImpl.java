package com.teapot.service.impl;

import com.teapot.dao.TbWishDao;
import com.teapot.pojo.TbWish;
import com.teapot.pojo.TbWishQuery;
import com.teapot.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    public void updateSmsPhone(Integer id, String phone){
        TbWish tbWish = new TbWish();
        tbWish.setId(id);
        tbWish.setSmsphone(phone);
        wishDao.updateByPrimaryKeySelective(tbWish);
    }

    public List<TbWish> selectAll() {
        TbWishQuery query = new TbWishQuery();
        List<TbWish> wishs = wishDao.selectByExample(query);
        return wishs;
    }

    @Override
    public List<TbWish> selectBySmsPhone(String phone) {
        TbWishQuery query = new TbWishQuery();
        query.setOrderByClause("id desc");
        TbWishQuery.Criteria criteria = query.createCriteria();
        criteria.andSmsphoneEqualTo(phone);
        List<TbWish> wishs = wishDao.selectByExample(query);
        return wishs;
    }

    @Override
    public List<TbWish> selectBySmsPhoneBeforeDate(String phone, Date time) {
        TbWishQuery query = new TbWishQuery();
        query.setOrderByClause("id desc");
        TbWishQuery.Criteria criteria = query.createCriteria();
        criteria.andSmsphoneEqualTo(phone);
        criteria.andCreatedGreaterThan(time);
        List<TbWish> wishs = wishDao.selectByExample(query);
        return wishs;
    }

    @Override
    public List<TbWish> selectByCustomerId(Integer customerId) {
        TbWishQuery query = new TbWishQuery();
        query.setOrderByClause("id desc");
        TbWishQuery.Criteria criteria = query.createCriteria();
        criteria.andCustomeridEqualTo(customerId);
        List<TbWish> wishs = wishDao.selectByExample(query);
        return wishs;
    }
}
