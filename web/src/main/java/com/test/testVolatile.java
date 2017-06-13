package com.test;

public class testVolatile {
	
	public volatile static Boolean flag = false;
	
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(!testVolatile.flag){
					System.out.println("run");
				}
			}
		}).start();
		
		Thread.sleep(4000);
		flag = true;
		System.out.println("跑完了");
	}
}