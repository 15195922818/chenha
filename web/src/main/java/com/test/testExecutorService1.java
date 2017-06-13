package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class testExecutorService1 {

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
