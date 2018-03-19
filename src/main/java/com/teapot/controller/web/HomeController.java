package com.teapot.controller.web;

import com.teapot.bean.JsonResult;
import com.teapot.contants.SessionKeyContants;
import com.teapot.pojo.TbWish;
import com.teapot.service.WishService;
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
    @Autowired
    private WishService wishService;

    /**
     * �?ҳ��
     * @param model
     * @return
     */
    @RequestMapping(value = "/wish.html")
    public String wish(Model model) {

        return "wish";
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
        return "detail";
    }

    @RequestMapping(value = "/wish/{id}/preview.html")
    public String preview(@PathVariable("id") Integer id, Model model){
        TbWish tbWish = wishService.selectById(id);
        model.addAttribute("wish", tbWish);
        return "preview";
    }

    @RequestMapping(value = "/wish/resultA.html")
    public String resulta(Model model){
        return "result_a";
    }

    @RequestMapping(value = "/wish/resultB.html")
    public String resultb(Model model){
        return "result_b";
    }
}
