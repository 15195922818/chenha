package com.chenha.enumtest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class enumTestClas {
	public static void main(String[] args) {
		enumTest a = enumTest.AFK;
		System.out.println(enumTest.valueOf("AFK1"));
	}
}

enum enumTest {
	OTHER(null),
	MEMORY("com.hansight.atom.collector.channel.MemoryChannel"),
	AFK("AKF"),
	CC("CC");
	
	private String name = "init";
	
	private enumTest(String a){
		//System.out.println(a);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
