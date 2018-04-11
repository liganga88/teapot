package com.teapot.service.impl;

import com.alipay.config.AlipayConfig;
import com.teapot.contants.OrderStateContants;
import com.teapot.dao.TbCustomerDao;
import com.teapot.dao.TbOrderDao;
import com.teapot.dao.TbWishDao;
import com.teapot.pojo.*;
import com.teapot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-3-27.
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbCustomerDao customerDao;

    @Autowired
    private TbOrderDao orderDao;

    @Autowired
    private TbWishDao wishDao;

    @Autowired
    private AlipayConfig alipayConfig;

    @Override
    public TbOrder newOrder(Integer wishId, String tempId, Double money) {

/*        TbCustomerQuery customerQuery = new TbCustomerQuery();
        TbCustomerQuery.Criteria customerCriteria = customerQuery.createCriteria();
        customerCriteria.andTempIdEqualTo(tempId);
        List<TbCustomer> customers = customerDao.selectByExample(customerQuery);
        TbCustomer customer;
        if(customers.size() > 0){
            customer = customers.get(0);
        }else {
            customer = new TbCustomer();
            customer.setTempId(tempId);
            customer.setState((byte) 0);
            customer.setCreated(new Date());
            customerDao.insert(customer);
        }*/

        TbWish wish = wishDao.selectByPrimaryKey(wishId);
        if (wish == null) {
            throw new IllegalArgumentException();
        }

        TbOrder order = new TbOrder();
//        order.setCustomerid(customer.getId());
        order.setTempid(tempId);
        order.setMoney((int)(money * 100));
        order.setState(OrderStateContants.CREATED);
        order.setCreated(new Date());
        order.setHoper(wish.getHoper());
        order.setWishid(wishId);
        orderDao.insert(order);

        return order;
    }

    @Override
    public TbOrder selectById(Integer id) {
        TbOrder order = orderDao.selectByPrimaryKey(id);
        return order;
    }

    @Override
    public void paid(String tradeNo, String outTradeNo, Byte payType){
        Integer orderId = Integer.parseInt(outTradeNo.replace(alipayConfig.getTradePrefix(), ""));
        TbOrder order = orderDao.selectByPrimaryKey(orderId);

        order.setPaytype(payType);
        order.setPayno(tradeNo);
        order.setPaytime(new Date());
        order.setState(OrderStateContants.PAID);
        orderDao.updateByPrimaryKey(order);
    }

    @Override
    public List<TbOrder> selectAllPaid() {
        TbOrderQuery query = new TbOrderQuery();
        query.setOrderByClause("money desc");
        TbOrderQuery.Criteria criteria = query.createCriteria();
        criteria.andStateEqualTo(OrderStateContants.PAID);
        List<TbOrder> orders = orderDao.selectByExample(query);

        return orders;
    }

    @Override
    public List<TbOrder> selectTopOrder(Integer limit) {
        TbOrderQuery query = new TbOrderQuery();
        TbOrderQuery.Criteria criteria = query.createCriteria();
        criteria.andStateEqualTo(OrderStateContants.PAID);
        query.setOrderByClause("money desc");
        query.setPageNo(1);
        query.setPageSize(limit);
        List<TbOrder> orders = orderDao.selectByExample(query);
        return orders;
    }

    @Override
    public TbOrder selectByWishId(Integer wishId) {
        TbOrderQuery query = new TbOrderQuery();
        TbOrderQuery.Criteria criteria = query.createCriteria();
        criteria.andWishidEqualTo(wishId);
        List<TbOrder> orders = orderDao.selectByExample(query);
        if (orders.size() > 0) {
            return orders.get(0);
        }
        return null;
    }

    @Override
    public TbOrder selectByPayNo(String payNo) {
        TbOrderQuery query = new TbOrderQuery();
        TbOrderQuery.Criteria criteria = query.createCriteria();
        criteria.andPaynoEqualTo(payNo);
        List<TbOrder> orders = orderDao.selectByExample(query);
        if (orders.size() > 0) {
            return orders.get(0);
        }
        return null;
    }
}
