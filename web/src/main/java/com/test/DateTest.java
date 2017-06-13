package com.test;

import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		long dl = Long.valueOf("1494817884000");
		System.out.println(dl);
		Date date = new Date(dl);
		System.out.println(date);
		
		long dl1 = Long.valueOf("1495990684000");
		System.out.println(dl1);
		Date date1 = new Date(dl1);
		System.out.println(date1);
		
	}
}
