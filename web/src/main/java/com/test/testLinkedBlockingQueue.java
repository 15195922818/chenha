package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class testLinkedBlockingQueue {
	
	public static void main(String[] args) {
		new testLinkedBlockingQueue().testLinkedBlockingQueue();
	}
	
	public void testLinkedBlockingQueue(){
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		List list = new ArrayList();
		try{
			queue.put("123");
			queue.put("234");
		}catch(InterruptedException e){
			
		}
		int a = queue.drainTo(list,2);
		System.out.println(a);
	}
}
