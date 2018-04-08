package com.teapot.controller.web;

import com.teapot.bean.JsonResult;
import com.teapot.controller.BaseController;
import com.teapot.pojo.TbCoupon;
import com.teapot.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018-4-3.
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
    @Autowired
    private CouponService couponService;

    @RequestMapping("toUse.html")
    public String toUse(){
        return "web/coupon/toUse";
    }

    @RequestMapping("check")
    @ResponseBody
    public JsonResult check(@RequestParam("code") String code, Model model){
        TbCoupon coupon = couponService.selectByToken(code);
        Integer type = 0;       //0:token不存在 1:券未使用 2:券已使用
        if (coupon != null) {
            if (coupon.getState() == 0) {
                type = 1;
            } else {
                type = 2;
            }
        }
        return JsonResult.ok(type);
    }

    @RequestMapping("use")
    @ResponseBody
    public JsonResult use(@RequestParam("code") String code) {
        TbCoupon coupon = couponService.selectByToken(code);
        if (coupon == null || coupon.getState() == 1) {
            return JsonResult.error("验证码不正确");
        }
        couponService.useCouponById(coupon.getId());
        return JsonResult.ok();
    }
}
