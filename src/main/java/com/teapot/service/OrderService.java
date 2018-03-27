package com.teapot.service;

import com.teapot.pojo.TbOrder;

/**
 * Created by Administrator on 2018-3-27.
 */
public interface OrderService {
    TbOrder newOrder(String tempId, Double money);

    TbOrder selectById(Integer id);

    void paid(String tradeNo, String outTradeNo, Byte payType);
}
