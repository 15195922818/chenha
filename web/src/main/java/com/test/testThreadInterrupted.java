package com.test;

public class testThreadInterrupted {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Threadtest());
		t.start();
		Thread.sleep(3000);
		System.out.println("####################");
		System.out.println("#Thread interrupted#");
		System.out.println("####################");
		t.interrupt();
	}
}

class Threadtest implements Runnable{

	@Override
	public void run() {
		int i=0;
		Thread t = Thread.currentThread();
		while(!t.isInterrupted()){
			System.out.println("I am running");
			i++;
			/*if(i==100){
				System.out.println("I am stop");
				t.interrupt();
			}*/
		}
	}
	
}
