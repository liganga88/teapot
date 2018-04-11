package com.teapot.controller.web;


import com.jpay.ext.kit.*;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApi.TradeType;
import com.jpay.weixin.api.WxPayApiConfig;
import com.jpay.weixin.api.WxPayApiConfig.PayModel;
import com.jpay.weixin.api.WxPayApiConfigKit;
import com.teapot.bean.H5ScencInfo;
import com.teapot.bean.H5ScencInfo.H5;
import com.teapot.bean.WxPayBean;
import com.teapot.contants.CouponTypeContants;
import com.teapot.contants.PayTypeContants;
import com.teapot.pojo.TbOrder;
import com.teapot.service.CouponService;
import com.teapot.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wxpay")
public class WxPayController extends WxPayApiController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxPayBean wxPayBean;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CouponService couponService;
	
	private String notify_url;

	@Override
	public WxPayApiConfig getApiConfig() {
		notify_url = wxPayBean.getDomain().concat("/wxpay/pay_notify");
		return WxPayApiConfig.New()
				.setAppId(wxPayBean.getAppId())
				.setMchId(wxPayBean.getMchId())
				.setPaternerKey(wxPayBean.getPartnerKey())
				.setPayModel(PayModel.BUSINESSMODEL);
	}

/*	@RequestMapping("")
	@ResponseBody
	public String index() {
		log.info("欢迎使用IJPay,商户模式下微信支付 - by Javen");
		log.info(wxPayBean.toString());
		return ("欢迎使用IJPay 商户模式下微信支付  - by Javen");
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		return wxPayBean.toString();
	}
	
	@RequestMapping("/getKey")
	@ResponseBody
	public String getKey(){
		return WxPayApi.getsignkey(wxPayBean.getAppId(), wxPayBean.getPartnerKey());
	}*/
	
	/**
	 * 微信H5 支付
	 * 注意：必须再web页面中发起支付且域名已添加到开发配置中
	 */
	@RequestMapping(value ="{orderId}/wapPay",method = {RequestMethod.POST, RequestMethod.GET})
	public void wapPay(@PathVariable("orderId") Integer orderId,HttpServletRequest request,HttpServletResponse response){
		TbOrder order = orderService.selectById(orderId);
		String ip = getIpAddr();
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		
		H5ScencInfo sceneInfo = new H5ScencInfo();
		
		H5 h5_info = new H5();
		h5_info.setType("Wap");
		//此域名必须在商户平台--"产品中心"--"开发配置"中添加

		h5_info.setWap_url("http://m.zijinwenchuang.com");
		h5_info.setWap_name("大明寺紫砂文化馆");
		sceneInfo.setH5_info(h5_info);
		
		Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig()
				.setBody("功德金")
				.setSpbillCreateIp(ip)
				.setTotalFee(order.getMoney().toString())
				.setTradeType(TradeType.MWEB)
				.setNotifyUrl(notify_url)
				.setOutTradeNo(wxPayBean.getPrefix() + order.getId())
				.setSceneInfo(h5_info.toString())
				.build();
		
		String xmlResult = WxPayApi.pushOrder(false, params);
log.info(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			log.error("return_code>"+return_code+" return_msg>"+return_msg);
			throw new RuntimeException(return_msg);
		}
		String result_code = result.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			log.error("result_code>"+result_code+" return_msg>"+return_msg);
			throw new RuntimeException(return_msg);
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
		
		String prepay_id = result.get("prepay_id");
		String mweb_url = result.get("mweb_url");

		//付款后跳转的页面
		String return_url = wxPayBean.getDomain() + "/wish/" + order.getWishid() + "/resultA.html";
		try {
			return_url = URLEncoder.encode(return_url,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		mweb_url = mweb_url + "&redirect_url=" + return_url;

		log.info("prepay_id:" + prepay_id + " mweb_url:" + mweb_url);
		try {
			response.sendRedirect(mweb_url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/pay_notify",method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String pay_notify(HttpServletRequest request) {
		// 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7

		String xmlMsg = HttpKit.readData(request);
		System.out.println("支付通知="+xmlMsg);
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
		// 微信支付订单号
		String transaction_id      = params.get("transaction_id");
		// 商户订单号
		String out_trade_no      = params.get("out_trade_no");
//		// 支付完成时间，格式为yyyyMMddHHmmss
//		String time_end      = params.get("time_end");
		
		/////////////////////////////以下是附加参数///////////////////////////////////
		
		String attach      = params.get("attach");
//		String fee_type      = params.get("fee_type");
//		String is_subscribe      = params.get("is_subscribe");
//		String err_code      = params.get("err_code");
//		String err_code_des      = params.get("err_code_des");
		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 避免已经成功、关闭、退款的订单被再次更新
		TbOrder order = orderService.selectByPayNo(transaction_id);
		if (order==null) {
			if(PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())){
				if (("SUCCESS").equals(result_code)) {

					orderService.paid(transaction_id, out_trade_no, PayTypeContants.ALIPAY);
					couponService.newCoupon(out_trade_no, CouponTypeContants.CHATANG);

					//更新订单信息
					log.warn("更新订单信息:"+attach);
					//发送通知等
					Map<String, String> xml = new HashMap<String, String>();
					xml.put("return_code", "SUCCESS");
					xml.put("return_msg", "OK");
					return PaymentKit.toXml(xml);
				}
			}
		}

		return null;
	}

}
