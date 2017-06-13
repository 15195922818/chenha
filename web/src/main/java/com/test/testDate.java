package com.test;

import java.util.Calendar;
import java.util.Date;

public class testDate {
	public static void main(String args[]){
		 Calendar c=Calendar.getInstance();
         c.setTime(new Date());
         c.get(Calendar.HOUR);
         System.out.println(c.getTime().toString());
         c.get(Calendar.HOUR_OF_DAY);
         System.out.println(c.getTime().toString());
	}
}
