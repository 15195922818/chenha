package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sun.reflect.Reflection;

public class testConcurrentHashMap {
	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		Map<String,String> myConcurrentHashMap = new myConcurrentHashMap<String,String>();
		service.execute(new ThreadDemo02(myConcurrentHashMap));
		service.execute(new ThreadDemo03(myConcurrentHashMap));
	}
}
@SuppressWarnings("static-access")
class myConcurrentHashMap<K,V> extends ConcurrentHashMap<K,V> implements Map<K,V>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map<String,List<K>> hashMap;
	List<String> list;
	
	public myConcurrentHashMap(){
		hashMap = new HashMap<String,List<K>>();
	}

	@SuppressWarnings("unchecked")
	public V put(K k,V v){
		Class<Thread> clz = Reflection.getCallerClass();
		try {
			Thread thread  = (Thread) clz.newInstance();
			synchronized(k){
				if(super.get(k)==null || (hashMap.get(thread.currentThread().getName())==null?false:hashMap.get(thread.currentThread().getName()).contains(k)))
				{
					super.put(k, v);
					List<K> list = new ArrayList<K>();
					list.add(k);
					hashMap.put(thread.currentThread().getName().toString(), list);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return v;
	}
}
@SuppressWarnings("static-access")
class ThreadDemo02 extends Thread{
	static Map<String,String> map;
	public ThreadDemo02(){}
	
	public ThreadDemo02(Map<String,String> map){
		this.map = map;
	}
	
	public void run(){
		System.out.println(this.currentThread().getName());
		map.put("a", "a");
		map.put("a", "b");
	}
}

class ThreadDemo03 extends Thread{
	Map<String,String> map;
	public ThreadDemo03(){}
	
	public ThreadDemo03(Map<String,String> map){
		this.map = map;
	}
	
	@SuppressWarnings("static-access")
	public void run(){
		System.out.println(this.currentThread().getName());
		map.put("e", "f");
		map.put("a", "d");
	}
}