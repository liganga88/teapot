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

    @Value("${wechat.mp.encryptMessage}")
    private Boolean encryptMessage;

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

    public Boolean getEncryptMessage() {
        return encryptMessage;
    }

    @Override
    public String toString() {
        return "WxConfig{" +
                "appid='" + appid + '\'' +
                ", secret='" + secret + '\'' +
                ", root='" + root + '\'' +
                ", token='" + token + '\'' +
                ", aeskey='" + aeskey + '\'' +
                ", encryptMessage=" + encryptMessage +
                '}';
    }

//    public void init() {
//        ApiConfig apiConfig = new ApiConfig(token, appid, secret);
//        ApiConfigKit.putApiConfig(apiConfig);
//
//        EventHandler.addCrypt("teapot", token, aeskey, appid);
//
//        EventHandler.wo.register(Command.EVENT_KEYS.SUBSCRIBE_EVENT, new SubscribeListener());
//        EventHandler.wo.register(Command.EVENT_KEYS.TEXT_MSG, new TextMessageListener());
//    }
}
