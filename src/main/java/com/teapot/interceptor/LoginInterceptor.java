package com.teapot.interceptor;

import com.teapot.contants.SessionKeyContants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by Administrator on 2017-12-13.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String tempId = (String) session.getAttribute(SessionKeyContants.SESSION_TEMP_CUSTOMER);
        if (StringUtils.isBlank(tempId)) {
            session.setAttribute(SessionKeyContants.SESSION_TEMP_CUSTOMER, UUID.randomUUID().toString());
        }

        return super.preHandle(request, response, handler);
    }
}
