package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class testThreadLocal {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new ThreadDemo01());
		service.execute(new ThreadDemo01());
	}
}

class ThreadDemo01 extends Thread{
	static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	static Map<String,String> map = new ConcurrentHashMap<>();
	public void run(){
		threadLocal.set("123");
		threadLocal.get();
		threadLocal.set("456");
		threadLocal.get();
		map.put("a", "a");
		map.put("a", "b");
	}
}