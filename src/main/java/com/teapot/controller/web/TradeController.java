package com.teapot.controller.web;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018-3-23.
 */
@Controller
@RequestMapping("/trade")
public class TradeController extends BaseController {

    @Autowired
    private AlipayConfig alipayConfig;

    @RequestMapping(value = "alipay", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String alipay(@RequestParam("tradeNo") String tradeNo, @RequestParam("payment") String payment){
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
        sParaTemp.put("out_trade_no", tradeNo);
        sParaTemp.put("subject", "纳车品交易");
        sParaTemp.put("total_fee", payment);
        sParaTemp.put("body", "纳车品交易");
        // 其他业务参数根据在线开发文档，
        // 添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        // 如sParaTemp.put("参数名","参数值");

        // 建立请求
        String html = AlipaySubmit.buildRequest(sParaTemp, alipayConfig, "get", "确认");
//        renderHtml(html);
        return html;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setPragma("no-cache");
//        headers.setCacheControl("no-cache");
//        headers.setExpires(0L);
//        MediaType type = new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
//        headers.setContentType(type);
//        return new ResponseEntity<String>(html,headers, HttpStatus.OK);
    }

    @RequestMapping("return_url.html")
    public String returnCheck(@RequestParam("trade_no") String tradeNo, @RequestParam("out_trade_no") String orderNo,
                              @RequestParam("total_fee") String totalFee, @RequestParam("buyer_email") String buyerEmail,
                              @RequestParam("trade_status") String tradeStatus) {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = getHttpServletRequest().getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        boolean dealResult = dealAlipayReturn(params, orderNo, totalFee, tradeStatus, tradeNo, buyerEmail);

        return  "redirect:/paySuccess";

    }

    @RequestMapping(value = "notify_url.html", produces = "text/plain; charset=utf-8")
    public String notifyCheck(@RequestParam("trade_no") String tradeNo, @RequestParam("out_trade_no") String orderNo,
                            @RequestParam("total_fee") String totalFee, @RequestParam("buyer_email") String buyerEmail,
                            @RequestParam("trade_status") String tradeStatus) {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = getHttpServletRequest().getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        boolean dealResult = dealAlipayReturn(params, orderNo, totalFee, tradeStatus, tradeNo, buyerEmail);

        return "success";
    }

    /**
     * 支付宝回调处理 (请不要修改)
     *
     * @param params        支付宝回调参数
     * @param orderNo       业务订单号
     * @param totalFee      金额
     * @param tradeStatus   支付宝交易状态
     * @param alipayTradeNo 支付宝交易号
     * @param buyerEmail    付款的支付宝帐号
     * @return 处理结果
     */
    private boolean dealAlipayReturn(Map<String, String> params, String orderNo, String totalFee, String tradeStatus,
                                     String alipayTradeNo, String buyerEmail) {
        boolean verifyResult = AlipayNotify.verify(params, alipayConfig);
        if (!verifyResult)
            return false;
//        String tradeIds = orderNo.replace(tradePrefix, "");
//        String[] strIds = tradeIds.split("_");
//
//        long alipayPayment = new BigDecimal(totalFee).multiply(new BigDecimal(100)).longValue();
//
//        // 计算得出通知验证结果
//        if ((tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS"))) {// 验证成功
//            for(String strId : strIds) {
//                Integer tid = Integer.parseInt(strId);
//                Trade trade = orderService.getTrade(tid);
//                if (StrKit.isBlank(trade.getAlipayNo())) {
//                    orderService.paid(tid, alipayTradeNo, buyerEmail);
//                }
//            }
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }
}
