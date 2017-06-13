package com.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.Cache;

import com.ai.core.util.utils.CheckNull;
import com.ai.core.util.utils.HttpClientUtil;
import com.ai.core.util.utils.SystemRedisCache;

public class test
{
	private Map<String,Object> StringToMap(String str){
		str = str.substring(1,str.length()-1).replaceAll("\"", "");
		Map<String,Object> map = new HashMap<String,Object>();
		if(!CheckNull.isNull(str) && str.indexOf(",")>0){
			String array[] = str.split(",");
			for(String arr:array){
				map.put(arr.trim().substring(0,arr.indexOf(":")),arr.trim().substring(arr.indexOf(":")+1));
			}
		}
		return map;
	}
	public static void main(String[] args)
	{
		//System.out.println("1");
		//String a = HttpClientUtil.httpGet("https://api.weixin.qq.com/sns/userinfo?access_token=OezXcEiiBSKSxW0eoylIeNit16UEqaKikxOc9fAreZ_2UJUzAyuHgTmAWe4oWYWu0Ujj4pr3HNfBQ_hxAPQP2vUqNxGsTZaICNbuMoUbkUaqhm-tcZXaO9YU-Ui1Le6boHAzeZ6ImGQKHAWDSjddYg&openid=ok621s6wv6yhVKSahgU1kSBXno5Y&lang=en");
		new test().a();
	}
	
	public void a(){
		Collection<? extends Cache> caches;
		SystemRedisCache s= new SystemRedisCache();
		Set<SystemRedisCache> se = new HashSet<SystemRedisCache>();
		caches=se;
	}
}

class dd extends fa{}

class fa extends cc implements it{}
class cc{}

interface it{}
