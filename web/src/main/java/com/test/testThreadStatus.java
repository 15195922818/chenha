package com.test;


public class testThreadStatus {
	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable(){public void run(){}});
		thread.start();
		ThreadGroup group = Thread.currentThread().getThreadGroup();
        while(group != null) {
            Thread[] threads = new Thread[(int)(group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for(int i = 0; i < count; i++) {
            	System.out.println(threads[i].getId());
            }
            group = group.getParent();
        }


	}
}
