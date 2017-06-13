package com.test;

public class testExtend extends a{
	
	String b;
	
	public void setB(String b){
		this.b = b;
	}
	
	public String getB(){
		return b;
	}
	
	public testExtend(){
		
	}
	
	public static void main(String[] args) {
		testExtend te = new testExtend();
		testInnerExtendB tieb = te.new testInnerExtendB();
		testInnerExtendC tiec = te.new testInnerExtendC();
		testInnerExtendD tied = te.new testInnerExtendD();
		testInnerExtendE tiee = te.new testInnerExtendE();
		String b = tieb.b("bbb");
		te.setB(b);
		System.out.println(b);
	}
	
	class testInnerExtendB extends b{
		
	}
	
	class testInnerExtendC extends c{
		
	}
	
	class testInnerExtendD extends d{
		
	}
	
	class testInnerExtendE extends e{
		
	}
}

class a{
	public String a(){
		System.out.println("a");
		return "a";
	}
}
class b{
	public String b(String str){
		System.out.println("b");
		return str;
	}
}
class c{
	public void c(){
		System.out.println("c");
	}
}
class d{
	public void d(){
		System.out.println("d");
	}
}
class e{
	public void e(){
		System.out.println("e");
	}
}