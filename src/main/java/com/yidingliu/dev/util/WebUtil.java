package com.yidingliu.dev.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Filename WebUtil.java
 *
 * @Description
 *
 * @Version 1.0
 *
 * @Author chenchuan
 *
 * @Email 329985581@qq.com
 * 
 * @History
 * 			<li>Author: chenchuan</li>
 *          <li>Date: 2017年1月12日</li>
 *          <li>Version: 1.0</li>
 *          <li>Content: create</li>
 *
 */
public class WebUtil {

	/**
	 * <p>
	 * 标题: requestMapping
	 * </p>
	 * <p>
	 * 说明:
	 * </p>
	 * <p>
	 * 作者: chenchuan
	 * <p>
	 * 时间: 2017年1月12日
	 * </p>
	 * 
	 * @param req
	 * @return
	 */
	public static String requestMapping(HttpServletRequest req) {
		LogUtil.info(WebUtil.class, "接收到WEB请求[{0}]", req.getRequestURL());
		return req.getRequestURI().replace(req.getContextPath() + "/", "");
	}

	/**
	 * <p>
	 * 标题: write
	 * </p>
	 * <p>
	 * 说明:
	 * </p>
	 * <p>
	 * 作者: chenchuan
	 * <p>
	 * 时间: 2017年1月12日
	 * </p>
	 * 
	 * @param rslt
	 * @param res
	 * @param resp
	 */
	public static void write(String rslt, HttpServletRequest res, HttpServletResponse resp) {

		LogUtil.info(WebUtil.class, "响应WEB请求[{0}]", res.getRequestURL());
		try {
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(rslt);
		} catch (IOException e) {
			LogUtil.error(WebUtil.class, "Request输出流I/O异常", e);
		}
	}


}
