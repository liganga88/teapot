package com.teapot.controller.web;

import com.aliyuncs.exceptions.ClientException;
import com.teapot.bean.JsonResult;
import com.teapot.contants.SessionKeyContants;
import com.teapot.controller.BaseController;
import com.teapot.pojo.TbWish;
import com.teapot.service.WishService;
import com.teapot.utils.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/3/16.
 */
@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WishService wishService;

    /**
     * �?ҳ��
     * @param model
     * @return
     */
    @RequestMapping(value = "/wish.html")
    public String wish(Model model) {

        return "web/wish";
    }

    /**
     * �?����
     * @return
     */
    @RequestMapping(value = "/doWish", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doWish(@RequestParam("wish") String wish, @RequestParam("hoper") String hoper, HttpSession session) {
        String tempId = (String) session.getAttribute(SessionKeyContants.SESSION_TEMP_CUSTOMER);
        Integer id = wishService.addWish(wish, hoper, tempId);

        return JsonResult.ok(id);
    }

    @RequestMapping(value = "/wish/{id}/detail.html")
    public String wishDetail(@PathVariable("id") Integer id, Model model){
        TbWish tbWish = wishService.selectById(id);
        model.addAttribute("wish", tbWish);
        return "web/detail";
    }

    @RequestMapping(value = "/wish/{id}/preview.html")
    public String preview(@PathVariable("id") Integer id, Model model){
        TbWish tbWish = wishService.selectById(id);
        model.addAttribute("wish", tbWish);
        return "web/preview";
    }

    @RequestMapping(value = "/wish/{id}/sendMessage")
    @ResponseBody
    public JsonResult sendMessage(@RequestParam("phone") String phone, @PathVariable("id") Integer id){
        TbWish tbWish = wishService.selectById(id);
        try {
//            String content = "{\"customer\":\"" + tbWish.getHoper() +"\"}";
            String content = "";
            SmsUtil.sendSms(SmsUtil.TEMPLATE_CODE_WISH, phone, content);

            wishService.updateSmsPhone(id, phone);
        } catch (ClientException e) {
            logger.error("短信发送失败");
        }
        return JsonResult.ok();
    }

    @RequestMapping(value = "/wish/{id}/resultA.html")
    public String resulta(@PathVariable("id") Integer id, Model model){
        TbWish tbWish = wishService.selectById(id);
        model.addAttribute("wish", tbWish);
        return "web/result_a";
    }

    @RequestMapping(value = "/wish/resultB.html")
    public String resultb(Model model){
        return "web/result_b";
    }
}
