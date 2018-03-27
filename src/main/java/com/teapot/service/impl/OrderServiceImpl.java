package com.teapot.service.impl;

import com.alipay.config.AlipayConfig;
import com.teapot.contants.OrderStateContants;
import com.teapot.dao.TbCustomerDao;
import com.teapot.dao.TbOrderDao;
import com.teapot.pojo.TbCustomer;
import com.teapot.pojo.TbCustomerQuery;
import com.teapot.pojo.TbOrder;
import com.teapot.pojo.TbOrderQuery;
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
    private AlipayConfig alipayConfig;

    public TbOrder newOrder(String tempId, Double money) {

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

        TbOrder order = new TbOrder();
//        order.setCustomerid(customer.getId());
        order.setTempid(tempId);
        order.setMoney((int) (money * 100));
        order.setState(OrderStateContants.CREATED);
        order.setCreated(new Date());
        orderDao.insert(order);

        return order;
    }

    public TbOrder selectById(Integer id) {
        TbOrder order = orderDao.selectByPrimaryKey(id);
        return order;
    }

    public void paid(String tradeNo, String outTradeNo, Byte payType){
        Integer orderId = Integer.parseInt(outTradeNo.replace(alipayConfig.getTradePrefix(), ""));
        TbOrder order = orderDao.selectByPrimaryKey(orderId);

        order.setPaytype(payType);
        order.setPayno(tradeNo);
        order.setState(OrderStateContants.PAID);
        orderDao.updateByPrimaryKey(order);
    }
}
