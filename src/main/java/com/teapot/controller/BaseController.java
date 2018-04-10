package com.teapot.controller;

import com.teapot.contants.SessionKeyContants;
import com.teapot.pojo.TbUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;

/**
 * Created by Administrator on 2018-3-13.
 */
public class BaseController {

    public HttpServletRequest getHttpServletRequest()
    {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpServletResponse getHttpServletResponse()
    {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public void setVerifyCode(String code) {
        getHttpServletRequest().getSession().setAttribute(SessionKeyContants.VERIFY_CODE, code);
    }

    public String getVerifyCode() {
        return (String) getHttpServletRequest().getSession().getAttribute(SessionKeyContants.VERIFY_CODE);
    }

    public void removeVerifyCode() {
        getHttpServletRequest().getSession().removeAttribute(SessionKeyContants.VERIFY_CODE);
    }

    public TbUser getCurUser(){
        return (TbUser) getHttpServletRequest().getSession().getAttribute(SessionKeyContants.SESSION_CUR_USER);
    }

    public void setCurUser(TbUser user){
        getHttpServletRequest().getSession().setAttribute(SessionKeyContants.SESSION_CUR_USER, user);
    }

    /**
     * @Description: 获取客户端IP地址
     */
    protected String getIpAddr() {
        HttpServletRequest request = getHttpServletRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
