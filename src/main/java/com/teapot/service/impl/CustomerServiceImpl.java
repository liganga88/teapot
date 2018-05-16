package com.teapot.service.impl;

import com.teapot.dao.TbCustomerDao;
import com.teapot.dao.TbOrderDao;
import com.teapot.dao.TbWishDao;
import com.teapot.pojo.*;
import com.teapot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-3-30.
 */
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private TbCustomerDao customerDao;
    @Autowired
    private TbOrderDao orderDao;
    @Autowired
    private TbWishDao wishDao;

    @Override
    public TbCustomer selectByPhone(String phone) {
        TbCustomerQuery query = new TbCustomerQuery();
        TbCustomerQuery.Criteria criteria = query.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<TbCustomer> customers = customerDao.selectByExample(query);
        if (customers.size() > 0) {
            return customers.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void newByPhone(String phone, String tempId) {
        TbCustomer customer = selectByPhone(phone);
        if (customer == null) {
            customer = new TbCustomer();
            customer.setPhone(phone);
            customer.setState((byte) 0);
            customer.setCreated(new Date());

            customerDao.insert(customer);
        }
        int cusutomerId = customer.getId();

        TbOrder order = new TbOrder();
        order.setCustomerid(cusutomerId);

        TbOrderQuery query = new TbOrderQuery();
        TbOrderQuery.Criteria criteria = query.createCriteria();
        criteria.andTempidEqualTo(tempId);
        orderDao.updateByExampleSelective(order, query);

        TbWish wish = new TbWish();
        wish.setCustomerid(cusutomerId);
        TbWishQuery wishQuery = new TbWishQuery();
        TbWishQuery.Criteria criteria1 = wishQuery.createCriteria();
        criteria1.andTempidEqualTo(tempId);
        wishDao.updateByExampleSelective(wish, wishQuery);

    }

    @Override
    public void updateOpenidById(Integer id, String openid) {
        TbCustomer customer = new TbCustomer();
        customer.setId(id);
        customer.setOpenid(openid);
        customerDao.updateByPrimaryKeySelective(customer);
    }
}
