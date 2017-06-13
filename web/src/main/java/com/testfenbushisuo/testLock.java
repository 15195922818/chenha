package com.testfenbushisuo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.nutz.ioc.val.SysPropValue;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;


public class testLock {
	
	public JedisCluster getRedis(){
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		//Jedis Cluster will attempt to discover cluster nodes automatically
		//只需要配置集群中的一个结点，连接成功后，自动获取点集群中其他结点信息
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7000));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7002));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7003));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7004));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7005));
		JedisCluster jc = new JedisCluster(jedisClusterNodes);
		return jc;
	}
	public void lock(){
        try{
            while(0==getRedis().setnx("syc_key1","1")){
            	Thread.currentThread().yield();
            }
            getRedis().expire("syc_key1", 30);
        }catch(Exception e){

        }
    }

    public void unlock(){
    	getRedis().del("syc_key1");
    }
    
    public void onlyOneExec(IOnlyOneExec iOnlyOneExec){
        try{
        	if(1==getRedis().setnx("syc_key1","1")){
            	getRedis().expire("syc_key1", 30);
                iOnlyOneExec.OnlyOneExec();
                getRedis().del("syc_key1");
            }
        }catch(Exception e){
        }
    }
	
	public static void main(String[] args) {
		/*testLock1 lock = new testLock1();
		IOnlyOneExec iOnlyOneExec = new IOnlyOneExec(){
			@Override
			public void OnlyOneExec() {
				System.out.println(1);
				System.out.println(2);
				System.out.println(3);
				System.out.println(4);
			}
		};
		lock.onlyOneExec(iOnlyOneExec);
		System.out.println(5);*/
		
		Boolean b = new testLock().getRedis().exists("syc_key1");
		System.out.println(b);
		new testLock().getRedis().del("syc_key1");
		Boolean b1 = new testLock().getRedis().exists("syc_key1");
		System.out.println(b1);
		
	}
}
