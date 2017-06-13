package com.test;

import java.util.concurrent.CountDownLatch;

public class testCountDownLatch {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch l = new CountDownLatch(3);
		Thread t1 = new Thread(new a1(l));
		Thread t2 = new Thread(new a2(l));
		Thread t3 = new Thread(new a3(l));
		t1.start();
		t2.start();
		t3.start();
		l.await();
		System.out.println("开始吃饭");
	}
}

class a1 extends Thread{
	
	private CountDownLatch l;
	
	public a1(CountDownLatch l){
		this.l=l;
	}
	
	public void run(){
		System.out.println("爸爸需要3小时到");
		l.countDown();
	}
}

class a2 extends Thread{
	
	private CountDownLatch l;
	
	public a2(CountDownLatch l){
		this.l=l;
	}
	
	public void run(){
		System.out.println("妈妈需要2小时到");
		l.countDown();
	}
}

class a3 extends Thread{
	
	private CountDownLatch l;
	
	public a3(CountDownLatch l){
		this.l=l;
	}
	
	public void run(){
		System.out.println("我需要1小时到");
		l.countDown();
	}
}