package com.teapot.controller.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.config.AlipayConfig;
import com.teapot.contants.PayTypeContants;
import com.teapot.contants.SessionKeyContants;
import com.teapot.pojo.TbOrder;
import com.teapot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private OrderService orderService;

    /**
     * 下单处理
     * @param payment
     * @return
     */
    @RequestMapping("order")
    public String order(@RequestParam("payment") Double payment, HttpSession session, RedirectAttributes redirectAttributes){
        String tempId = (String) session.getAttribute(SessionKeyContants.SESSION_TEMP_CUSTOMER);
        TbOrder order = orderService.newOrder(tempId, payment);
        redirectAttributes.addFlashAttribute("orderId", order.getId());
        return "redirect:toPayment";
    }

    @RequestMapping("toPayment.hmtl")
    public String toPayment(@RequestParam("orderId") Integer orderId, Model model){
        model.addAttribute("orderId", orderId);
        return "toPayment";
    }

    @RequestMapping(value = "alipay", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String alipay(@PathVariable("orderId") Integer orderId){

        TbOrder order = orderService.selectById(orderId);

        // 销售产品码 必填
        String product_code = "QUICK_WAP_WAY";

        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(alipayConfig.getURL(), alipayConfig.getAppid(), alipayConfig.getRSA_PRIVATE_KEY(),
                alipayConfig.getFORMAT(), alipayConfig.getInputCharset(), alipayConfig.getALIPAY_PUBLIC_KEY(), alipayConfig.getSignType());
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(alipayConfig.getTradePrefix() + order.getId());
        model.setSubject("功德金");
        Double payment = order.getMoney() / 100d;
        model.setTotalAmount(payment.toString());
//        model.setBody(body);
        model.setTimeoutExpress(alipayConfig.getTimeoutExpress());
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(alipayConfig.getNotifyUrl());
        // 设置同步地址
        alipay_request.setReturnUrl(alipayConfig.getReturnUrl());

        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
//            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
//            response.getWriter().write(form);//直接将完整的表单html输出到页面
//            response.getWriter().flush();
//            response.getWriter().close();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return form;

        /*Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("app_id", alipayConfig.getAppid());

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
        return html;*/
//        HttpHeaders headers = new HttpHeaders();
//        headers.setPragma("no-cache");
//        headers.setCacheControl("no-cache");
//        headers.setExpires(0L);
//        MediaType type = new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
//        headers.setContentType(type);
//        return new ResponseEntity<String>(html,headers, HttpStatus.OK);
    }

    @RequestMapping("return_url.html")
    public String returnCheck(@RequestParam("trade_no") String tradeNo, @RequestParam("out_trade_no") String orderNo) {
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

        boolean verify_result = false;
        try {
            verify_result = AlipaySignature.rsaCheckV1(params, alipayConfig.getALIPAY_PUBLIC_KEY(), alipayConfig.getInputCharset(), alipayConfig.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return  "redirect:/paySuccess";

    }

    @RequestMapping(value = "notify_url.html", produces = "text/plain; charset=utf-8")
    public String notifyCheck(@RequestParam("trade_no") String tradeNo, @RequestParam("out_trade_no") String orderNo,
                                  @RequestParam("trade_status") String tradeStatus) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = getHttpServletRequest().getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
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

        boolean verify_result = false;
        try {
            verify_result = AlipaySignature.rsaCheckV1(params, alipayConfig.getALIPAY_PUBLIC_KEY(), alipayConfig.getInputCharset(), alipayConfig.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
//        boolean dealResult = dealAlipayReturn(params, orderNo, totalFee, tradeStatus, tradeNo, buyerEmail);
        if (verify_result) {
            if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
                orderService.paid(tradeNo, orderNo, PayTypeContants.ALIPAY);
            }

            return "success";
        } else {
            return "fail";
        }
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
//        boolean verifyResult = AlipayNotify.verify(params, alipayConfig);
//        if (!verifyResult)
//            return false;
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
