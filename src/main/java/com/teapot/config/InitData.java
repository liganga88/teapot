package com.teapot.config;

import com.teapot.bean.WxConfig;
import org.springframework.beans.factory.InitializingBean;
import com.teapot.wx.listener.SubscribeListener;
import com.teapot.wx.listener.TextMessageListener;
import com.weixin.sdk.api.ApiConfig;
import com.weixin.sdk.api.ApiConfigKit;
import com.weixin.sdk.command.Command;
import com.weixin.sdk.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/4/23.
 */
public class InitData implements InitializingBean {

    private WxConfig wxConfig;

    public WxConfig getWxConfig() {
        return wxConfig;
    }

    public void setWxConfig(WxConfig wxConfig) {
        this.wxConfig = wxConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initWeixin();
    }

        public void initWeixin() {
            System.out.println("initWeixin start");
            ApiConfig apiConfig = new ApiConfig(wxConfig.getToken(), wxConfig.getAppid(), wxConfig.getSecret());
            ApiConfigKit.putApiConfig(apiConfig);

            EventHandler.addCrypt("teapot", wxConfig.getToken(), wxConfig.getAeskey(), wxConfig.getAppid());

            EventHandler.wo.register(Command.EVENT_KEYS.SUBSCRIBE_EVENT, new SubscribeListener());
            EventHandler.wo.register(Command.EVENT_KEYS.TEXT_MSG, new TextMessageListener());
            System.out.println("initWeixin end");
    }
}
