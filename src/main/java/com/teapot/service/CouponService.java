package com.teapot.service;

import com.teapot.pojo.TbCoupon;

import java.util.List;

/**
 * Created by Administrator on 2018-4-2.
 */
public interface CouponService {
    void newCoupon(String orderCode, Integer couponType);
    void useCouponByToken(String token);
    TbCoupon selectByToken(String token);
    TbCoupon selectById(Integer id);
    void useCouponById(Integer id);
    List<TbCoupon> selectByOrderId(Integer orderId);
}
