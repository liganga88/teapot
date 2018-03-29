package com.teapot.ibeetl;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExt implements WebRenderExt {
	private final static long VERSION = System.currentTimeMillis();

	@Override
	public void modify(Template template, GroupTemplate arg1, HttpServletRequest arg2, HttpServletResponse arg3) {
		// js,css 的版本编号
		template.binding("version", VERSION);
	}
}