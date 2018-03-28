package com.teapot.service;

import com.teapot.pojo.TbOrder;

import java.util.List;

/**
 * Created by Administrator on 2018-3-27.
 */
public interface OrderService {
    TbOrder newOrder(Integer wishId, String tempId, Double money);

    TbOrder selectById(Integer id);

    List<TbOrder> selectAllPaid();

    void paid(String tradeNo, String outTradeNo, Byte payType);

    List<TbOrder> selectTopOrder(Integer limit);
}
