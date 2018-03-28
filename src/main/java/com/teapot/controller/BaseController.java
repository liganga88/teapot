package com.teapot.controller;

import com.teapot.contants.SessionKeyContants;
import com.teapot.pojo.TbUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
