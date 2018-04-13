/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.weixin.sdk.api;

import com.teapot.utils.JsonUtils;
import com.weixin.sdk.util.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * menu api
 */
public class MenuApi {

    private static String getMenu = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
    private static String createMenu = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    /**
     * 查询自定义菜单
     * @return {ApiResult}
     */
    public static ApiResult getMenu() {
        String jsonResult = HttpUtils.get(getMenu + AccessTokenApi.getAccessTokenStr());
        return new ApiResult(jsonResult);
    }

    /**
     * 创建自定义菜单
     * @param jsonStr json字符串
     * @return {ApiResult}
     */
    public static ApiResult createMenu(String jsonStr) {
        String jsonResult = HttpUtils.post(createMenu + AccessTokenApi.getAccessTokenStr(), jsonStr);
        return new ApiResult(jsonResult);
    }

    public static Result createMenu(String accessToken, Object btns) {
        Map<String, String> textMap = new HashMap<String, String>();
        textMap.put("access_token", accessToken);
        Map<String, Object> map = new HashMap<>();
        map.put("button", btns);
        String result = HttpUtils.post(createMenu + accessToken, JsonUtils.objectToJson(map));
        return JsonUtils.jsonToPojo(result, Result.class);
    }

    private static String deleteMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

    /**
     * 自定义菜单删除接口
     * @return ApiResult
     */
    public static ApiResult deleteMenu() {
        String jsonResult = HttpUtils.get(deleteMenuUrl + AccessTokenApi.getAccessTokenStr());
        return new ApiResult(jsonResult);
    }

    public static ApiResult deleteMenu(String accessToken) {
        String jsonResult = HttpUtils.get(deleteMenuUrl + accessToken);
        return new ApiResult(jsonResult);
    }

    private static String addConditionalUrl = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=";

    /**
     * 创建个性化菜单
     * @param jsonStr json字符串
     * @return {ApiResult}
     */
    public static ApiResult addConditional(String jsonStr) {
        String jsonResult = HttpUtils.post(addConditionalUrl + AccessTokenApi.getAccessTokenStr(), jsonStr);
        return new ApiResult(jsonResult);
    }

    private static String delConditionalUrl = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=";

    /**
     * 删除个性化菜单
     * @param menuid menuid为菜单id，可以通过自定义菜单查询接口获取。
     * @return ApiResult
     */
    public static ApiResult delConditional(String menuid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("menuid", menuid);

        String url = delConditionalUrl + AccessTokenApi.getAccessTokenStr();

        String jsonResult = HttpUtils.post(url, JsonUtils.objectToJson(params));
        return new ApiResult(jsonResult);
    }

    private static String tryMatchUrl = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=";

    /**
     * 测试个性化菜单匹配结果
     * @param userId user_id可以是粉丝的OpenID，也可以是粉丝的微信号。
     * @return ApiResult
     */
    public static ApiResult tryMatch(String userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", userId);

        String url = tryMatchUrl + AccessTokenApi.getAccessTokenStr();

        String jsonResult = HttpUtils.post(url, JsonUtils.objectToJson(params));
        return new ApiResult(jsonResult);
    }

    private static String getCurrentSelfMenuInfoUrl = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=";

    /**
     * 获取自定义菜单配置接口
     * @return {ApiResult}
     */
    public static ApiResult getCurrentSelfMenuInfo() {
        String jsonResult = HttpUtils.get(getCurrentSelfMenuInfoUrl + AccessTokenApi.getAccessTokenStr());
        return new ApiResult(jsonResult);
    }

    public static void main(String[] args) {

        AccessToken accessToken = AccessTokenApi.getAppAccessToken("wx2d4e33d0cc9a07a7", "c350bb97822a590d2cf10cd31a9cfa45");
//        deleteMenu(accessToken.getAccessToken());
        List<Map<String, Object>> menus = new ArrayList<>();
        Map<String, Object> m1 = new HashMap<>();
        m1.put("type", "view");
        m1.put("name", "供销平台");
        m1.put("url", "http://www.nachepin.com");
        menus.add(m1);
        Map<String, Object> m2 = new HashMap<>();
        m2.put("type", "click");
        m2.put("name", "联系我们");
        m2.put("key", "us");
        menus.add(m2);
        List<Btn> carBtns3 = new ArrayList<>();
        carBtns3.add(Btn.newView("绑定账号", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2d4e33d0cc9a07a7&redirect_uri=http://www.nachepin.com/weixin/user/toBind&response_type=code&scope=snsapi_base#wechat_redirect"));
        carBtns3.add(Btn.newView("推送设置", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2d4e33d0cc9a07a7&redirect_uri=http://www.nachepin.com/weixin/user/settings&response_type=code&scope=snsapi_base#wechat_redirect"));
        Map<String, Object> m3 = new HashMap<>();
        m3.put("name", "个人中心");
        m3.put("sub_button", carBtns3);
        menus.add(m3);
        Map<String, Object> map = new HashMap<>();
        map.put("button", menus);
        System.out.println(JsonUtils.objectToJson(map));
        Result result = createMenu(accessToken.getAccessToken(), menus);
        System.out.println(result.getErrcode() + ", " + result.getErrmsg());
    }
}


