package com.teapot.interceptor;

import com.teapot.contants.SessionKeyContants;
import com.teapot.pojo.TbCustomer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/4/19.
 */
public class WxInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String openid = (String) session.getAttribute(SessionKeyContants.SESSION_OPENID);
//        if(customer == null){
//            response.sendRedirect("/customer/allRank.html");
//            return false;
//        }

        return super.preHandle(request, response, handler);
    }
}
