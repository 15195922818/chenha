package com.test;

import java.util.Iterator;

public class testScan {
	public static void main(String[] args) {
		String array[]={"1","2","3"};
		scan1:{
			for (int i = 0; i < array.length; i++) {
				System.out.println(array[i]);
				if("2".equals(array[i])){
					break scan1;
				}
			}
		}
		System.out.println("out");
	}
}
