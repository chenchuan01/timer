package com.yidingliu.dev.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public class WebUtil {

	public static String requestMapping(HttpServletRequest req) {
		LogUtil.info(WebUtil.class,"接收到WEB请求[{0}]", req.getRequestURI());
		return req.getRequestURI().replace(req.getContextPath(),"");
	}

	public static void write(String rslt, HttpServletRequest res,HttpServletResponse resp) {
		
		LogUtil.info(WebUtil.class,"响应WEB请求[{0}]",res.getRequestURL());
		try {
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(rslt);
		} catch (IOException e) {
			LogUtil.error(WebUtil.class, "Request输出流I/O异常", e);
		}
	}

}
