package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testArray{
	
	public void removeArray(){
		String array[] = {"1","2","3"};
		int i=0;
		boolean flag = false;
		for(String arr:array)
		{
			if("2".equals(arr))
			{
				flag = true;
			}
			if(flag)
			{
				if(i+1>=array.length)
				{
					array[i]=null;
					break;
				}
				array[i]=array[i+1];
			}
			i++;
		}
		System.out.println(array);
	}
	
	public void testAsList(){
		String array[] = {"1","2","3"};
		List li = new ArrayList();
		li = Arrays.asList(array);
		System.out.println(li.get(1));
	}
	
	public void testCopy(){
		String array[] = new String[5];
		array[0]="1";
		array[1]="2";
		array[2]="3";
		String array1[] = new String[2];
		array1[0]="a";
		array1[1]="b";
		System.arraycopy(array1, 0, array, 3, 2);
		System.out.println(array1);
	}
	
	public static void main(String[] args) {
		new testArray().testCopy();
	}
}
