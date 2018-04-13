package com.teapot.controller.web;

import com.jpay.ext.kit.StrKit;
import com.teapot.controller.BaseController;
import com.weixin.sdk.handler.EventHandler;
import com.weixin.sdk.kit.SignatureCheckKit;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018-4-13.
 */
@Controller
@RequestMapping("/wx")
public class WxController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

/*    @RequestMapping("/")
    public void index() throws IOException {
        if ("get".equalsIgnoreCase(getHttpServletRequest().getMethod())) {
            get();
            post();
        } else {
            post();
        }
    }*/

    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(
            @RequestParam(name = "signature",
                    required = false) String signature,
            @RequestParam(name = "timestamp",
                    required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {

        // 如果是服务器配置请求，则配置服务器并返回
        if (StrKit.notBlank(echostr)) {
            logger.debug("微信验证开始:signature:{},timestamp:{},nonce:{},echostr:{}", new String[]{signature,timestamp,nonce,echostr});
            boolean isOk = SignatureCheckKit.me.checkSignature(signature, timestamp, nonce);
            if (isOk) {
                return echostr;
            }else {
                logger.error("验证失败：configServer");
                return  "非法请求";
            }

        }
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam(name = "encrypt_type",
                               required = false) String encType,
                       @RequestParam(name = "msg_signature",
                               required = false) String msgSignature) throws IOException{
   /*     String mp = getUrlPara("mp", "");
        String signature = getPara("msg_signature");
        String timestamp = getPara("timestamp");
        String nonce = getPara("nonce");*/
        BufferedReader in = new BufferedReader(new InputStreamReader(getHttpServletRequest().getInputStream()));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
//        logger.info(mp + ":" + sb.toString());
        String xml = EventHandler.wo.handler(requestBody, signature, timestamp, nonce, sb.toString());
        renderText(xml);
    }
}
