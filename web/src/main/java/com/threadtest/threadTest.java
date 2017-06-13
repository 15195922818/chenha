package com.threadtest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class threadTest {
	public static void main(String[] args) 
	{
		/*BlockingQueue que = new LinkedBlockingQueue();
		producer p = new producer(que);
		consumer c = new consumer(que);
		Thread tp = new Thread(p);
		Thread tc = new Thread(c);
		tp.run();
		tc.run();*/
		BlockingQueue que = new LinkedBlockingQueue(1);
		boolean flag = que.offer("1");
		String a = (String) que.poll();
		boolean flag1 = que.offer("2");
		String a1 = (String)que.poll();
		boolean flag2 = que.offer("3");
		String a2 = (String)que.poll();
		boolean flag3 = que.offer("4");
		String a3 = (String)que.poll();
		System.out.println(flag);
		System.out.println(flag1);
		System.out.println(flag2);
		System.out.println(flag3);
		
		System.out.println(a);
		System.out.println(a1);
		System.out.println(a2);
		System.out.println(a3);
	}
}
