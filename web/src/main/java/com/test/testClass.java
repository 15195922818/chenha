package com.test;

import java.util.HashMap;
import java.util.Map;

public class testClass {
	private static aa a=testClass1.getA();
	
	private static Map map = new HashMap();
	
	public void s(){
		System.out.println(a);
	}
	
	public static void main(String[] args) {
		new testClass();
		new testClass();
	}
}


class testClass1{
	static aa a;
	public static aa getA(){
		if(a==null){
			return new aa();
		}
		return a;
	}
}

class aa{}