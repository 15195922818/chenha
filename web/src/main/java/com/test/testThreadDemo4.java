package com.test;

public class testThreadDemo4 {
	public static void main(String[] args) {
		RunThread thr = new RunThread();
		Thread thread = new Thread(thr);
        thread.start();
        Thread thread1 = new Thread(new RunThread1(thr));
        thread1.start();
        Thread thread2 = new Thread(new RunThread2());
        thread2.start();
        try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        //thread.setRunFlag(false);
        System.out.println("flag is reseted: " + thr.isRunning());
	}
}

class RunThread extends Thread {
	
    private  boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunFlag(boolean flag) {
        isRunning = flag;
    }

    @Override
    public void run() {
        System.out.println("RunThread");
        boolean first = true;
        while(isRunning) {
            if (first) {
                //System.out.println("I'm in while...");
                //first = false;
            }
        }
        System.out.println("I'll go out.");
    }
}

class RunThread1 extends Thread {

	private RunThread runThread;
	
	public RunThread1(RunThread runThread){
		this.runThread = runThread;
	}
	
    @Override
    public void run() {
    	System.out.println("RunThread1");
    	//runThread.setRunFlag(false);
    }
}

class RunThread2 extends Thread {

    @Override
    public void run() {
    	System.out.println("RunThread2");
    }
}