/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.weixin.sdk.api;

import com.weixin.sdk.cache.IAccessTokenCache;
import com.weixin.sdk.kit.ParaMap;
import com.weixin.sdk.util.HttpUtils;
import com.weixin.sdk.util.RetryUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 认证并获取 access_token API
 * http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token
 *
 * AccessToken默认存储于内存中，可设置存储于redis或者实现IAccessTokenCache到数据库中实现分布式可用
 *
 * 具体配置：
 * <pre>
 * ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache());
 * </pre>
 */
public class AccessTokenApi {

    // "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    /**
     * 从缓存中获取 access token，如果未取到或者 access token 不可用则先更新再获取
     * @return AccessToken accessToken
     */
    public static AccessToken getAccessToken() {
        ApiConfig ac = ApiConfigKit.getApiConfig();
        AccessToken result = getAvailableAccessToken(ac);
        if (result != null) {
            return result;
        }

        return refreshAccessTokenIfNecessary(ac);
    }

    private static AccessToken getAvailableAccessToken(ApiConfig ac) {
        // 利用 appId 与 accessToken 建立关联，支持多账户
        IAccessTokenCache accessTokenCache = ApiConfigKit.getAccessTokenCache();
        
        String accessTokenJson = accessTokenCache.get(ac.getAppId());
        if (StringUtils.isNotBlank(accessTokenJson)) {
            AccessToken result = new AccessToken(accessTokenJson);
            if (result != null && result.isAvailable()) {
                return result;
            }
        }
        return null;
    }

    /**
     * 直接获取 accessToken 字符串，方便使用
     * @return String accessToken
     */
    public static String getAccessTokenStr() {
        return getAccessToken().getAccessToken();
    }

    /**
     * synchronized 配合再次获取 token 并检测可用性，防止多线程重复刷新 token 值
     */
    private static synchronized AccessToken refreshAccessTokenIfNecessary(ApiConfig ac) {
        AccessToken result = getAvailableAccessToken(ac);
        if (result != null) {
            return result;
        }
        return refreshAccessToken(ac);
    }

    /**
     * 无条件强制更新 access token 值，不再对 cache 中的 token 进行判断
     * @return AccessToken
     */
    public static AccessToken refreshAccessToken() {
        return refreshAccessToken(ApiConfigKit.getApiConfig());
    }

    /**
     * 无条件强制更新 access token 值，不再对 cache 中的 token 进行判断
     * @param ac ApiConfig
     * @return AccessToken
     */
    public static AccessToken refreshAccessToken(ApiConfig ac) {
        String appId = ac.getAppId();
        String appSecret = ac.getAppSecret();
        final Map<String, String> queryParas = ParaMap.create("appid", appId).put("secret", appSecret).getData();

        // 最多三次请求
        AccessToken result = RetryUtils.retryOnException(3, new Callable<AccessToken>() {

            @Override
            public AccessToken call() throws Exception {
                String json = HttpUtils.get(url, queryParas);
                return new AccessToken(json);
            }
        });

        // 三次请求如果仍然返回了不可用的 access token 仍然 put 进去，便于上层通过 AccessToken 中的属性判断底层的情况
        if (null != result) {
            // 利用 appId 与 accessToken 建立关联，支持多账户
            IAccessTokenCache accessTokenCache = ApiConfigKit.getAccessTokenCache();
            accessTokenCache.set(ac.getAppId(), result.getCacheJson());
        }
        return result;
    }

    public static AccessToken getAppAccessToken(String appId, String appSecret){
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appId);
        params.put("secret", appSecret);
        String json = HttpUtils.get(url, params);
        return new AccessToken(json);
    }

}
