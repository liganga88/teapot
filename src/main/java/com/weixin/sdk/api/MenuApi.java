/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.weixin.sdk.api;

import com.teapot.utils.JsonUtils;
import com.weixin.sdk.WeiXinConfig;
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

    public static ApiResult getMenu(String token) {
        String jsonResult = HttpUtils.get(getMenu + token);
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

    public static ApiResult createMenu(String accessToken, String jsonStr) {
        String jsonResult = HttpUtils.post(createMenu + accessToken, jsonStr);
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

    public static ApiResult getCurrentSelfMenuInfo(String accessToken) {
        String jsonResult = HttpUtils.get(getCurrentSelfMenuInfoUrl + accessToken);
        return new ApiResult(jsonResult);
    }

    public static void main(String[] args) {

        //大明寺
//        AccessToken accessToken = AccessTokenApi.getAppAccessToken("wxba0a77ef0b45e8da", "2e9e978024b953b6df557af0622a59dd");
        //测试平台
        AccessToken accessToken = AccessTokenApi.getAppAccessToken("wx12e9ba24f14539d5", "a4f2ea3151c86e7165ce58f217c5f7c5");
//        deleteMenu(accessToken.getAccessToken());
        List<Map<String, Object>> menus = new ArrayList<>();
        List<Btn> carBtns1 = new ArrayList<>();
        carBtns1.add(Btn.newMedia("大明简介","p_4cjq7eL4YBwRdTCrPSXoHtJDwK3H6BfCAqYseZRzk"));
        carBtns1.add(Btn.newMedia("般若丈室","p_4cjq7eL4YBwRdTCrPSXoHtJDwK3H6BfCAqYseZRzk"));
        carBtns1.add(Btn.newMedia("联系我们","IYd2EWf04GEjZCRElQNkL-Ep5z_xf-E-DLP4uYwCQTU"));
        carBtns1.add(Btn.newMedia("大明律宗","p_4cjq7eL4YBwRdTCrPSXoHtJDwK3H6BfCAqYseZRzk"));
        carBtns1.add(Btn.newView("日行一善", "http://www.1foli.com/support/getSupportedTempleDetailWx?hostid=608"));
        Map<String, Object> m1 = new HashMap<>();
        m1.put("name", "寺院巡礼");
        m1.put("sub_button", carBtns1);
        menus.add(m1);

        List<Btn> carBtns2 = new ArrayList<>();
        carBtns2.add(Btn.newView("鉴真文化", "http://www.1foli.com/wxTempleNews/getNewsdetail/4817/1"));
        carBtns2.add(Btn.newMedia("佛学常识","p_4cjq7eL4YBwRdTCrPSXqTIhWxm9v3n8spdMIzULQs"));
        carBtns2.add(Btn.newMedia("佛门故事","p_4cjq7eL4YBwRdTCrPSXqTIhWxm9v3n8spdMIzULQs"));
        carBtns2.add(Btn.newMedia("皈依佛门","RE2sxz7s-MkotHHP9uB3WxSbGqI3tABDelLFbtux19k"));
        carBtns2.add(Btn.newMedia("方丈微语","p_4cjq7eL4YBwRdTCrPSXoHtJDwK3H6BfCAqYseZRzk"));
        Map<String, Object> m2 = new HashMap<>();
        m2.put("name", "佛教知识");
        m2.put("sub_button", carBtns2);
        menus.add(m2);

        List<Btn> carBtns3 = new ArrayList<>();
        carBtns3.add(Btn.newMedia("最新佛讯", "p_4cjq7eL4YBwRdTCrPSXrObQCdwJ_DcGeRiDErMV2c"));
        carBtns3.add(Btn.newMedia("鉴真医堂", "p_4cjq7eL4YBwRdTCrPSXrObQCdwJ_DcGeRiDErMV2c"));
        carBtns3.add(Btn.newMedia("大明慈善", "aY8wG9v4SdznpSlV0QT0_XZCyU5BNZH8xs1RFnFTDBc"));
        carBtns3.add(Btn.newMedia("法务公告", "CgniKJXUN2ssHrmNRT7jfOQCMnTT4bd8m6811AhQ_zQ"));
        carBtns3.add(Btn.newView("功德排名", "http://m.zijinwenchuang.com/customer/rank.html"));
        Map<String, Object> m3 = new HashMap<>();
        m3.put("name", "大明动态");
        m3.put("sub_button", carBtns3);
        menus.add(m3);
        Map<String, Object> map = new HashMap<>();
        map.put("button", menus);
        System.out.println(JsonUtils.objectToJson(map));
        Result result = createMenu(accessToken.getAccessToken(), menus);
        System.out.println(result.getErrcode() + ", " + result.getErrmsg());

//        ApiResult result = getMenu(accessToken.getAccessToken());

        /*ApiResult result = getCurrentSelfMenuInfo("8_0Emnsh_f4uI3Xb8Hm60iJnYwmWeWsPgOMe8wItX6zO4PXO_Nvhuln13gfTg9s3lGaJEmO82RBCnH0pabXx0NXZfwgk8U72hgZbXtYl_mqE5FtpYXjFhtqkKnn9_VeEIUCUJEp18kQQAaJfl4HPNcAHAYGE");
        System.out.println(result.toString());*/

    }
}


