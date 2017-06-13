package com.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import redis.clients.jedis.HostAndPort;

import com.asiainfo.appframe.ext.exeframe.cache.redis.clients.jedis.Jedis;
import com.asiainfo.appframe.ext.exeframe.cache.redis.clients.jedis.JedisCluster;
import com.asiainfo.appframe.ext.exeframe.cache.redis.clients.jedis.JedisPool;



public class testRedis {
	
	static JedisCluster jc;
	
	public static void main(String[] args) {
		/*Jedis test = new Jedis("192.168.1.120",6379);
		test.set("hw","hello world");
		String hello = test.get("hw");
		System.out.println(hello);*/
		
		/*String host = "192.168.1.120";
		int port = Integer.parseInt("7009");
		String passwd = "";
		String group = "AOP_BASE";
		
		
		JedisShardInfo jsd = new JedisShardInfo(host, port);
			jsd.setPassword("123");
		Jedis jd = null;
	    jd = new Jedis(jsd);
	    jd.set(group+"AILK_REDIS_CONNECT_TEST", "TRUE");
	    System.out.println(123);*/
			//Boolean localBoolean1 = Boolean.TRUE;
		
		
		
		
		String group = "AOP_BASE";
		
		Set jedisClusterNodes = new HashSet();
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7000));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7002));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7003));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7004));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7005));
		jc = null;
		try {
		jc = new JedisCluster(jedisClusterNodes);
		jc.set(group + "AILK_REDIS_CONNECT_TEST", "TRUE");
		System.out.println(jc.get(group + "AILK_REDIS_CONNECT_TEST"));
		Boolean localBoolean1 = Boolean.TRUE;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		TreeSet<String> keys=keys("*");
		
		//遍历key  进行删除  可以用多线程
	
		for(String key:keys){
		
			jc.del(key);
			System.out.println(key);
		}
	}
	

    //@param pattern  获取key的前缀  全是是 * 

	public static TreeSet<String> keys(String pattern){  
	   
	
	  TreeSet<String> keys = new TreeSet<>();  
	   //获取所有的节点
	
	  Map<String, JedisPool> clusterNodes = jc.getClusterNodes();  
	  //遍历节点 获取所有符合条件的KEY 
	
	  for(String k : clusterNodes.keySet()){  
	      JedisPool jp = clusterNodes.get(k);  
	      Jedis connection = jp.getResource();  
	      try {  
	          keys.addAll(connection.keys(pattern));  
	      } catch(Exception e){  
	      } finally{  
	          connection.close();//用完一定要close这个链接！！！  
	      }  
	  }  
	  return keys;  
	}  
}
