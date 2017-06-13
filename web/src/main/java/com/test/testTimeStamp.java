package com.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class testTimeStamp {
	public static void main(String[] args) {
		Date d = new Date();
		d.setTime(Long.valueOf("1497000753122"));
        System.out.println(d);
        /*long t = System.currentTimeMillis();
        List list = new ArrayList();
        int j=0;
        int f=0;
        for(int i=0;i<10000000;i++){
        	//if(1000<2000){
        		list.add(i+"123123123123213123123123123123123123123123123123123123123");
        	//}
        	if(j<8000000){
        		list.remove(f--);
        	}
        	if(j<10000000 && j>=9000000){
        		list.remove(f--);
        	}
        	j++;
        	f++;
        }
        long t1 = System.currentTimeMillis();
        System.out.println(t1-t);
        System.out.println(list.size());*/
	}
	
	public void a(){
		Calendar c = Calendar.getInstance();
        Date date = new Date();
        long startTime = 0;
        long endTime = date.getTime();

        //查询当天目前为止的时间到前一天的所有数据
        c.add(Calendar.DATE,-1);
        System.out.println("过去一天："+c.getTime());

        c = Calendar.getInstance();
        c.add(Calendar.DATE,-7);
        System.out.println("过去一天："+c.getTime());

        c = Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        System.out.println("过去一个月："+c.getTime());
	}
}
