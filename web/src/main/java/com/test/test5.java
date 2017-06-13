package com.test;

import java.io.Serializable;

public class test5 implements Serializable
{
	public static void main(String[] args) throws Exception
	{
	       new test5().getHandler("1","2");
	}

	public void getHandler(Object...a)
			throws Exception
	{
		Object[] c = a;
		System.out.println(c[0]);
	}
}
