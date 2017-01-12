package com.yidingliu.dev.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

	/** 
	 * <p>标题: sendGet</p>	
	 * <p>说明: </p>	
	 * <p>作者: chenchuan
	 * <p>时间: 2017年1月12日</p>
	 * @param bakUrl
	 * @return
	 */
	public static String sendGet(String bakUrl,long serNum) {
		HttpURLConnection con = null;
		BufferedReader br = null;
		StringBuffer resultBuffer = new StringBuffer("");
		try {
			URL url = new URL(bakUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.connect();
			resultBuffer = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
			LogUtil.info(WebUtil.class, "序号=>[{0}];请求发送成功URL=>{1}",serNum, bakUrl);
		} catch (Exception e) {
			LogUtil.error(WebUtil.class, "WebUtil.sendGet", "请求发送失败URL=>{0}", bakUrl);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				} finally {
					if (con != null) {
						con.disconnect();
						con = null;
					}
				}
			}
		}
		return resultBuffer!=null?resultBuffer.toString():"";
	}
}
