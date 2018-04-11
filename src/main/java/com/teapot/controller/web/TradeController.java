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
    public String doOrder(@RequestParam("wishId") Integer wishId, @RequestParam("payment") Double payment, HttpSession session) {
        String tempId = (String) session.getAttribute(SessionKeyContants.SESSION_TEMP_CUSTOMER);
        TbOrder order = orderService.newOrder(wishId ,tempId, payment);

        return "redirect:" + order.getId() + "/toPayment.html";
    }

    @RequestMapping("{orderId}/toPayment.html")
    public String toPayment(@PathVariable("orderId") Integer orderId, Model model){
        model.addAttribute("orderId", orderId);
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

    @RequestMapping(value = "{orderId}/wxpay")
    public String wxpay(@PathVariable("orderId") Integer orderId){

        TbOrder order = orderService.selectById(orderId);


        /*HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", "功德金");
        data.put("out_trade_no", alipayConfig.getTradePrefix() + order.getId());
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        Double money = (order.getMoney() / 100d);
        data.put("total_fee", money.toString());
        data.put("spbill_create_ip", getIpAddr());
        data.put("notify_url", "http://test.letiantian.me/wxpay/notify");
        data.put("trade_type", "MWEB");
//        data.put("product_id", "12");
        // data.put("time_expire", "20170112104120");

        try {
            Map<String, String> r = wxpay.unifiedOrder(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        String ip = getIpAddr();

        H5ScencInfo sceneInfo = new H5ScencInfo();

        H5ScencInfo.H5 h5_info = new H5ScencInfo.H5();
        h5_info.setType("Wap");
        //此域名必须在商户平台--"产品中心"--"开发配置"中添加
        h5_info.setWap_url("http://m.zijinwenchuang.com/");
        h5_info.setWap_name("大明寺紫砂文化馆");
        sceneInfo.setH5_info(h5_info);

        Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig()
                .setBody("功德金")
                .setSpbillCreateIp(ip)
                .setTotalFee(order.getMoney().toString())
                .setTradeType(WxPayApi.TradeType.MWEB)
                .setNotifyUrl(wx_notify_url)
                .setOutTradeNo(wxPrefix + order.getId())
                .setSceneInfo(h5_info.toString())
                .build();

        String xmlResult = WxPayApi.pushOrder(false,params);
//        log.info(xmlResult);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            logger.error(return_msg);
            return null;
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            logger.error(return_msg);
            return null;
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回

        String prepay_id = result.get("prepay_id");
        String mweb_url = result.get("mweb_url");

        logger.info("prepay_id:" + prepay_id + " mweb_url:" + mweb_url);
        return "redirect:" + mweb_url;
    }

    @RequestMapping(value = "wx_notify_url.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String pay_notify() {
/*        //获取所有的参数
        StringBuffer sbf=new StringBuffer();

        Enumeration<String> en=getParaNames();
        while (en.hasMoreElements()) {
            Object o= en.nextElement();
            sbf.append(o.toString()+"="+getPara(o.toString()));
        }

        log.error("支付通知参数："+sbf.toString());*/

        // 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
        String xmlMsg = HttpKit.readData(getHttpServletRequest());
        logger.info("支付通知=" + xmlMsg);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);

//		String appid  = params.get("appid");
//		//商户号
//		String mch_id  = params.get("mch_id");
        String result_code  = params.get("result_code");
//		String openId      = params.get("openid");
//		//交易类型
//		String trade_type      = params.get("trade_type");
//		//付款银行
//		String bank_type      = params.get("bank_type");
//		// 总金额
//		String total_fee     = params.get("total_fee");
//		//现金支付金额
//		String cash_fee     = params.get("cash_fee");
//		// 微信支付订单号
//		String transaction_id      = params.get("transaction_id");
//		// 商户订单号
//		String out_trade_no      = params.get("out_trade_no");
//		// 支付完成时间，格式为yyyyMMddHHmmss
//		String time_end      = params.get("time_end");

        /////////////////////////////以下是附加参数///////////////////////////////////

//        String attach      = params.get("attach");
//		String fee_type      = params.get("fee_type");
//		String is_subscribe      = params.get("is_subscribe");
//		String err_code      = params.get("err_code");
//		String err_code_des      = params.get("err_code_des");


        // 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
        // 避免已经成功、关闭、退款的订单被再次更新
//		Order order = Order.dao.getOrderByTransactionId(transaction_id);
//		if (order==null) {
        if (PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())) {
            if (("SUCCESS").equals(result_code)) {
                //更新订单信息
//                logger.warn("更新订单信息:" + attach);

                //发送通知等
                Map<String, String> xml = new HashMap<String, String>();
                xml.put("return_code", "SUCCESS");
                xml.put("return_msg", "OK");
                return PaymentKit.toXml(xml);
            }
        }
//		}
        return "";
    }
}
