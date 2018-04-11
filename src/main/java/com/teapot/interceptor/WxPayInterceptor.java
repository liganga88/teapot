package com.teapot.interceptor;


import com.jpay.weixin.api.WxPayApiConfigKit;
import com.teapot.controller.web.WxPayApiController;
import com.teapot.controller.web.WxPayController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WxPayInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (HandlerMethod.class.equals(handler.getClass())) {
			HandlerMethod method = (HandlerMethod) handler;
			Object controller = method.getBean();
			if (controller instanceof WxPayApiController == false) {
				throw new RuntimeException("控制器需要继承 WxPayApiController");
			}
			
			try {
				WxPayApiConfigKit.setThreadLocalWxPayApiConfig(((WxPayController) controller).getApiConfig());
				return true;
			}
			finally {
			}
		}
		return false;
	}
}
