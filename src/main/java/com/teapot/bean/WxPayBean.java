package com.teapot.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/properties/wxpay.properties")
//@ComponentScan(basePackages = {"com.teapot.bean"})
public class WxPayBean {
	@Value("${wxpay.appid}")
    private String appId;
	@Value("${wxpay.appSecret}")
    private String appSecret;
	@Value("${wxpay.mchid}")
    private String mchId;
	@Value("${wxpay.key}")
    private String partnerKey;
	@Value("${wxpay.certPath}")
    private String certPath;
	@Value("${wxpay.domain}")
    private String domain;
	@Value("${wxpay.trade.prefix}")
	private String prefix;
    
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getPartnerKey() {
		return partnerKey;
	}
	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}
	public String getCertPath() {
		return certPath;
	}
	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String toString() {
		return "WxPayBean [appId=" + appId + ", appSecret=" + appSecret + ", mchId=" + mchId + ", partnerKey="
				+ partnerKey + ", certPath=" + certPath + ", domain=" + domain + "]";
	}
}
