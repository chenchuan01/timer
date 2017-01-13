package com.yidingliu.dev;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yidingliu.dev.util.LogUtil;

/**
 * 任务执行管理
 * 
 * @author Administrator
 *
 */
public class PockerTaskExecutor {
	/**
	 * 任务队列
	 */
	private static List<PockerTimeTask> taskQueue;
	
	/**
	 * 队列上限
	 */
	private static final int MAX_WAIT_QUEUE=100;
	
    /**
     * 线程池
     */
    private ExecutorService threadPool;
    
    private long recieveTotal=0;
    private long executeTotal=0;
    
    /**
     * 实例化线程执行管理
     * @param maxThreads 线程池最大线程数
     */
    public PockerTaskExecutor() {
    	taskQueue = new ArrayList<>();
        threadPool = Executors.newCachedThreadPool();
        
        Manager manager = new Manager();
        Thread consumer  = new Thread(manager,"守护线程");
        consumer.start();
    }
    
    /**
     * @param task
     */
    public void receivedTask(long msce,String bakUrl){
    	recieveTotal++;
    	taskQueue.add(new PockerTimeTask(msce, bakUrl, recieveTotal));
    	LogUtil.info(getClass(), "接受定时任务总数=>【{0}】", recieveTotal);
    	synchronized (taskQueue) {
    		taskQueue.notify();
		}
    	
    }
    
    class Manager implements Runnable {
        public void run() {
            while (true) {
                try {
                    synchronized (taskQueue) {
                        while (taskQueue.isEmpty()||taskQueue.size()>MAX_WAIT_QUEUE) {
                        	taskQueue.wait();
                        }
                        PockerTimeTask task = taskQueue.remove(0);
                        executeTotal++;
                        threadPool.submit(task);
                        LogUtil.info(Manager.class,"执行第【{0}】个定时任务！",executeTotal);
                    }
                } catch (InterruptedException t) {
                    break;
                }
            }
            threadPool.shutdown();
        }
    }
   
}
