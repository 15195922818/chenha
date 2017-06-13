package com.test;

import java.util.List;
import java.util.Vector;

public class testStack {
	public static void main(String args[]){
		thr t = new thr(testStackRoom.list);
		thr t1 = new thr(testStackRoom.list);
		thr t2 = new thr(testStackRoom.list);
		thr t3 = new thr(testStackRoom.list);
		Thread th1 = new Thread(t);
		Thread th2 = new Thread(t1);
		Thread th3 = new Thread(t2);
		Thread th4 = new Thread(t3);
		pro p = new pro(testStackRoom.list);
		Thread th5 = new Thread(p);
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		th5.start();
	}
}


class thr extends Thread{
	
	public List<String> list;
	
	public thr(List<String> list){
		this.list = list;
	}
	
	public void run(){
		synchronized(list){
			for(;;){
				while(list.size()==0){
					try {
						System.out.println("list容器空了，请稍等");
						testStackRoom.list.wait(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(int i=0;i<list.size();i++){
					System.out.println("线程"+Thread.currentThread().getName()+"消费了"+testStackRoom.list.get(0));
					testStackRoom.list.remove(0);
				}
			}
		}
	}
}

