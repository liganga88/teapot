package com.teapot.controller.admin;

import com.teapot.bean.JsonResult;
import com.teapot.controller.BaseController;
import com.teapot.pojo.TbOrder;
import com.teapot.pojo.TbUser;
import com.teapot.pojo.TbWish;
import com.teapot.service.OrderService;
import com.teapot.service.UserService;
import com.teapot.service.WishService;
import com.teapot.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2018-3-28.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private WishService wishService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/login.html")
    public String login(){
        return "/admin/login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public JsonResult doLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("passcode") String passcode, HttpSession session){

        String vcode = getVerifyCode();
        if (!passcode.equalsIgnoreCase(vcode)) {
            return JsonResult.error("验证码不正确");
        }

        TbUser user = userService.selectByUsername(username);

        String pw = null;
        try {
            pw = MD5.md5(password, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null && user.getPassword().equals(pw)) {
            setCurUser(user);
            session.setMaxInactiveInterval(24 * 60 * 60);
            return JsonResult.ok();
        } else {
            return JsonResult.error("用户名或密码错误");
        }
    }

    @RequestMapping("rank.html")
    public String rank(Model model){
        List<TbOrder> orders = orderService.selectAllPaid();
        model.addAttribute("orders", orders);

        List<TbOrder> topOrders = orderService.selectTopOrder(2);
        model.addAttribute("topOrders", topOrders);

        return "admin/rank";
    }
}
