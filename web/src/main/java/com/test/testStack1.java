package com.test;

import java.util.List;

public class testStack1 {
	public static void main(String args[]){
		
	}
}

class pro extends Thread{
	
	public List<String> list;
	
	public pro(List<String> list){
		this.list = list;
	}
	
	public void run(){
		for(int i=0;i<2000;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testStackRoom.list.add(String.valueOf(i));
			System.out.println("容器添加一个元素:"+i);
		}
	}
}