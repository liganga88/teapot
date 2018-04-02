package com.teapot.service.impl;

import com.alipay.config.AlipayConfig;
import com.teapot.contants.CouponTypeContants;
import com.teapot.contants.OrderStateContants;
import com.teapot.dao.TbCouponDao;
import com.teapot.dao.TbOrderDao;
import com.teapot.pojo.TbCoupon;
import com.teapot.pojo.TbCouponQuery;
import com.teapot.pojo.TbOrder;
import com.teapot.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-4-2.
 */
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class CouponServiceImpl implements CouponService {

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private TbOrderDao orderDao;
    @Autowired
    private TbCouponDao couponDao;

    // 得到随机字符
    private String randomStr(int n) {
        String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String str2 = "";
        int len = str1.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            str2 = str2 + str1.charAt((int) r);
        }
        return str2;
    }

    private String getToken(){
        String token;
        TbCouponQuery query = new TbCouponQuery();
        TbCouponQuery.Criteria criteria = query.createCriteria();
        criteria.andStateEqualTo((byte)0);
        while (true) {
            token = randomStr(6);
            //todo andtoken
            List<TbCoupon> coupons = couponDao.selectByExample(query);
            if (coupons.size() <= 0) {
                break;
            }
        }
        return token;
    }

    @Override
    public void newCoupon(String orderCode, Integer couponType) {
        Integer orderId = Integer.parseInt(orderCode.replace(alipayConfig.getTradePrefix(), ""));
        TbOrder order = orderDao.selectByPrimaryKey(orderId);

        int num = 1;
        if (order.getMoney() >= 100000) {
            num = 3;
        }
        for (int i = 0; i < num; i++) {
            TbCoupon coupon = new TbCoupon();
            coupon.setTemplateid(CouponTypeContants.CHATANG);
            coupon.setCustomerid(order.getCustomerid());
            coupon.setCreated(new Date());
            coupon.setState((byte) 0);
            String token = getToken();
            coupon.setToken(token);
            couponDao.insert(coupon);
        }
    }

    @Override
    public void useCouponByToken(String token) {
        TbCouponQuery query = new TbCouponQuery();
        TbCouponQuery.Criteria criteria = query.createCriteria();
        criteria.andStateEqualTo((byte)0);
        //todo andtoken

        TbCoupon coupon = new TbCoupon();
        coupon.setState((byte)1);
        coupon.setUsedTime(new Date());
        couponDao.updateByExampleSelective(coupon, query);
    }
}
