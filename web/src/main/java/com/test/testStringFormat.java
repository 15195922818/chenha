package com.test;

public class testStringFormat {
	public static void main(String[] args) {
		String str = String.format("%.1f %.1f", Float.valueOf("10.0"),40.1);
		System.out.println(str);
	}
}
