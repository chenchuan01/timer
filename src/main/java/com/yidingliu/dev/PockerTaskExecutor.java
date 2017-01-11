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
     * 线程池
     */
    private ExecutorService threadPool;
    
    /**
     * 实例化线程执行管理
     * @param maxThreads 线程池最大线程数
     */
    public PockerTaskExecutor(int maxThreads) {
    	taskQueue = new ArrayList<>();
        threadPool = Executors.newFixedThreadPool(maxThreads);
        
        Manager manager = new Manager();
        Thread consumer  = new Thread(manager,"守护线程");
        consumer.start();
    }
    
    /**
     * @param task
     */
    public void receivedTask(PockerTimeTask task){
    	synchronized (taskQueue) {
    		taskQueue.add(task);
    		taskQueue.notify();
		}
    	
    }
    
    class Manager implements Runnable {
        int num = 0;
        public void run() {
            while (true) {
                try {
                    synchronized (taskQueue) {
                        while (taskQueue.isEmpty()) {
                        	taskQueue.wait();
                        }
                        LogUtil.info(PockerTaskExecutor.class,"定时任务队列的长度为:{0}",taskQueue.size());
                        PockerTimeTask task = taskQueue.remove(0);
                        num++;
                        LogUtil.info(PockerTaskExecutor.class,"成功从队列中取到定时任务！",num);
                        threadPool.execute(task);
                    }
                } catch (InterruptedException t) {
                    break;
                }
            }
            threadPool.shutdown();
        }
    }
   
}
