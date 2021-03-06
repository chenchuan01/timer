package com.yidingliu.dev.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yidingliu.dev.PockerTaskExecutor;
import com.yidingliu.dev.util.WebUtil;

public class CenterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String URI_STARTTIMER="startTimer";
	
	private PockerTaskExecutor pockerTaskExecutor;
	
	
	public CenterController() {
		pockerTaskExecutor= new PockerTaskExecutor();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		domain(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		domain(req, resp);
	}
	
	/**
	 * domain
	 * @param req 
	 * 	msec 毫秒
	 * 	bakurl 回调请求
	 * @param resp
	 */
	private void domain(HttpServletRequest req, HttpServletResponse resp){
		String requestMapping = WebUtil.requestMapping(req);
		if(URI_STARTTIMER.equalsIgnoreCase(requestMapping)){
			long   msec   = Long.valueOf(req.getParameter("msec"));
			String bakurl = req.getParameter("bakurl");
			pockerTaskExecutor.receivedTask(msec, bakurl);
		}
		WebUtil.write("success",req,resp);
	}
}
