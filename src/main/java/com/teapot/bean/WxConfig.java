package com.teapot.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/properties/wx.properties")
public class WxConfig {
    @Value("${wechat.mp.appId}")
    private String appid;

    @Value("${wechat.mp.secret}")
    private String secret;
    @Value("${wechat.mp.root}")
    private String root;

    @Value("${wechat.mp.token}")
    private String token;

    @Value("${wechat.mp.aesKey}")
    private String aeskey;

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getRoot() {
        return root;
    }

    public String getToken() {
        return token;
    }

    public String getAeskey() {
        return aeskey;
    }

    @Override
    public String toString() {
        return "WxConfig{" +
                "appid='" + appid + '\'' +
                ", secret='" + secret + '\'' +
                ", token='" + token + '\'' +
                ", aeskey='" + aeskey + '\'' +
                '}';
    }
}
