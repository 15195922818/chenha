package com.test;

import java.util.HashMap;
import java.util.Map;

public class testMap {
	public static void main(String[] args) {
		Map<Object,Object> map = new HashMap<Object,Object>();
		en e = new en("小明",12);
		en e1 = new en("小红",23);
		map.put(e, "123");
		System.out.println(map.get(e));
		map.put(e1, "234");
		System.out.println(map.get(e1));
	}
}

class en{
	private String name;
	
	private int age;
	
	public en(String name,int age){
		this.name=name;
		this.age=age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}