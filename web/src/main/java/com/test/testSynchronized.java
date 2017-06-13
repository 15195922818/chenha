package com.test;

public class testSynchronized implements Runnable{
	
	private testSynchronized t;
	
	private testSynchronized(){
		
	}
	
	private testSynchronized(testSynchronized t){
		this.t = t;
	}
	
	public void t(){
		synchronized(this){
			System.out.println(123);
			System.out.println(23);
		}
		System.out.println(555);
	}
	
	public static void main(String[] args) {
		testSynchronized t = new testSynchronized();
		testSynchronized tt = new testSynchronized();
		testSynchronized t1 = new testSynchronized(t);
		testSynchronized t2 = new testSynchronized(tt);
		new Thread(t1).start();
		new Thread(t2).start();
	}

	@Override
	public void run() {
		t.t();
	}
}
