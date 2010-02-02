package com.cell.util.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


public class ThreadPool
{
    private class PriorityThreadFactory implements ThreadFactory
    {
    	private int 				_prio;
		private String 				_name;
		private AtomicInteger 		_threadNumber = new AtomicInteger(1);
		private ThreadGroup 		_group;

		public PriorityThreadFactory(String name, int prio)
    	{
    		_prio = prio;
    		_name = name;
    		_group = new ThreadGroup(_name);
    	}

		public Thread newThread(Runnable r)
		{
			Thread t = new Thread(_group,r);
			t.setName(_name+"-"+_threadNumber.getAndIncrement());
			t.setPriority(_prio);
			return t;
		}

		public ThreadGroup getGroup()
		{
			return _group;
		}
    }
    
    /** temp workaround for VM issue */
    private static final long 				MAX_DELAY	= Long.MAX_VALUE/1000000/2;
    
    final public String						name;
    
	private ThreadPoolExecutor 				gameThreadPool;
	private ScheduledThreadPoolExecutor 	gameScheduledThreadPool;
	
	private boolean 						shutdown;

	public ThreadPool(
			String pool_name,
			int scheduled_corePoolSize, 
			int threadpool_corePoolSize,
            int threadpool_maximumPoolSize)
	{
		this.name = pool_name;

		if (scheduled_corePoolSize>0) {
			gameScheduledThreadPool = new ScheduledThreadPoolExecutor(
					scheduled_corePoolSize, 
					new PriorityThreadFactory(name + " Scheduled ThreadPool", Thread.NORM_PRIORITY));
		}
		
		if (threadpool_corePoolSize>0) {
			gameThreadPool = new ThreadPoolExecutor(
					threadpool_corePoolSize, 
					threadpool_maximumPoolSize,
			        5L, 
			        TimeUnit.SECONDS,
			        new LinkedBlockingQueue<Runnable>(),
			        new PriorityThreadFactory(name + " ThreadPool", Thread.NORM_PRIORITY));
		}

		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				System.out.println("ThreadPool \"" + name + "\" : ShutdownHook running...");
				purge();
				shutdown();
			}
		});
	}
    
	public ThreadPool(String pool_name)
	{
		this(pool_name,
				Runtime.getRuntime().availableProcessors(),
				Runtime.getRuntime().availableProcessors(), 
				Runtime.getRuntime().availableProcessors()+1);
	}
	
	public ThreadPoolExecutor getExecutor() 
	{
		return gameThreadPool;
	}
	
	public ScheduledThreadPoolExecutor getScheduledExecutor() 
	{
		return gameScheduledThreadPool;
	}
	
    public static long validateDelay(long delay) 
    {
		if (delay < 0) {
			delay = 0;
		} else if (delay > MAX_DELAY) {
			delay = MAX_DELAY;
		}
		return delay;
	}

	public ScheduledFuture<?> schedule(Runnable r, long delay)
	{
		try
        {
            delay = ThreadPool.validateDelay(delay);
            return gameScheduledThreadPool.schedule(r, delay, TimeUnit.MILLISECONDS);
        }
		catch (RejectedExecutionException e)
        {
			e.printStackTrace();
            return null;
        }
    }

	public ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long initial, long period)
    {
        try
        {
        	period = ThreadPool.validateDelay(period);
            initial = ThreadPool.validateDelay(initial);
            return gameScheduledThreadPool.scheduleAtFixedRate(r, initial, period, TimeUnit.MILLISECONDS);
        }
        catch (RejectedExecutionException e)
        {
        	e.printStackTrace();
            return null;
        }
    }

	public void executeTask(Runnable r)
	{
		gameThreadPool.execute(r);
	}

	public String getStats()
	{
		String lines = "";
		
		if (gameScheduledThreadPool!=null)
		{
			lines += name + " Scheduled Thread Pool:"+"\n"+
             " |-    ActiveThreads : "+gameScheduledThreadPool.getActiveCount()+"\n"+
             " |-  getCorePoolSize : "+gameScheduledThreadPool.getCorePoolSize()+"\n"+
             " |-         PoolSize : "+gameScheduledThreadPool.getPoolSize()+"\n"+
             " |-  MaximumPoolSize : "+gameScheduledThreadPool.getMaximumPoolSize()+"\n"+
             " |-   CompletedTasks : "+gameScheduledThreadPool.getCompletedTaskCount()+"\n"+
             " |-   ScheduledTasks : "+(gameScheduledThreadPool.getTaskCount() - gameScheduledThreadPool.getCompletedTaskCount())+"\n"+
             " | -------\n";
		}
		
		if (gameThreadPool!=null)
		{
			lines += name + " Thread Pool:"+"\n"+
                " |-    ActiveThreads : "+gameThreadPool.getActiveCount()+"\n"+
                " |-  getCorePoolSize : "+gameThreadPool.getCorePoolSize()+"\n"+
                " |-  MaximumPoolSize : "+gameThreadPool.getMaximumPoolSize()+"\n"+
                " |-  LargestPoolSize : "+gameThreadPool.getLargestPoolSize()+"\n"+
                " |-         PoolSize : "+gameThreadPool.getPoolSize()+"\n"+
                " |-   CompletedTasks : "+gameThreadPool.getCompletedTaskCount()+"\n"+
                " |-      QueuedTasks : "+gameThreadPool.getQueue().size()+"\n"+
                " | -------\n";
		};
		
		return lines;
	}


	/**
	 *
	 */
	public void shutdown()
	{
		if (!shutdown) 
		{
			shutdown = true;
			
			try {
				if (gameScheduledThreadPool!=null) {
					gameScheduledThreadPool.awaitTermination(1, TimeUnit.SECONDS);
					gameScheduledThreadPool.shutdown();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			try {
				if (gameThreadPool!=null) {
					gameThreadPool.awaitTermination(1, TimeUnit.SECONDS);
					gameThreadPool.shutdown();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			System.out.println("ThreadPool \"" + name+ "\" : All Threads are now stoped");
		}
	}
	
	public void shutdownNow()
	{
		if (!shutdown) 
		{
			shutdown = true;
			
			try {
				if (gameScheduledThreadPool!=null) {
					gameScheduledThreadPool.awaitTermination(1, TimeUnit.SECONDS);
					gameScheduledThreadPool.shutdownNow();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			try {
				if (gameThreadPool!=null) {
					gameThreadPool.awaitTermination(1, TimeUnit.SECONDS);
					gameThreadPool.shutdownNow();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			System.out.println("ThreadPool \"" + name+ "\" : All Threads are now stoped");
		}
	}
	
	public boolean isShutdown()
	{
		return shutdown;
	}

	/**
	 * 尝试从工作队列移除所有已取消的Future任务。
	 */
	public void purge()
	{
		if (gameScheduledThreadPool != null) {
			gameScheduledThreadPool.purge();
		}
		if (gameThreadPool != null) {
			gameThreadPool.purge();
		}
	}
	
	
//	public static void main(String[] args)
//	{
//		final ThreadPool pool = new ThreadPool("test", 1, 1, 1);
//		
////		pool.executeTask(new Runnable(){
////			public void run() {
////				System.out.println("runtask 1");
////			}
////		});
////		pool.executeTask(new Runnable(){
////			public void run() {
////				System.out.println("runtask 1");
////			}
////		});
////		pool.executeTask(new Runnable(){
////			public void run() {
////				System.out.println("runtask 2" + (1 / 0));
////			}
////		});
////		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println(pool.getStats());
//		
//	}
	
	

}