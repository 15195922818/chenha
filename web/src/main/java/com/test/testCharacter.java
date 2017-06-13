package com.test;

public class testCharacter {
	public void toUpperCase(){
		long time = System.currentTimeMillis();
		Character ch=new Character('a');
		for(int i=0;i<1000000;i++){
			System.out.println(ch.toUpperCase(ch));
		};
		long time1 = System.currentTimeMillis();
		System.out.println("执行时间="+(time1-time));
	}
	
	public void toUpperCaseString(){
		long time = System.currentTimeMillis();
		String ch=new String("a");
		for(int i=0;i<1000000;i++){
			System.out.println(ch.toUpperCase());
		};
		long time1 = System.currentTimeMillis();
		System.out.println("执行时间="+(time1-time));
	}
	
	public static void main(String[] args) {
		new testCharacter().toUpperCase();
	}
}
