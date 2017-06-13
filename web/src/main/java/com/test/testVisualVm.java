package com.test;

import scala.collection.mutable.StringBuilder;


public class testVisualVm {
	public static void main(String[] args) {
		aaa a = new aaa();
		for(int i=0;i<30000;i++){
			Thread t = new Thread(a);
			t.start();
		}
	}
}

class aaa extends Thread{
	public void run(){
			long time1 = System.currentTimeMillis();
			StringBuffer a=new StringBuffer("");
			//String a = "";
			for(int i=0;i<10000;i++){
				a.append("a");
				//a+="abcdefghijklmnop";
			}
			long time2 = System.currentTimeMillis();
			System.out.println(Thread.currentThread().getName()+ "消耗时间为:" + (time2 - time1));
	}
}