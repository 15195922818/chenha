package com.threadtest;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer1 implements Runnable{

	public Producer1(BlockingQueue queue){
		this.queue = queue;
	}

	boolean flag =true;
	
	/*@Override
	public void run() {
		while(true){
			int num = count.getAndIncrement();
			if(queue.size()==0)
			{
				queue.offer(num);
				System.out.println("生产data"+num+"成功!");
			}
			else
			{
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}*/
	
	public void run() {
		String data = null;
		Random r = new Random();

		System.out.println("启动生产者线程！");
			while (isRunning) {
				int num = count.incrementAndGet();
				data = "data:" + num;
				System.out.println("生产--------：" + data );
				try {
					if (!queue.offer(data,2, TimeUnit.SECONDS)) {
						System.out.println("放入数据失败：" + data);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void stop() {
			isRunning = false;
		}


		private volatile boolean isRunning = true;
		private BlockingQueue queue;
		private static AtomicInteger count = new AtomicInteger();
		private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
	
}
