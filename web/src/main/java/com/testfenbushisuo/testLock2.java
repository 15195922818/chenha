package com.testfenbushisuo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

public class testLock2 {
	
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
                Thread.yield();
            }
            getRedis().expire("syc_key1", 30);
        }catch(Exception e){

        }
    }

    public void unlock(){
    	getRedis().del("syc_key1");
    }
	
	public static void main(String[] args) {
		testLock1 lock = new testLock1();
		lock.lock();
		System.out.println(1);
		System.out.println(2);
		System.out.println(3);
		System.out.println(4);
		lock.unlock();
		System.out.println(5);
	}
}
