package com.test;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class redisDaoTest {
	
	private Jedis jedis = null;
	
	/**
     * 某些查询方法，用不到事物。
     */
    public redisDaoTest(Jedis jedis) {
        this.jedis = jedis;
        jedis.select(2);
        //this.transaction = jedis.multi();
    }
	
	public Set<String> sinter(String... key) {
        return jedis.sinter(key);
    }
	
	public Set<String> keys(String pattern) {
        return jedis.keys(pattern);
    }
	
	/**
     * @Description:根据key返回值
     * @param key
     * @param jedis
     * @return:String
     */
	public static String get(String key, Jedis jedis) {
        return jedis.get(key);
    }
	
	/**
     * @Description: 存放set类型
     * @param key
     * @param value
     */
    public void sadd(String key, String value) {
        jedis.sadd(key, value);
    }
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("172.16.100.227", 6379, 100000, 100000);
		jedis.auth("cloud@hansight.com");
		jedis.mset("name","liuling","age","23","qq","476777XXX");
		System.out.println(jedis.get("name"));
		System.out.println(123);
	}
}
