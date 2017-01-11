package com.yidingliu.dev.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yidingliu.dev.PockerTaskExecutor;
import com.yidingliu.dev.PockerTimeTask;
import com.yidingliu.dev.util.WebUtil;

public class CenterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PockerTaskExecutor pockerTaskExecutor;
	
	private Integer maxThreads;
	
	public CenterController() {
		if(maxThreads==null){
			maxThreads=3;
		}
		pockerTaskExecutor= new PockerTaskExecutor(maxThreads);
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
		long   msec   = Long.valueOf(req.getParameter("msec"));
		String bakurl = req.getParameter("bakurl");
		pockerTaskExecutor.receivedTask(new PockerTimeTask(msec, bakurl));
		WebUtil.write(requestMapping,req,resp);
	}

	public Integer getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(Integer maxThreads) {
		this.maxThreads = maxThreads;
	}
	
}
