package com.yidingliu.dev;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
	private static Queue<PockerTimeTask> taskQueue;
	
    /**
     * 线程池
     */
    private ExecutorService threadPool;
    
    /**
     * 实例化线程执行管理
     * @param maxThreads 线程池最大线程数
     */
    public PockerTaskExecutor(int maxThreads) {
    	taskQueue = new ConcurrentLinkedQueue<>();
        threadPool = Executors.newFixedThreadPool(maxThreads);
        
        Manager manager = new Manager();
        Thread consumer  = new Thread(manager,"守护线程");
        consumer.start();
    }
    
    /**
     * @param task
     */
    public void receivedTask(PockerTimeTask task){
    		taskQueue.add(task);
    }
    
    class Manager implements Runnable {
        int num = 0;
        public void run() {
            while (true) {
                try {
                    while (!taskQueue.isEmpty()) {
                    	 LogUtil.info(PockerTaskExecutor.class,"定时任务队列的长度为:{0}",taskQueue.size());
                         PockerTimeTask task = taskQueue.poll();
                         num++;
                         LogUtil.info(PockerTaskExecutor.class,"成功从队列中取到定时任务！",num);
                         threadPool.execute(task);
                    }
                } catch (Exception t) {
                    break;
                }
            }
            threadPool.shutdown();
        }
    }
   
}
