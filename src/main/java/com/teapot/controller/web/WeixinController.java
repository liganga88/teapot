package com.teapot.controller.web;

import com.teapot.bean.JsonResult;
import com.teapot.bean.WxConfig;
import com.teapot.contants.SessionKeyContants;
import com.teapot.controller.BaseController;
import com.weixin.sdk.api.SnsAccessToken;
import com.weixin.sdk.api.SnsAccessTokenApi;
import com.weixin.sdk.handler.EventHandler;
import com.weixin.sdk.kit.SignatureCheckKit;
import com.weixin.sdk.kit.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018-3-13.
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WeixinController.class);

    @Autowired
    private WxConfig wxConfig;


/*    @RequestMapping(value = "/{mp}", method = {RequestMethod.POST, RequestMethod.GET})
    public void index(HttpServletRequest request) throws IOException {
        if ("get".equalsIgnoreCase(getHttpServletRequest().getMethod())) {
            get(request);
            post();
        } else {
            post();
        }
    }*/

    @GetMapping(produces = "text/plain;charset=utf-8")
    @ResponseBody
    private String get(HttpServletRequest request) {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        // 如果是服务器配置请求，则配置服务器并返回
        if (StrKit.notBlank(echostr)) {
            logger.debug("微信验证开始:signature:{},timestamp:{},nonce:{},echostr:{}", new String[]{signature, timestamp, nonce, echostr});
            boolean isOk = SignatureCheckKit.me.checkSignature(signature, timestamp, nonce);
            if (isOk)
                return echostr;
            else
                logger.error("验证失败：configServer");
        }

        return "非法请求";
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    @ResponseBody
    private String post(@RequestBody String requestBody,
                      @RequestParam("signature") String signature,
                      @RequestParam("timestamp") String timestamp,
                      @RequestParam("nonce") String nonce,
                      @RequestParam(name = "encrypt_type",
                              required = false) String encType,
                      @RequestParam(name = "msg_signature",
                              required = false) String msgSignature) throws IOException {


        logger.info("signature={}&timestamp={}&nonce={}&encrypt_type={}&msg_signature={}&body={}",
                new String[]{signature,timestamp,nonce,encType,msgSignature,requestBody});
        String xml = EventHandler.wo.handler(msgSignature, timestamp, nonce, requestBody, wxConfig.getEncryptMessage());
        return xml;
    }

    /**
     * 获取openid并跳转网页
     * @param type  p1:首页
     * @return
     */
    @RequestMapping(value = "goUrl", method = {RequestMethod.POST, RequestMethod.GET})
    public String getCode(@RequestParam("type") String type){
        StringBuilder result = new StringBuilder();
        result.append("https://open.weixin.qq.com/connect/oauth2/authorize?")
                .append("appid=").append(wxConfig.getAppid())
                .append("&redirect_uri=").append(wxConfig.getRoot().concat("/getToken"))
                .append("&response_type=code&scope=snsapi_base")
                .append("&state=").append(type)
                .append("#wechat_redirect");
        return "redirect:" + result.toString();
    }

    @RequestMapping(value ="getToken",method = {RequestMethod.POST, RequestMethod.GET})
    public String getAccessToken(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session){
        SnsAccessToken accessToken = SnsAccessTokenApi.getSnsAccessToken(wxConfig.getAppid(), wxConfig.getSecret(), code);
        String openId = accessToken.getOpenid();
        session.setAttribute(SessionKeyContants.SESSION_OPENID, openId);
        logger.debug("获得openid:{}", openId);
        String url = "";
        //首页
        if ("p1".equals(state)) {
            url = "/";
        }
        return "redirect:" + url;
    }
}
