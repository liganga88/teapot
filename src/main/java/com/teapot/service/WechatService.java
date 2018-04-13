package com.teapot.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/13.
 */
public interface WechatService {
    public String weixinPost(HttpServletRequest request);
}
