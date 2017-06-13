package com.ai.web.entity;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.google.common.hash.HashCode;

import redis.clients.jedis.HostAndPort;

@Component
public class asd {
	public asd(){
		System.out.println("asd");
	}
	
	public String getJedis(String c){
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		//Jedis Cluster will attempt to discover cluster nodes automatically
		//只需要配置集群中的一个结点，连接成功后，自动获取点集群中其他结点信息
		
		return c;
	}
	
	public static void main(String[] args) {
		
	}
}
