package com.test;

public class ThreadDemo04 extends Thread{
	
	private boolean flag;
	
	public ThreadDemo04(boolean flag){
		this.flag=flag;
	}
	
	static String a="1",b="2";
	public void run(){
		if(flag){
			synchronized(a){
				try {
					a.wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*synchronized(b){
					System.out.println("1");
				}*/
			}
		}
		
		if(!flag){
			synchronized(a){
				a.notifyAll();
			}
		}
	}
}