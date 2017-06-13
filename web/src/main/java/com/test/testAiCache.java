package com.test;

import com.asiainfo.appframe.ext.exeframe.cache.util.CacheUtil;

public class testAiCache {
	public static void main(String[] args) {
		CacheUtil cu = new CacheUtil();
		try {
			cu.writeString("String", "a", "aa");
			System.out.println(cu.readString("String", "a"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

