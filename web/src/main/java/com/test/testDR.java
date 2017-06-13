package com.test;

public class testDR {
	public static void main(String args[]){
		new testDR().test6();
	}
	public void test4(){
		int  x=6, y=10, k=5; 
		switch( x % y )  
		{  
			case 0:  k=x*y;
			case 6:  k=x/y;
			case 12:  k=x-y;
			default:  k=x*y-x;
		}
		System.out.println(k);
		System.out.println(6%10);
	}
	
	public void test5(){
		int i=10;
		do {
			i/=2; 
		} 
		while( i-- > 1 );
		System.out.println(i);
		System.out.println(1/2);
	}
	
	//杨辉三角
	public void test6(){
		for(int i=0;i<7;i++){
			String str = "";
			for(int f=i;f<7;f++){
				str += " ";
			}
			for(int j=0;j<i+1;j++){
				str +="* ";
			}
			System.out.println(str);
		}
	}
}
