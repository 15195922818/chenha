package com.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.threadtest.Consumer1;
import com.threadtest.Producer1;

public class demo1 {
	
	public void a(){
		
		//CuratorFramework curator = CuratorFrameworkFactory.newClient("zookeeper.codelast.com:2181", 
		//5000, 3000, new RetryNTimes(5, 1000));
		/*curator.start();
		String regContent = "192.168.1.5:2688";
		String zkRegPathPrefix = "/codelast/service-provider-";
		//TODO:
		curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
		.forPath(zkRegPathPrefix, regContent.getBytes("UTF-8"));*/
	}
	
		public static void main(String[] args) throws InterruptedException {
			// 声明一个容量为10的缓存队列
			BlockingQueue queue = new LinkedBlockingQueue(5);

			Producer1 producer = new Producer1(queue);
			Consumer1 consumer = new Consumer1(queue);
			
			Thread tp = new Thread(producer);
			Thread tc = new Thread(consumer);
			
			tp.start();
			tc.start();
			//producer.stop();
			
			/*// 借助Executors
			ExecutorService service = Executors.newCachedThreadPool();
			// 启动线程
			service.execute(consumer);
			service.execute(producer1);*/
		}



}