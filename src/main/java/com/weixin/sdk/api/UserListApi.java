package com.weixin.sdk.api;

import com.teapot.utils.JsonUtils;
import com.weixin.sdk.api.dto.UserList;
import com.weixin.sdk.util.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class UserListApi {

    public static final String USER_LIST_API = "https://api.weixin.qq.com/cgi-bin/user/get";

    public static UserList getUserList(String accessToken, String openId) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        if (openId != null && !openId.equals("")) {
            params.put("next_openid", openId);
        }
        String result = HttpUtils.get(USER_LIST_API, params);
        return JsonUtils.jsonToPojo(result, UserList.class);
    }

    public static UserList getUserList(String openId) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", AccessTokenApi.getAccessTokenStr());
        if (openId != null && !openId.equals("")) {
            params.put("next_openid", openId);
        }
        String result = HttpUtils.get(USER_LIST_API, params);
        return JsonUtils.jsonToPojo(result, UserList.class);
    }

}
