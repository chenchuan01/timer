package com.yidingliu.dev.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Filename LogUtil.java
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
 *          <li>Date: 2016年12月20日</li>
 *          <li>Version: 1.0</li>
 *          <li>Content: create</li>
 *
 */
public class LogUtil {
	private static Logger logger;

	/**
	 * info级别
	 * 
	 * @param clazz
	 * @param context
	 * @param params
	 */
	public static void info(Class<?> clazz, String context, Object... params) {
		logger(clazz);
		context = formatParam(context, params);
		logger.info(context, params);
	}


	/**
	 * error级别
	 * 
	 * @param clazz
	 * @param context
	 * @param params
	 */
	private static void error(Class<?> clazz, String context, Object... params) {
		logger(clazz);
		logger.error(context, params);
	}

	/**
	 * error级别（含方法名）
	 * 
	 * @param clazz
	 * @param methodName
	 * @param context
	 * @param params
	 */
	public static void error(Class<?> clazz, String methodName, String context, Object... params) {
		context = "methodName=>(" + methodName + ");error->" + context;
		error(clazz, context, params);
	}

	/**
	 * 记录异常
	 * 
	 * @param clazz
	 * @param context
	 * @param e
	 */
	public static void error(Class<?> clazz, String context, Exception e) {
		logger(clazz);
		logger.error(context, e);
	}

	public static Logger logger(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
		return logger;
	}

	/** 
	 * <p>标题: debug</p>	
	 * <p>说明: </p>	
	 * <p>作者: chenchuan
	 * <p>时间: 2016年12月30日</p>
	 * @param clazz
	 * @param msg
	 */
	public static void debug(Class<?> clazz, String msg) {
		logger(clazz);
		logger.debug(msg);
	}
	/** 
	 * <p>标题: debug</p>	
	 * <p>说明: </p>	
	 * <p>作者: chenchuan
	 * <p>时间: 2016年12月30日</p>
	 * @param clazz
	 * @param msg
	 */
	public static void debug(Class<?> clazz, String content,Object...param) {
		logger(clazz);
		logger.debug(formatParam(content, param));
	}


	private static String formatParam(String content, Object... param) {
		if(param!=null&&param.length>0
				&&content!=null&&content!=""){
			for (int i = 0; i < param.length; i++) {
				content=content.replaceAll("\\{"+i+"\\}", param[i].toString());
			}
		}
		return content;
	}

	
}
