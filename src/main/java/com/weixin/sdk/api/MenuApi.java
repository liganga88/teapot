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

        /*ApiConfig apiConfig = new ApiConfig("", WeiXinConfig.APP_ID,WeiXinConfig.APP_SECRET);
        ApiConfigKit.putApiConfig(apiConfig);
        String json = "{\"button\":[{\"name\":\"寺院巡礼\",\"sub_button\":{\"list\":[{\"type\":\"news\",\"name\":\"大明简介\",\"value\":\"p_4cjq7eL4YBwRdTCrPSXoHtJDwK3H6BfCAqYseZRzk\",\"news_info\":{\"list\":[{\"title\":\"【佛教常识】礼佛三拜之含义\",\"author\":\"\",\"digest\":\"礼佛三拜之含义：　　(1)折伏骄慢心(2)见贤思齐(3)忏除业障：所谓“礼佛一拜灭罪河沙”灭罪当先整肃\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS2KweicoibJChm8ooQEk1AgHWgN8vv3NuMV8twmIh7e00wX75kw8h7myhOIzlycz4obD1ibTTnUASazA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=1&sn=47c9d411c6345af025857d3416bc01fe&chksm=04582381332faa975096d27bfc9aef276a32fa2809cad24470d9d5ff9afb8e499ffc3daf8e99#rd\",\"source_url\":\"\"},{\"title\":\"【方丈微语】悲欣交集\",\"author\":\"\",\"digest\":\"悲欣交集逆多顺将至，失久得必来。命运总是悲喜交集、忧乐相伴、苦甜掺杂，正如波谷与涛头、冬凋与夏荣。平淡了，执\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0gLmUCoVPHia7G3p5pLy1pgMuBRST96losYBiajXFYSxtjBaufdskJiaic2ibZqR0VEsXt9wgqvxrPW5A\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=2&sn=c6f472d2752ae79baa9e423964d57c43&chksm=04582381332faa9785ccdf15afbfd69d9bf05d56603777ff9747223f1eba74818fc48039ee35#rd\",\"source_url\":\"\"},{\"title\":\"兰花献应供 心地吐芳馨\",\"author\":\"\",\"digest\":\"佛家讲究因果循环，而鲜花代表现世中的因，警示信众要行善事，这样就能得到诸佛菩萨的护佑，结善果、得福报。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS059U3509BafF6Hew8cNtibWIajU6GrYqo2S4xpwgSomwzPkmmytrMLahp9cjFzHksMCKBX7yJYkGQ\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=3&sn=46a5ab566f66025f563b72fc1944c111&chksm=04582381332faa975eb290f7b858995f2f4a8ee0b1baddba916bf98d9e671ef9d0e83000165f#rd\",\"source_url\":\"\"},{\"title\":\"【佛教故事】欣赏比拥有更重要\",\"author\":\"\",\"digest\":\"那是冬日寒冷的清晨。富人像平时一样，睡过温暖的一夜，吃过丰盛的早餐，独自到广大的花园散步。正如同所有的富人早\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0LGQgGqzGb44suySKqdtib1EYicjjkTwIV8nBIDkQ5J1bms1ZibYjbaqEDfNsvBTerL9BcJGcKg3CvA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=4&sn=9f0336635396e78a5c15b4ef635ce26a&chksm=04582381332faa974e154454f7a9ad83887ae2ea71083152903ab26cd07f24e15edaad12089c#rd\",\"source_url\":\"\"}]}},{\"type\":\"news\",\"name\":\"般若丈室\",\"value\":\"p_4cjq7eL4YBwRdTCrPSXoHtJDwK3H6BfCAqYseZRzk\",\"news_info\":{\"list\":[{\"title\":\"【佛教常识】礼佛三拜之含义\",\"author\":\"\",\"digest\":\"礼佛三拜之含义：　　(1)折伏骄慢心(2)见贤思齐(3)忏除业障：所谓“礼佛一拜灭罪河沙”灭罪当先整肃\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS2KweicoibJChm8ooQEk1AgHWgN8vv3NuMV8twmIh7e00wX75kw8h7myhOIzlycz4obD1ibTTnUASazA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=1&sn=47c9d411c6345af025857d3416bc01fe&chksm=04582381332faa975096d27bfc9aef276a32fa2809cad24470d9d5ff9afb8e499ffc3daf8e99#rd\",\"source_url\":\"\"},{\"title\":\"【方丈微语】悲欣交集\",\"author\":\"\",\"digest\":\"悲欣交集逆多顺将至，失久得必来。命运总是悲喜交集、忧乐相伴、苦甜掺杂，正如波谷与涛头、冬凋与夏荣。平淡了，执\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0gLmUCoVPHia7G3p5pLy1pgMuBRST96losYBiajXFYSxtjBaufdskJiaic2ibZqR0VEsXt9wgqvxrPW5A\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=2&sn=c6f472d2752ae79baa9e423964d57c43&chksm=04582381332faa9785ccdf15afbfd69d9bf05d56603777ff9747223f1eba74818fc48039ee35#rd\",\"source_url\":\"\"},{\"title\":\"兰花献应供 心地吐芳馨\",\"author\":\"\",\"digest\":\"佛家讲究因果循环，而鲜花代表现世中的因，警示信众要行善事，这样就能得到诸佛菩萨的护佑，结善果、得福报。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS059U3509BafF6Hew8cNtibWIajU6GrYqo2S4xpwgSomwzPkmmytrMLahp9cjFzHksMCKBX7yJYkGQ\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=3&sn=46a5ab566f66025f563b72fc1944c111&chksm=04582381332faa975eb290f7b858995f2f4a8ee0b1baddba916bf98d9e671ef9d0e83000165f#rd\",\"source_url\":\"\"},{\"title\":\"【佛教故事】欣赏比拥有更重要\",\"author\":\"\",\"digest\":\"那是冬日寒冷的清晨。富人像平时一样，睡过温暖的一夜，吃过丰盛的早餐，独自到广大的花园散步。正如同所有的富人早\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0LGQgGqzGb44suySKqdtib1EYicjjkTwIV8nBIDkQ5J1bms1ZibYjbaqEDfNsvBTerL9BcJGcKg3CvA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=4&sn=9f0336635396e78a5c15b4ef635ce26a&chksm=04582381332faa974e154454f7a9ad83887ae2ea71083152903ab26cd07f24e15edaad12089c#rd\",\"source_url\":\"\"}]}},{\"type\":\"news\",\"name\":\"联系我们\",\"value\":\"IYd2EWf04GEjZCRElQNkL-Ep5z_xf-E-DLP4uYwCQTU\",\"news_info\":{\"list\":[{\"title\":\"联系我们\",\"author\":\"扬州大明寺\",\"digest\":\"地址：江苏省扬州市维扬区平山堂东路8号\\n联系电话：0514-87331089\\n法务联系：仁轮法师 18936220009\\n网址：www.damingsi.com\",\"show_cover\":1,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS02bahqZ4n30oQiatsX7bsxBd1IMDibGziaz19q2ibrHRWrGtOY3SMlWABribUbN4mpW5icAZfQzeKFr8uA\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=200528019&idx=1&sn=2ceae03f5401bbfd09f4e1deb71b9f03#rd\",\"source_url\":\"http:\\/\\/www.damingsi.com\\/gongde-1.asp?lbid=30\"}]}},{\"type\":\"news\",\"name\":\"大明律宗\",\"value\":\"p_4cjq7eL4YBwRdTCrPSXoHtJDwK3H6BfCAqYseZRzk\",\"news_info\":{\"list\":[{\"title\":\"【佛教常识】礼佛三拜之含义\",\"author\":\"\",\"digest\":\"礼佛三拜之含义：　　(1)折伏骄慢心(2)见贤思齐(3)忏除业障：所谓“礼佛一拜灭罪河沙”灭罪当先整肃\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS2KweicoibJChm8ooQEk1AgHWgN8vv3NuMV8twmIh7e00wX75kw8h7myhOIzlycz4obD1ibTTnUASazA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=1&sn=47c9d411c6345af025857d3416bc01fe&chksm=04582381332faa975096d27bfc9aef276a32fa2809cad24470d9d5ff9afb8e499ffc3daf8e99#rd\",\"source_url\":\"\"},{\"title\":\"【方丈微语】悲欣交集\",\"author\":\"\",\"digest\":\"悲欣交集逆多顺将至，失久得必来。命运总是悲喜交集、忧乐相伴、苦甜掺杂，正如波谷与涛头、冬凋与夏荣。平淡了，执\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0gLmUCoVPHia7G3p5pLy1pgMuBRST96losYBiajXFYSxtjBaufdskJiaic2ibZqR0VEsXt9wgqvxrPW5A\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=2&sn=c6f472d2752ae79baa9e423964d57c43&chksm=04582381332faa9785ccdf15afbfd69d9bf05d56603777ff9747223f1eba74818fc48039ee35#rd\",\"source_url\":\"\"},{\"title\":\"兰花献应供 心地吐芳馨\",\"author\":\"\",\"digest\":\"佛家讲究因果循环，而鲜花代表现世中的因，警示信众要行善事，这样就能得到诸佛菩萨的护佑，结善果、得福报。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS059U3509BafF6Hew8cNtibWIajU6GrYqo2S4xpwgSomwzPkmmytrMLahp9cjFzHksMCKBX7yJYkGQ\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=3&sn=46a5ab566f66025f563b72fc1944c111&chksm=04582381332faa975eb290f7b858995f2f4a8ee0b1baddba916bf98d9e671ef9d0e83000165f#rd\",\"source_url\":\"\"},{\"title\":\"【佛教故事】欣赏比拥有更重要\",\"author\":\"\",\"digest\":\"那是冬日寒冷的清晨。富人像平时一样，睡过温暖的一夜，吃过丰盛的早餐，独自到广大的花园散步。正如同所有的富人早\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0LGQgGqzGb44suySKqdtib1EYicjjkTwIV8nBIDkQ5J1bms1ZibYjbaqEDfNsvBTerL9BcJGcKg3CvA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=4&sn=9f0336635396e78a5c15b4ef635ce26a&chksm=04582381332faa974e154454f7a9ad83887ae2ea71083152903ab26cd07f24e15edaad12089c#rd\",\"source_url\":\"\"}]}},{\"type\":\"view\",\"name\":\"日行一善\",\"url\":\"http:\\/\\/www.1foli.com\\/support\\/getSupportedTempleDetailWx?hostid=608\"}]}},{\"name\":\"佛教知识\",\"sub_button\":{\"list\":[{\"type\":\"view\",\"name\":\"鉴真文化\",\"url\":\"http:\\/\\/www.1foli.com\\/wxTempleNews\\/getNewsdetail\\/4817\\/1\"},{\"type\":\"news\",\"name\":\"佛学常识\",\"value\":\"p_4cjq7eL4YBwRdTCrPSXqTIhWxm9v3n8spdMIzULQs\",\"news_info\":{\"list\":[{\"title\":\"【佛教常识】“四大皆空”是哪“四大”？\",\"author\":\"\",\"digest\":\"根据《佛学教科书》解释：　　四大是指地、水、火、风等结合物体的四种元素。　　地大：以坚硬为性，能支持\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0nmk5dTgNP1WGyb3uJSKmJ1xQQ0eQp60pEmoXNkkmS8UPK6sFHeq2Vvq189UjWCB46YGcje2Twsw\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=1&sn=8ba992d69f684d6ba7040d7ba3657924&chksm=04582399332faa8fc4ef7a484aef97722748102156073388fcd97f0b287f9bc31458a4771d11#rd\",\"source_url\":\"\"},{\"title\":\"【佛教故事】 阿难的洗澡水\",\"author\":\"\",\"digest\":\"佛陀十大弟子之一 ——阿难，具庄严之相（ 佛陀三十二相，阿难三十相）。有次举行法会，佛陀带领许多弟子去受供，\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS3jthjh8SOyYA80LotHNT0ZPH19CyBqe1KibJKJkNMQfhiaSfxNCIaw8DGz9RmRiboFHj40ynolRXaYg\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=2&sn=8def5e602938bf705310915287ad107a&chksm=04582399332faa8f4e9fc20778aba45cb8e7cde53fb10d1868a300ab1ccc0e7b7762fdec2bbb#rd\",\"source_url\":\"\"},{\"title\":\"兰花献应供 心地吐芳馨\",\"author\":\"\",\"digest\":\"佛家讲究因果循环，而鲜花代表现世中的因，警示信众要行善事，这样就能得到诸佛菩萨的护佑，结善果、得福报。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS059U3509BafF6Hew8cNtibWgWF6dwrRAmq8KcTDlDaPEalsIfibMy7KhiaxueoCL43EkPpOsVEWYEZg\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=3&sn=2ac1cd833fcc51a28c8b61a3cf55f915&chksm=04582399332faa8f35bd648747753550833da0c6d286cac0e646f8cf0f5bccf9c2e0c02a2ff4#rd\",\"source_url\":\"\"},{\"title\":\"鉴真医堂：菠菜补铁？你对孩子的营养负责了吗？\",\"author\":\"\",\"digest\":\"大家一般都认为菠菜补铁，其实这是个误区。菠菜虽然含有铁元素，但是我们人体能吸收的量几乎等于零。如果您要想\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/4LvVDCD1oY8uNg2MpQyIqxflt3BYlMWzCw6MQSDumEV3F1Odc299EYtN6wmiaibVCnwQj6ibVGewsvgDjnfbTyHAA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=4&sn=35b63ebece952882099aa4aaf53962d4&chksm=04582399332faa8fca4f38e5c0e747b99afe797497be02a5bdc8d2b6abfcde167f7cd3aeb9d6#rd\",\"source_url\":\"\"}]}},{\"type\":\"news\",\"name\":\"佛门故事\",\"value\":\"p_4cjq7eL4YBwRdTCrPSXqTIhWxm9v3n8spdMIzULQs\",\"news_info\":{\"list\":[{\"title\":\"【佛教常识】“四大皆空”是哪“四大”？\",\"author\":\"\",\"digest\":\"根据《佛学教科书》解释：　　四大是指地、水、火、风等结合物体的四种元素。　　地大：以坚硬为性，能支持\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0nmk5dTgNP1WGyb3uJSKmJ1xQQ0eQp60pEmoXNkkmS8UPK6sFHeq2Vvq189UjWCB46YGcje2Twsw\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=1&sn=8ba992d69f684d6ba7040d7ba3657924&chksm=04582399332faa8fc4ef7a484aef97722748102156073388fcd97f0b287f9bc31458a4771d11#rd\",\"source_url\":\"\"},{\"title\":\"【佛教故事】 阿难的洗澡水\",\"author\":\"\",\"digest\":\"佛陀十大弟子之一 ——阿难，具庄严之相（ 佛陀三十二相，阿难三十相）。有次举行法会，佛陀带领许多弟子去受供，\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS3jthjh8SOyYA80LotHNT0ZPH19CyBqe1KibJKJkNMQfhiaSfxNCIaw8DGz9RmRiboFHj40ynolRXaYg\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=2&sn=8def5e602938bf705310915287ad107a&chksm=04582399332faa8f4e9fc20778aba45cb8e7cde53fb10d1868a300ab1ccc0e7b7762fdec2bbb#rd\",\"source_url\":\"\"},{\"title\":\"兰花献应供 心地吐芳馨\",\"author\":\"\",\"digest\":\"佛家讲究因果循环，而鲜花代表现世中的因，警示信众要行善事，这样就能得到诸佛菩萨的护佑，结善果、得福报。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS059U3509BafF6Hew8cNtibWgWF6dwrRAmq8KcTDlDaPEalsIfibMy7KhiaxueoCL43EkPpOsVEWYEZg\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=3&sn=2ac1cd833fcc51a28c8b61a3cf55f915&chksm=04582399332faa8f35bd648747753550833da0c6d286cac0e646f8cf0f5bccf9c2e0c02a2ff4#rd\",\"source_url\":\"\"},{\"title\":\"鉴真医堂：菠菜补铁？你对孩子的营养负责了吗？\",\"author\":\"\",\"digest\":\"大家一般都认为菠菜补铁，其实这是个误区。菠菜虽然含有铁元素，但是我们人体能吸收的量几乎等于零。如果您要想\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/4LvVDCD1oY8uNg2MpQyIqxflt3BYlMWzCw6MQSDumEV3F1Odc299EYtN6wmiaibVCnwQj6ibVGewsvgDjnfbTyHAA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=4&sn=35b63ebece952882099aa4aaf53962d4&chksm=04582399332faa8fca4f38e5c0e747b99afe797497be02a5bdc8d2b6abfcde167f7cd3aeb9d6#rd\",\"source_url\":\"\"}]}},{\"type\":\"news\",\"name\":\"皈依佛门\",\"value\":\"RE2sxz7s-MkotHHP9uB3WxSbGqI3tABDelLFbtux19k\",\"news_info\":{\"list\":[{\"title\":\"腊八授三皈\",\"author\":\"扬州大明寺\",\"digest\":\"唱赞礼佛上香授三皈依合十谛听  三皈依圆满，合影   2015年1月27日农历腊月初八，恰逢腊八节，大和尚慈\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS0rtCEQ72J3g9Hq4xj6wS2MsiamvVmOiaibv8pdMjP5UMviagyzvogakvltNI1oBA2OTQwN2IB89tpESg\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=202955261&idx=1&sn=ca63284287c190048abd5b8163bc8abf#rd\",\"source_url\":\"\"},{\"title\":\"佛教故事：花生的秘密\",\"author\":\"扬州大明寺\",\"digest\":\"有一个年轻人渴望自己能够成功,但是在他短短的人生旅途中已经遭受了接二连三的打击和挫折。他处于崩溃的边缘,几乎\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS0rtCEQ72J3g9Hq4xj6wS2MiaD7CyZa2Eib58aayuTLLN6JTWVS7gfiaoWBravatT3O2dSbkibrQ2IIBQ\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=202955261&idx=2&sn=cec98567ce7fb64b88ea879170d60e35#rd\",\"source_url\":\"\"},{\"title\":\"鉴真医堂：吃素这么好，为什么你的身体这么差？\",\"author\":\"扬州大明寺\",\"digest\":\"1、问题的由来　　在医堂经常遇到，好多吃素多年的朋友，出现了面色萎黄，声音低微，元气不足，浑身乏力，畏寒肢冷\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS0rtCEQ72J3g9Hq4xj6wS2Md6B2yU45VvY8PjgwJ5YxBnT3Evccb9h5uHrEVY61KTGTgerebNUzjg\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=202955261&idx=3&sn=998b6dfc6507d7acdaab8e6571221b60#rd\",\"source_url\":\"\"}]}},{\"type\":\"news\",\"name\":\"方丈微语\",\"value\":\"p_4cjq7eL4YBwRdTCrPSXoHtJDwK3H6BfCAqYseZRzk\",\"news_info\":{\"list\":[{\"title\":\"【佛教常识】礼佛三拜之含义\",\"author\":\"\",\"digest\":\"礼佛三拜之含义：　　(1)折伏骄慢心(2)见贤思齐(3)忏除业障：所谓“礼佛一拜灭罪河沙”灭罪当先整肃\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS2KweicoibJChm8ooQEk1AgHWgN8vv3NuMV8twmIh7e00wX75kw8h7myhOIzlycz4obD1ibTTnUASazA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=1&sn=47c9d411c6345af025857d3416bc01fe&chksm=04582381332faa975096d27bfc9aef276a32fa2809cad24470d9d5ff9afb8e499ffc3daf8e99#rd\",\"source_url\":\"\"},{\"title\":\"【方丈微语】悲欣交集\",\"author\":\"\",\"digest\":\"悲欣交集逆多顺将至，失久得必来。命运总是悲喜交集、忧乐相伴、苦甜掺杂，正如波谷与涛头、冬凋与夏荣。平淡了，执\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0gLmUCoVPHia7G3p5pLy1pgMuBRST96losYBiajXFYSxtjBaufdskJiaic2ibZqR0VEsXt9wgqvxrPW5A\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=2&sn=c6f472d2752ae79baa9e423964d57c43&chksm=04582381332faa9785ccdf15afbfd69d9bf05d56603777ff9747223f1eba74818fc48039ee35#rd\",\"source_url\":\"\"},{\"title\":\"兰花献应供 心地吐芳馨\",\"author\":\"\",\"digest\":\"佛家讲究因果循环，而鲜花代表现世中的因，警示信众要行善事，这样就能得到诸佛菩萨的护佑，结善果、得福报。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS059U3509BafF6Hew8cNtibWIajU6GrYqo2S4xpwgSomwzPkmmytrMLahp9cjFzHksMCKBX7yJYkGQ\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=3&sn=46a5ab566f66025f563b72fc1944c111&chksm=04582381332faa975eb290f7b858995f2f4a8ee0b1baddba916bf98d9e671ef9d0e83000165f#rd\",\"source_url\":\"\"},{\"title\":\"【佛教故事】欣赏比拥有更重要\",\"author\":\"\",\"digest\":\"那是冬日寒冷的清晨。富人像平时一样，睡过温暖的一夜，吃过丰盛的早餐，独自到广大的花园散步。正如同所有的富人早\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0LGQgGqzGb44suySKqdtib1EYicjjkTwIV8nBIDkQ5J1bms1ZibYjbaqEDfNsvBTerL9BcJGcKg3CvA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161363&idx=4&sn=9f0336635396e78a5c15b4ef635ce26a&chksm=04582381332faa974e154454f7a9ad83887ae2ea71083152903ab26cd07f24e15edaad12089c#rd\",\"source_url\":\"\"}]}}]}},{\"name\":\"大明动态\",\"sub_button\":{\"list\":[{\"type\":\"news\",\"name\":\"最新佛讯\",\"value\":\"p_4cjq7eL4YBwRdTCrPSXrObQCdwJ_DcGeRiDErMV2c\",\"news_info\":{\"list\":[{\"title\":\"【佛教常识】“回向”的意义\",\"author\":\"\",\"digest\":\"回向(梵语parinama)，又作回向、转向、施向。意谓回转自己所作的功德善根以趣向菩提，或往生净土，或施与\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS0C82eTBeB2b3lgnkAjS1PI3C8X0P0YQqEGYdZb485doOG2ZyQu6KpiaoAYfvXKx7mNFFlOUcS64bg\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161345&idx=1&sn=eed6801a7f96c6a77b7bfe9891189656&chksm=04582393332faa8571902500777d67823e901ee5d2f798a2fc2d57b22d3f2363f3a772a45428#rd\",\"source_url\":\"\"},{\"title\":\"【方丈微语】随缘顺势\",\"author\":\"\",\"digest\":\"随缘顺势随缘不是得过且过，因循苟且，而是尽人事听天命；顺势不是因陋就简，敷衍塞责，而是抓住恰当的机遇。适当放\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS2FqyK3y35xpWFW60bEic6jxUx613b2mzAiapP4uibuAj7dcjuVub4DbeiazqzhPCcZls5J9esvbw304Q\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161345&idx=2&sn=f0635eb7c5687b660c13ec2f679fa5ce&chksm=04582393332faa853d616ff18d308f60c9f9c7bdafdd06119b6b3371fc7183337064aea3760c#rd\",\"source_url\":\"\"},{\"title\":\"兰花献应供 心地吐芳馨\",\"author\":\"\",\"digest\":\"佛家讲究因果循环，而鲜花代表现世中的因，警示信众要行善事，这样就能得到诸佛菩萨的护佑，结善果、得福报。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS059U3509BafF6Hew8cNtibWVgntOonLqXrfp8rRFPGalraEQsoP9Se5WG09nT4YLWpx0P9bg6SIicQ\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161345&idx=3&sn=61c4009a4f2efaa7c4f7ab8fd8125fe8&chksm=04582393332faa855c82d63f0d54c8bf03603d8db2821190ff3ce58b30b5b4c436365381813f#rd\",\"source_url\":\"\"},{\"title\":\"佛教歌曲：心灯\",\"author\":\"\",\"digest\":\"点一盏心灯 照亮黑暗的心灵角落点一盏心灯 带来希望的每一分钟点一盏心灯 照亮黑暗的心灵角落点一盏心灯 带来希\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS3r4UhS5an7M13rYhpzmRsjcmTlribXfO0ANltQETryI33bHWibuxFUzoOlicB1ZCymwBXwJia1gvgicfw\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161345&idx=4&sn=1967c9006ad90cfd1192f7e2028763f5&chksm=04582393332faa85d5139c8d47ac20fa5d68c2e16d708da99914b6bec7756d3346ea56b4d037#rd\",\"source_url\":\"\"}]}},{\"type\":\"news\",\"name\":\"鉴真医堂\",\"value\":\"p_4cjq7eL4YBwRdTCrPSXqTIhWxm9v3n8spdMIzULQs\",\"news_info\":{\"list\":[{\"title\":\"【佛教常识】“四大皆空”是哪“四大”？\",\"author\":\"\",\"digest\":\"根据《佛学教科书》解释：　　四大是指地、水、火、风等结合物体的四种元素。　　地大：以坚硬为性，能支持\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS0nmk5dTgNP1WGyb3uJSKmJ1xQQ0eQp60pEmoXNkkmS8UPK6sFHeq2Vvq189UjWCB46YGcje2Twsw\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=1&sn=8ba992d69f684d6ba7040d7ba3657924&chksm=04582399332faa8fc4ef7a484aef97722748102156073388fcd97f0b287f9bc31458a4771d11#rd\",\"source_url\":\"\"},{\"title\":\"【佛教故事】 阿难的洗澡水\",\"author\":\"\",\"digest\":\"佛陀十大弟子之一 ——阿难，具庄严之相（ 佛陀三十二相，阿难三十相）。有次举行法会，佛陀带领许多弟子去受供，\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS3jthjh8SOyYA80LotHNT0ZPH19CyBqe1KibJKJkNMQfhiaSfxNCIaw8DGz9RmRiboFHj40ynolRXaYg\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=2&sn=8def5e602938bf705310915287ad107a&chksm=04582399332faa8f4e9fc20778aba45cb8e7cde53fb10d1868a300ab1ccc0e7b7762fdec2bbb#rd\",\"source_url\":\"\"},{\"title\":\"兰花献应供 心地吐芳馨\",\"author\":\"\",\"digest\":\"佛家讲究因果循环，而鲜花代表现世中的因，警示信众要行善事，这样就能得到诸佛菩萨的护佑，结善果、得福报。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/mjnTZ0koyS059U3509BafF6Hew8cNtibWgWF6dwrRAmq8KcTDlDaPEalsIfibMy7KhiaxueoCL43EkPpOsVEWYEZg\\/0?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=3&sn=2ac1cd833fcc51a28c8b61a3cf55f915&chksm=04582399332faa8f35bd648747753550833da0c6d286cac0e646f8cf0f5bccf9c2e0c02a2ff4#rd\",\"source_url\":\"\"},{\"title\":\"鉴真医堂：菠菜补铁？你对孩子的营养负责了吗？\",\"author\":\"\",\"digest\":\"大家一般都认为菠菜补铁，其实这是个误区。菠菜虽然含有铁元素，但是我们人体能吸收的量几乎等于零。如果您要想\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/4LvVDCD1oY8uNg2MpQyIqxflt3BYlMWzCw6MQSDumEV3F1Odc299EYtN6wmiaibVCnwQj6ibVGewsvgDjnfbTyHAA\\/640?wx_fmt=jpeg\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=504161355&idx=4&sn=35b63ebece952882099aa4aaf53962d4&chksm=04582399332faa8fca4f38e5c0e747b99afe797497be02a5bdc8d2b6abfcde167f7cd3aeb9d6#rd\",\"source_url\":\"\"}]}},{\"type\":\"news\",\"name\":\"大明慈善\",\"value\":\"aY8wG9v4SdznpSlV0QT0_XZCyU5BNZH8xs1RFnFTDBc\",\"news_info\":{\"list\":[{\"title\":\"大明动态：大明寺每月十五福慧放生祈福法会\",\"author\":\"扬州大明寺\",\"digest\":\"皈依三宝 远离三涂每月农历十五于大雄宝殿前举行福慧放生祈福法会，如有意愿，请联系客堂“仁轮法师”  欢迎广大\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS2l2enuEmXLLia9AoZMH1nebuTzkKLtRicrABmXOicOiblHic8UsnRRcicprD1FgRAICSHwQXGgPZ0nSjibA\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=203002372&idx=1&sn=eea6790489c4e97149c6266d7e05cc49#rd\",\"source_url\":\"\"},{\"title\":\"佛教常识：诵经\",\"author\":\"扬州大明寺\",\"digest\":\"凡是佛教徒都知道诵经，大部分的佛教徒都会诵一些经典。可是没有几个佛教徒懂诵经的窍诀，他们认为诵经就是照本宣科\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS2l2enuEmXLLia9AoZMH1nebByBeuXf1ZGFlPJE1oTScibDJo69BTn9ib4Cic6Y7n5QSBH2B0GlUAH31Q\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=203002372&idx=2&sn=9250d0e3fc0ffa7e3a9af68afd56e1e7#rd\",\"source_url\":\"\"},{\"title\":\"鉴真医堂：饭后不宜做的八件事情\",\"author\":\"扬州大明寺\",\"digest\":\" 1、饭后立即吃水果　　现象：很多人都喜欢饭后吃点水果，认为能够通过这个方式补充人体所需的碳水化合物和微量元\",\"show_cover\":1,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS2l2enuEmXLLia9AoZMH1nebr7U8GQtch2DxruspcRhKOpbXv2Vau68moKUXgKIcQdE8rXaO4EP3zw\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=203002372&idx=3&sn=3aa58ca0d4122c06ac3635e306c13b42#rd\",\"source_url\":\"\"}]}},{\"type\":\"news\",\"name\":\"法务公告\",\"value\":\"CgniKJXUN2ssHrmNRT7jfOQCMnTT4bd8m6811AhQ_zQ\",\"news_info\":{\"list\":[{\"title\":\"大明寺春节期间佛事活动预告——大供天\",\"author\":\"扬州大明寺\",\"digest\":\"齋天功德 佛教徒雖然不歸依諸天，卻禮敬諸天。這是因為諸天歸依佛，奉行正法，護持正教，並且修諸善業，不做惡行。\",\"show_cover\":0,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS0OqmicicTaY8JlRTTjVnUu1ZgeCTH7Jg2AA5HkdPJdhnxFKTiaibvib5iaRlZt86vx1qmvB7GEHiaq8r7RQ\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=203301166&idx=1&sn=f92816d525d05404f454ea4be7155c03#rd\",\"source_url\":\"\"},{\"title\":\"佛教故事：小河的流水\",\"author\":\"扬州大明寺\",\"digest\":\"佛陀旅行经过一个森林，那一天非常热，刚好在中午，他觉得口渴，所以他告诉他的弟子阿难：“我们刚走过一条小溪，你\",\"show_cover\":1,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS0OqmicicTaY8JlRTTjVnUu1ZLudsYQ13JYcmsujfvsKnGSHYWncIQ8rxUoTRJEWQReO61OkOicqicCFw\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=203301166&idx=2&sn=2d051d0c34abd2c77dcd3d400c25df82#rd\",\"source_url\":\"\"},{\"title\":\"鉴真医堂：修禅治病的六种方法\",\"author\":\"扬州大明寺\",\"digest\":\"修禅第一是修止法。修止法就是意守“忧陀那”(丹田)，《摩诃止观》卷八说：“丹田是气海，能锁吞万病，若止心丹田\",\"show_cover\":1,\"cover_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz\\/mjnTZ0koyS0OqmicicTaY8JlRTTjVnUu1ZWWf73G6WReVT9Oqj64vGHYVYRaYKqXzpwY1x7AuiazXjX6IPRfEA3tQ\\/0\",\"content_url\":\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MzA4MDY4NzgwNQ==&mid=203301166&idx=3&sn=e8bd01bfba45371743ec5466ecc78814#rd\",\"source_url\":\"\"}]}},{\"type\":\"view\",\"name\":\"功德排名\",\"url\":\"http:\\/\\/m.zijinwenchuang.com\\/customer\\/rank.html\"}]}}]}";
        ApiResult result = createMenu(json);*/
    }
}


