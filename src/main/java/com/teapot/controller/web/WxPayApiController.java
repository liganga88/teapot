package com.teapot.controller.web;

import com.jpay.weixin.api.WxPayApiConfig;
import com.teapot.controller.BaseController;

public abstract class WxPayApiController extends BaseController {
	public abstract WxPayApiConfig getApiConfig();
}