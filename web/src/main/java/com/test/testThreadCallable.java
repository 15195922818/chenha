package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class testThreadCallable {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		Future<List> fs = service.submit(new ThreadCallAble());
		try {
			System.out.println(fs.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class ThreadCallAble implements Callable<List>{
	@Override
	public List call() throws Exception {
		List list = new ArrayList();
		list.add("2");
		return list;
	}
}