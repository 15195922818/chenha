package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class testProductAndConsume {
	public static void main(String[] args) {
		LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();
		List<String> list = new ArrayList<String>();
		for(int i=0;i<50;i++)
		{
			list.add(String.valueOf(i));
		}
		producer1 p1 = new producer1(blockingQueue,list,1);
		producer1 p2 = new producer1(blockingQueue,list,2);
		producer1 p3 = new producer1(blockingQueue,list,3);
		producer1 p4 = new producer1(blockingQueue,list,4);
		producer1 p5 = new producer1(blockingQueue,list,5);
		consumer c1 = new consumer(blockingQueue,1);
		consumer c2 = new consumer(blockingQueue,2);
		consumer c3 = new consumer(blockingQueue,3);
		consumer c4 = new consumer(blockingQueue,4);
		consumer c5 = new consumer(blockingQueue,5);
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(p1);
		service.execute(p2);
		service.execute(p3);
		service.execute(p4);
		service.execute(p5);
		service.execute(c1);
		service.execute(c2);
		service.execute(c3);
		service.execute(c4);
		service.execute(c5);
	}
}

class producer1 extends Thread{
	
	private BlockingQueue<String> blockingQueue;
	
	private List<String> list;
	
	private int index;
	
	boolean flag = true;
	
	public producer1(){}
	
	public producer1(LinkedBlockingQueue<String> blockingQueue,List<String> list,int index){
		this.blockingQueue = blockingQueue;
		this.list = list;
		this.index = index;
	}
	
	public void run(){
		try {
			for(int i=(index-1)*10;i<index*10;i++)
			{
				System.out.println(this.currentThread().getName()+"线程，生产了:"+i);
				blockingQueue.put(list.get(i));
			}
			System.out.println(this.currentThread().getName()+"线程，生产完毕...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class consumer extends Thread{
	
	private LinkedBlockingQueue<String> blockingQueue;
	
	private final ReentrantLock takeLock = new ReentrantLock();
	
	private boolean flag = true;
	
	private int index;
	
	public consumer(){}
	
	public consumer(LinkedBlockingQueue<String> blockingQueue,int index){
		 this.blockingQueue = blockingQueue;
		 this.index = index;
	}
	
	public void run(){
		String good;
		while(flag){
			synchronized(Object.class)
			{
				if((good = blockingQueue.peek()) != null){
					System.out.println(this.currentThread().getName() +"线程,消费了:" + good);
					blockingQueue.poll();
					
				}else{
					try {
						Thread.sleep(5000);
						if(blockingQueue.peek() == null)
						{
							flag = false;
							System.out.println(this.currentThread().getName()+"线程，消费完毕...");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}