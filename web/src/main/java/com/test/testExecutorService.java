package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class testExecutorService {

	public static void main(String[] args) {
		Info05 info05 = new Info05();
		info05.obj = "1";
		Thread thread = new Thread(new ThreadEx());
		Thread thread1 = new Thread(new ThreadEx());
		Thread thread2 = new Thread(new ThreadEx());
		Thread thread3 = new Thread(new ThreadEx());
		
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(thread);
		executorService.execute(thread1);
		executorService.execute(thread2);
		executorService.execute(thread3);
		System.out.println(123);
	}
}

class InfoEx{
	Thread051 thread051;
	
	String obj;
}

class ThreadEx extends Thread{
	
	public ThreadEx(){}
	
	public ThreadEx(InfoEx infoEx){
		this.info = infoEx;
	}
	
	private InfoEx info;
	
	public void run(){
		System.out.println(123);
		System.out.println("12");
	}
}
