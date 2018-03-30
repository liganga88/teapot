package com.teapot.controller.web;

import com.teapot.bean.JsonResult;
import com.teapot.contants.SessionKeyContants;
import com.teapot.controller.BaseController;
import com.teapot.dto.OrderDto;
import com.teapot.pojo.TbCustomer;
import com.teapot.pojo.TbOrder;
import com.teapot.service.CustomerService;
import com.teapot.service.OrderService;
import com.teapot.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("rank.html")
    public String rank(@RequestParam("phone") String phone, Model model){

        TbCustomer customer = customerService.selectByPhone(phone);

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

    @RequestMapping("search.html")
    public String search(){
        return "web/search";
    }

    @RequestMapping("checkPhone")
    @ResponseBody
    public JsonResult checkPhone(@RequestParam("phone") String phone) {
        TbCustomer customer = customerService.selectByPhone(phone);
        if (customer == null) {
            return JsonResult.error("该手机用户不存在");
        } else {
            return JsonResult.ok();
        }
    }

    @RequestMapping("newByPhone")
    @ResponseBody
    public JsonResult newByPhone(@RequestParam("phone") String phone, HttpSession session) {
        String tempId = (String) session.getAttribute(SessionKeyContants.SESSION_TEMP_CUSTOMER);
        customerService.newByPhone(phone, tempId);

        return JsonResult.ok();
    }
}
