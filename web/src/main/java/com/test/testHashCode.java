package com.test;

import java.util.HashSet;




public class testHashCode{
	public static void main(String args[]){
		new testHashCode().test();
	}
	
	public void test(){
		System.out.println(new String("111").hashCode());
		System.out.println(new testHashCode().hashCode());
		System.out.println(new HashSet().hashCode());
		System.out.println(new String("111") == (new String("111")));
		System.out.println(new String("111").hashCode() == new String("111").hashCode());
		System.out.println("".equals(new testHashCode()));
	}
}
