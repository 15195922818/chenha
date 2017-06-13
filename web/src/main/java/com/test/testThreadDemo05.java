package com.test;

public class testThreadDemo05 {
	public static void main(String[] args) {
		Info05 info05 = new Info05();
		info05.obj = "1";
		info05.thread052 = new Thread051(info05);
		Thread thread = new Thread(info05.thread052);
		thread.start();
		Info05 info052 = new Info05();
		info052.thread052 = new Thread051(info052);
		Thread thread1 = new Thread(info052.thread052);
		thread1.start();
		Info05 info051 = new Info05();
		info051.obj = "2";
		Thread thread2 = new Thread(info05.thread052);
		thread2.start();
	}
}

class Info05{
	Thread051 thread052;
	
	String obj;
}

class Thread051 extends Thread{
	
	public Thread051(){}
	
	public Thread051(Info05 info05){
		this.info = info05;
	}
	
	private Info05 info;
	
	public void run(){
		System.out.println(123);
		synchronized(info.thread052){
			System.out.println("info.obj");
		}
		
		synchronized(this){
			System.out.println("this");
		}
	}
}

class Thread052 extends Thread{
	
	public Thread052(){}
	
	public Thread052(Info05 info05){
		this.info = info05;
	}
	
	private Info05 info;
	
	public void run(){
		System.out.println(123);
		synchronized(info.obj){
			System.out.println("12");
		}
		
		synchronized(this){
			System.out.println("12");
		}
	}
}