package com.teapot.controller.web;

import com.teapot.controller.BaseController;
import com.teapot.utils.CreateImageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取图形验证码
 * 
 * @author yujing 2017年2月7日
 */
@Controller
@RequestMapping("/verify")
public class VerifyCodeController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 获取图形验证码
	 * 
	 * 
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getcode", method = RequestMethod.GET)
	public void getcode(@RequestParam("width") Integer width, 
								@RequestParam("height") Integer height, 
								HttpServletResponse resp) throws IOException {

		CreateImageCode image = new CreateImageCode(width, height, 4);

		logger.info("verifycode = {}", image.getCode());

		setVerifyCode(image.getCode());

		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		resp.setContentType("image/png");

		ImageIO.write(image.getBuffImg(), "png", getHttpServletResponse().getOutputStream());

		return;
	}
}
