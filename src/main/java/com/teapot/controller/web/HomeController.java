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
public class HomeController {
    @Autowired
    private WishService wishService;

    /**
     * ∆Ì∏£“≥√Ê
     * @param model
     * @return
     */
    @RequestMapping(value = "/wish.html")
    public String wish(Model model) {

        return "wish";
    }

    /**
     * ∆Ì∏£¥¶¿Ì
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
}
