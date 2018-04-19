package com.weixin.sdk;

import java.util.HashMap;
import java.util.Map;

public class WeiXinConfig {

    private static Map<String, String> config = new HashMap<String, String>();

    public static void init(String appId, String appSecret, String gcId, String token, String encodingAesKey){
        setAppId(appId);
        setAppSecret(appSecret);
        setFocusId(gcId);
        setToken(token);
        setEncodingAesKey(encodingAesKey);
    }

    public static void setAppId(String appId){
        config.put("app_id", appId);
    }

    public static void setAppSecret(String appSecret){
        config.put("app_secret", appSecret);
    }

    public static void setFocusId(String id){
        config.put("focus_url", "weixin://profile/" + id);
    }

    public static void setToken(String token){
        config.put("token", token);
    }

    public static String getAppId(){
        return config.get("app_id");
    }

    public static String getAppSecret(){
        return config.get("app_secret");
    }

    public static String getFocusUrl(){
        return config.get("focus_url");
    }

    public static String getToken(){
        return config.get("token");
    }

    public static void setEncodingAesKey(String encodingAesKey){
        config.put("encodingAesKey", encodingAesKey);
    }

    public static String getEncodingAesKey(){
        return config.get("encodingAesKey");
    }

    public static final String APP_ID = "wxba0a77ef0b45e8da";
    public static final String APP_SECRET = "2e9e978024b953b6df557af0622a59dd";

//    public static final String APP_ID = "wx12e9ba24f14539d5";
//    public static final String APP_SECRET = "a4f2ea3151c86e7165ce58f217c5f7c5";
    public static final String FOCUS_URL = "weixin://profile/gh_56f94717dcef";
}
