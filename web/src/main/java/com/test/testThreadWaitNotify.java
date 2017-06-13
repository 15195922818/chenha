package com.test;

import org.nutz.ioc.val.SysPropValue;

public class testThreadWaitNotify {
	public static void main(String[] args) {
		Thread1 thread1 = new Thread1();
		Thread2 thread2 = new Thread2(thread1);
		Thread t1 = new Thread(thread1);
		Thread t2 = new Thread(thread2);
		t1.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
	}
}

class Info2{
	private String syc;
}

class Thread1 extends Thread{
	
	private Thread2 thread2;
	
	private Info2 info;
	
	public Thread1(){}
	
	public Thread1(Info2 info){
		this.info=info;
	}
	
	public Thread1(Thread2 thread2){
		this.thread2=thread2;
	}
	
	public void run(){
		long time = System.currentTimeMillis();
		synchronized(this){
			while(true){
				long time1 = System.currentTimeMillis();
				long race = time1-time;
				//System.out.println("tbread1 执行时间:"+race);
				if(race>2000)
				{
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

class Thread2 extends Thread{
	private Thread1 thread1;
	
	private Info2 info;
	
	public Thread2(){}
	
	public Thread2(Info2 info){
		this.info=info;
	}
	
	public Thread2(Thread1 thread1){
		this.thread1=thread1;
	}
	
	public void run(){
		System.out.println("1");
		
		synchronized(thread1){
			thread1.notify();
		}
		System.out.println(this.thread1);
		System.out.println("2");
	}
}