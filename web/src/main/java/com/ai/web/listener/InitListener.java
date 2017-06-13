package com.ai.web.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.ai.core.util.utils.CacheUtils;

public class InitListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent context) {
    }

    @Override
    public void contextInitialized(ServletContextEvent context) {
        // 上下文初始化执行
        System.out.println("================>[ServletContextListener]自动加载启动开始.");
        /*Thread th = new Thread(new KafkaConsumer("test"));
        th.start();
        CacheUtils.CacheMap.put("abs", "ok");*/
    }
    
    public void readRedisConfig(){
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
		jc.set("foo","1234");
		String value = jc.get("foo");
		System.out.println(value);
    }

}