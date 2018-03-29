package com.teapot.interceptor;

import com.teapot.contants.SessionKeyContants;
import com.teapot.pojo.TbUser;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018-3-28.
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        TbUser user = (TbUser) session.getAttribute(SessionKeyContants.SESSION_CUR_USER);
        if(user == null){
            response.sendRedirect("/admin/login.html");
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
