package com.test;

public class testThread implements Runnable{
	
	private boolean running = true;

	public testThread(boolean running){
		this.running = running;
	}
	
	@Override
	public void run() {
		while(running){
			System.out.println(1);
		}
	}
	
	public static void main(String[] args) {
		new Thread(new testThread(false)).start();
	}
}
