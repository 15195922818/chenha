package com.test;  
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/** 
 * redis-cluster java客户端工具类(单例) 
 *  
 * @author leifu 
 * @Date 2015-8-30 
 * @Time 上午10:08:12 
 */  
public class RedisClusterComponent {  
	public static void main(String[] args) {
		JedisPool pool = new JedisPool("192.168.1.120", 7002);
        Jedis jedis = pool.getResource();
       
        //清空数据
        jedis.flushAll();
 
        //操作String
        jedis.set("site", "dataguru");
        System.out.println(jedis.get("site"));
	}
}
