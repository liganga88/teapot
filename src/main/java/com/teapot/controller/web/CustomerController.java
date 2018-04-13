package com.teapot.controller.web;

import com.aliyuncs.exceptions.ClientException;
import com.teapot.bean.JsonResult;
import com.teapot.contants.SessionKeyContants;
import com.teapot.controller.BaseController;
import com.teapot.dto.OrderDto;
import com.teapot.pojo.TbCustomer;
import com.teapot.pojo.TbOrder;
import com.teapot.pojo.TbWish;
import com.teapot.service.CustomerService;
import com.teapot.service.OrderService;
import com.teapot.service.WishService;
import com.teapot.utils.BeanUtils;
import com.teapot.utils.SmsUtil;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-3-30.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private WishService wishService;

    @RequestMapping("rank.html")
    public String rank(Model model, HttpSession session){

        TbCustomer customer = (TbCustomer) session.getAttribute(SessionKeyContants.SESSION_CUR_CUSTOMER);

        //所有功德金及排位
        List<TbOrder> orders = orderService.selectAllPaid();
        List<OrderDto> orderDtos = BeanUtils.copyObjectList(orders, OrderDto.class);
        //设置排位(同金额，排位并列)
        for (int i = 0; i < orderDtos.size(); i++) {
            OrderDto preOrder = null;
            if (i > 1) {
                preOrder = orderDtos.get(i - 1);
            }
            OrderDto curOrder = orderDtos.get(i);
            if (preOrder != null && preOrder.getMoney() == curOrder.getMoney()) {
                curOrder.setRank(preOrder.getRank());
            } else {
                curOrder.setRank(i + 1);
            }
        }
        model.addAttribute("orders", orderDtos);

        //获取我的功德金
        List<OrderDto> myOrders = new ArrayList<>();
        if (customer != null) {
            for (OrderDto order : orderDtos) {
                if (order.getCustomerid() == customer.getId()) {
                    myOrders.add(order);
                }
            }
        }
        model.addAttribute("myOrders", myOrders);

        return "web/rank";
    }

    @RequestMapping("allRank.html")
    public String allRank(Model model){
        //所有功德金及排位
        List<TbOrder> orders = orderService.selectAllPaid();
        List<OrderDto> orderDtos = BeanUtils.copyObjectList(orders, OrderDto.class);
        //设置排位(同金额，排位并列)
        for (int i = 0; i < orderDtos.size(); i++) {
            OrderDto preOrder = null;
            if (i > 1) {
                preOrder = orderDtos.get(i - 1);
            }
            OrderDto curOrder = orderDtos.get(i);
            if (preOrder != null && preOrder.getMoney() == curOrder.getMoney()) {
                curOrder.setRank(preOrder.getRank());
            } else {
                curOrder.setRank(i + 1);
            }
        }
        model.addAttribute("orders", orderDtos);

        return "web/all_rank";
    }

    @RequestMapping("search.html")
    public String search(){
        return "web/search";
    }

    @RequestMapping("checkPhone")
    @ResponseBody
    public JsonResult checkPhone(@RequestParam("phone") String phone,
                                 @RequestParam("code") String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute(SessionKeyContants.SESSION_SMS_CODE);
        if (!sessionCode.equals(code)) {
            return JsonResult.error("验证码不正确");
        }
        TbCustomer customer = customerService.selectByPhone(phone);
        if (customer == null) {
            return JsonResult.error("该手机用户不存在");
        } else {
            session.setAttribute(SessionKeyContants.SESSION_CUR_CUSTOMER, customer);
            session.removeAttribute(SessionKeyContants.SESSION_SMS_CODE);
            return JsonResult.ok();
        }
    }

    @RequestMapping("checkPePhone")
    @ResponseBody
    public JsonResult checkPePhone(@RequestParam("phone") String phone,
                                 @RequestParam("code") String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute(SessionKeyContants.SESSION_SMS_CODE);
        if (!sessionCode.equals(code)) {
            return JsonResult.error("验证码不正确");
        }
        session.setAttribute(SessionKeyContants.SESSION_TEMP_PREVIEW, phone);
        session.removeAttribute(SessionKeyContants.SESSION_SMS_CODE);
        return JsonResult.ok();
    }

    @RequestMapping("newByPhone")
    @ResponseBody
    public JsonResult newByPhone(@RequestParam("phone") String phone, HttpSession session) {
        String tempId = (String) session.getAttribute(SessionKeyContants.SESSION_TEMP_CUSTOMER);
        customerService.newByPhone(phone, tempId);

        return JsonResult.ok();
    }

    @RequestMapping(value = "sendSms")
    @ResponseBody
    public JsonResult sendMessage(@RequestParam("phone") String phone, HttpSession session){

        String smscode = String.valueOf(RandomUtils.nextInt(100000, 999999));
        session.setAttribute(SessionKeyContants.SESSION_SMS_CODE, smscode);
        String content = "{\"code\":\"" + smscode + "\"}";

        try {
            SmsUtil.sendSms(SmsUtil.TEMPLATE_CODE_YANZ, phone, content);

        } catch (ClientException e) {
            logger.error("短信发送失败");
        }
        return JsonResult.ok();
    }

    @RequestMapping("myWish.html")
    public String myWish(Model model, HttpSession session){
        TbCustomer customer = (TbCustomer) session.getAttribute(SessionKeyContants.SESSION_CUR_CUSTOMER);
        List<TbWish> wishs = wishService.selectByCustomerId(customer.getId());
        model.addAttribute("wishs", wishs);
        return "web/myWish";
    }
}
