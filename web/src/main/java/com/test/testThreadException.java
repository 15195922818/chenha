package com.test;


public class testThreadException {
	public static void main(String[] args) {
		Thread thread = new Thread(new ThreadDemoException());
		thread.setUncaughtExceptionHandler(new myUncaughtExceptionHandler());
		thread.start();
	}
}

class ThreadDemoException extends Thread{
	public void run(){
		String a[] = new String[1];
		a[0] = "1";
		a[1] = "2";
	}
}

class myUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if("java.lang.ArrayIndexOutOfBoundsException: 1".equals(e.toString()))
			System.out.println(e);
		if(!"java.lang.ArrayIndexOutOfBoundsException: 1".equals(e.toString()))
			System.out.println(2);
	}
}