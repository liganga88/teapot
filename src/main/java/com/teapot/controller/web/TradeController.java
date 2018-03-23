package com.teapot.controller.web;

import com.alipay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-3-23.
 */
@Controller
@RequestMapping("/trade")
public class TradeController extends BaseController {

    @Autowired
    private AlipayConfig alipayConfig;

    @RequestMapping("alipay")
    @ResponseBody
    public String alipay(){
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", alipayConfig.getService());
        sParaTemp.put("partner", alipayConfig.getPartner());
        sParaTemp.put("seller_id", alipayConfig.getSellerId());
        sParaTemp.put("_input_charset", alipayConfig.getInputCharset());
        sParaTemp.put("payment_type", alipayConfig.getPaymentType());
        sParaTemp.put("notify_url", alipayConfig.getNotifyUrl());
        sParaTemp.put("return_url", alipayConfig.getReturnUrl());
        sParaTemp.put("anti_phishing_key", alipayConfig.getAntiPhishingKey());
        sParaTemp.put("exter_invoke_ip", alipayConfig.getExterInvokeIp());
        sParaTemp.put("it_b_pay", alipayConfig.getItBPay());
        sParaTemp.put("out_trade_no", getPara("t"));
        sParaTemp.put("subject", "纳车品交易");
        sParaTemp.put("total_fee", getPara("payment"));
        sParaTemp.put("body", "纳车品交易");
        // 其他业务参数根据在线开发文档，
        // 添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        // 如sParaTemp.put("参数名","参数值");

        // 建立请求
        String html = AlipaySubmit.buildRequest(sParaTemp, alipayConfig, "get", "确认");
        renderHtml(html);
    }
}
