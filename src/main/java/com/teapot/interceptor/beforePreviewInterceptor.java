package com.teapot.interceptor;

import com.teapot.contants.SessionKeyContants;
import com.teapot.pojo.TbCustomer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018-3-28.
 */
public class beforePreviewInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String previewPhone  = (String) session.getAttribute(SessionKeyContants.SESSION_TEMP_PREVIEW);
        if (StringUtils.isBlank(previewPhone)) {
            response.sendRedirect("/wish/preview/search.html");
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
