package com.teapot.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    /*@RequestMapping(value = "/{mp}", method = {RequestMethod.POST, RequestMethod.GET})
    public void index(@RequestParam(value = "signature", required = false) String signature,
                      @RequestParam(value = "timestamp", required = false) String timestamp,
                      @RequestParam(value = "nonce", required = false) String nonce,
                      @RequestParam(value = "echostr", required = false) String echostr,
                      @RequestParam(value = "msg_signature", required = false) String msg_signature,
                      @Url(value = "mp", required = false) String mp,
                      @RequestParam(value = "echostr", required = false) String echostr,
                      @RequestParam(value = "echostr", required = false) String echostr,) throws IOException {
        if ("get".equalsIgnoreCase(getHttpServletRequest().getMethod())) {
            get();
            post();
        } else {
            post();
        }
    }

    private void get(@RequestParam(value = "signature", required = false) String signature,
                     @RequestParam(value = "timestamp", required = false) String timestamp,
                     @RequestParam(value = "nonce", required = false) String nonce,
                     @RequestParam(value = "echostr", required = false) String echostr) {

        // 如果是服务器配置请求，则配置服务器并返回
        if (StrKit.notBlank(echostr)) {
            logger.debug("微信验证开始:signature:{},timestamp:{},nonce:{},echostr:{}",signature,timestamp,nonce,echostr);
            boolean isOk = SignatureCheckKit.me.checkSignature(signature, timestamp, nonce);
            if (isOk)
                renderText(echostr);
            else
                logger.error("验证失败：configServer");
        }
    }

    private void post() throws IOException {
        String mp = getUrlPara("mp", "");
        String signature = getPara("msg_signature");
        String timestamp = getPara("timestamp");
        String nonce = getPara("nonce");
        BufferedReader in = new BufferedReader(new InputStreamReader(getRequest().getInputStream()));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        logger.info(mp + ":" + sb.toString());
        String xml = EventHandler.wo.handler(mp, signature, timestamp, nonce, sb.toString());
        renderText(xml);
    }*/
}
