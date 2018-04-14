package com.teapot.controller.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.config.AlipayConfig;
import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfigKit;
import com.teapot.bean.H5ScencInfo;
import com.teapot.bean.WxPayBean;
import com.teapot.contants.CouponTypeContants;
import com.teapot.contants.PayTypeContants;
import com.teapot.contants.SessionKeyContants;
import com.teapot.controller.BaseController;
import com.teapot.pojo.TbOrder;
import com.teapot.service.CouponService;
import com.teapot.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018-3-23.
 */
@Controller
@RequestMapping("/trade")
public class TradeController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    @Value("wxpay.notify_url")
    private String wx_notify_url;

    @Value("wxpay.trade.prefix")
    private String wxPrefix;

    /**
     * 下单处理
     * @param payment
     * @return
     */
    @RequestMapping("order")
    public String doOrder(@RequestParam("wishId") Integer wishId, @RequestParam("payment") Double payment,
                          @RequestParam(value = "bWeixin", required = false, defaultValue = "false") Boolean bWeixin, HttpSession session) {
        String tempId = (String) session.getAttribute(SessionKeyContants.SESSION_TEMP_CUSTOMER);
        TbOrder order = orderService.newOrder(wishId ,tempId, payment);

        String url;
        if (bWeixin) {
            url = "/wxpay/" + order.getId() + "/getCode";
        } else {
            url = "/toPayment.html?orderId=" + order.getId();
        }
        return "redirect:" + url;
    }

    @RequestMapping("toPayment.html")
    public String toPayment(@RequestParam("orderId") Integer orderId, Model model){
        TbOrder order = orderService.selectById(orderId);
        model.addAttribute("order", order);

        return "web/toPayment";
    }

    @RequestMapping(value = "{orderId}/alipay", produces = "text/html; charset=utf-8")
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
        Double money = (order.getMoney() / 100d);
        model.setTotalAmount(money.toString());
//        model.setTotalAmount("0.01");
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
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return form;
    }

    @RequestMapping("return_url.html")
    public String returnCheck(@RequestParam("trade_no") String tradeNo, @RequestParam("out_trade_no") String orderNo) {
        logger.info("支付宝同步回调开始:trade_no={},out_trade_no={}", tradeNo, orderNo);
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
            logger.info("支付宝同步回调验证错误:trade_no={},message={}", tradeNo, e.getMessage());
        }

        Integer orderId = Integer.parseInt(orderNo.replace(alipayConfig.getTradePrefix(), ""));
        TbOrder order = orderService.selectById(orderId);

        logger.info("支付宝同步回调结束:trade_no={},verify_result={}", tradeNo, verify_result);

        return  "redirect:/wish/" + order.getWishid() + "/resultA.html";

    }

    @RequestMapping(value = "notify_url.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String notifyCheck(@RequestParam("trade_no") String tradeNo, @RequestParam("out_trade_no") String orderNo,
                                  @RequestParam("trade_status") String tradeStatus) {
        logger.info("支付宝异步回调开始:trade_no={},out_trade_no={}, trade_status={}", new String[]{tradeNo, orderNo, tradeStatus});

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

        String result = null;
        if (verify_result) {
            if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
                orderService.paid(tradeNo, orderNo, PayTypeContants.ALIPAY);
                couponService.newCoupon(orderNo, CouponTypeContants.CHATANG);
            }

            result = "success";
        } else {
            result = "fail";
        }
        logger.info("支付宝异步回调结束:trade_no={},result={}", tradeNo, result);
        return result;
    }
}
