package com.teapot.service;

/**
 * Created by Administrator on 2018-4-2.
 */
public interface CouponService {
    void newCoupon(String orderCode, Integer couponType);
    void useCouponByToken(String token);
}
