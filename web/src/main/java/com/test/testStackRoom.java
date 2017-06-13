package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class testStackRoom {
	public static volatile List<String> list = new Vector<String>();
	
	static {
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("f");
		list.add("g");
		list.add("h");
		list.add("i");
		list.add("j");
		list.add("k");
		list.add("l");
		list.add("m");
		list.add("n");
	}
}
