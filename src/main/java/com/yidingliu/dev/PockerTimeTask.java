package com.yidingliu.dev;

import java.util.Timer;
import java.util.TimerTask;

import com.yidingliu.dev.util.LogUtil;
import com.yidingliu.dev.util.WebUtil;

/**
 * 急速扑克定时任务
 * @author cc
 *
 */
public class PockerTimeTask implements Runnable{
	/**
	 * 定时毫秒
	 */
	private long msec;
	/**
	 * 回调请求地址
	 */
	private String bakUrl;
	/** 
	 * 序列号
	 */
	private Long seraNum;
	/**
	 * 定时器
	 */
	private Timer timer;
	public PockerTimeTask(long mseconds,String callbakUrl){
		msec  = mseconds;
		bakUrl= callbakUrl;
		timer = new Timer();
	}
	public PockerTimeTask(long mseconds,String callbakUrl,Long seralNum){
		msec  = mseconds;
		bakUrl= callbakUrl;
		timer = new Timer();
		seraNum=seralNum;
	}
	
	public void run() {
		final Thread pockerThread = Thread.currentThread();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				LogUtil.info(PockerTimeTask.class, 
						"线程:[{0}];发送Pocker请求[bakurl=>{1}]",
						pockerThread.getName(),
						bakUrl);
				String rslt=WebUtil.sendGet(bakUrl,seraNum);  
				if(rslt!=null&&rslt!=""){
					LogUtil.info(TimerTask.class, 
							"线程：[{0}];序号=>【{1}】请求响应[{2}]",pockerThread.getName(),seraNum,rslt);
				}
				
			}

		}, msec);
	}

}
