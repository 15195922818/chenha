package com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class testArrayList<E> extends ArrayList{
	
	public E get1(E e){
		return e;
	}
	
	public testArrayList(){
		super();
	}
	
	public testArrayList(int index){
		super(index);
	}
	
	public static void main(String[] args) {
		new testArrayList().testToArray();
	}
	
	public void testToArray(){
		List list = new ArrayList(10);
		list.add(1);
		list.add(2);
		list.add(3);
		list.toArray();
	}
	
	public void testList(){
		List list = new ArrayList(10);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add("223");
		Object l = list.remove(2);
		
		List list1 = new ArrayList();
		list1.add("223");
		
		list.addAll(list);
		
		list.containsAll(list1);
		Iterator it = list.listIterator();
		testArrayList<Integer> t = new testArrayList<Integer>();
		t.add("222");
		t.get1(123);
		list.set(5, "555");
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
}
