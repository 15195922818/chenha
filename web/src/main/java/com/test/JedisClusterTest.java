package com.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.nutz.ioc.val.SysPropValue;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedisPool;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.ai.web.entity.asd;
import com.asiainfo.appframe.ext.exeframe.cache.redis.clients.jedis.JedisPoolConfig;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class JedisClusterTest 
{
	private String jid;
	
	private asd ad;
	
	public JedisCluster getJedis(){
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		//Jedis Cluster will attempt to discover cluster nodes automatically
		//只需要配置集群中的一个结点，连接成功后，自动获取点集群中其他结点信息
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7000));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7002));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7003));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7004));
		jedisClusterNodes.add(new HostAndPort("192.168.1.120", 7005));
		JedisPoolConfig jp = new JedisPoolConfig();
		
		JedisCluster jc = new JedisCluster(jedisClusterNodes,jp);
		return jc;
	}
	
	public static void main(String[] args) 
	{
		new JedisClusterTest().testRedisTransAction();
	}
	
	public void opList(){
		JedisCluster jc = getJedis();
		Gson gson = new Gson();
		JedisClusterTest t = new JedisClusterTest();
		t.setJid("je");
		jc.rpush("students",gson.toJson(t));
		jc.lpush("students","end");
		
		
		
		List<String> students = jc.lrange("students", 0, -1);
		
		jc.lpop("students");
		
		List<String> students1 = jc.lrange("students", 0, -1);
		
		jc.rpop("students");
		
		List<String> students2 = jc.lrange("students", 0, -1);
		
		jc.lrem("students", 5, "end");
		
		List<String> students3 = jc.lrange("students", 0, -1);
		
		String str = students.get(2);
		JedisClusterTest t1 = gson.fromJson(str, JedisClusterTest.class);
		System.out.println(t1.getJid());
		
		/*jc.set("a", gson.toJson(t));
		String str1 = jc.get("a");
		JedisClusterTest t2 = gson.fromJson(str1, JedisClusterTest.class);*/
		
	}
	public void test03(){ 
		JedisCluster jedis = getJedis();
		//添加元素    
		jedis.sadd("teachers", "zhangsan");    
		jedis.sadd("teachers", "lisi","hello");    
		jedis.sadd("teachers", "wangwu");    
			
		//判断Set是否包含制定元素    
		Boolean b1 = jedis.sismember("teachers", "wangwu");    
		Boolean b2 = jedis.sismember("teachers", "xxxxx");    
		System.out.println(b1 + "   " + b2);      
			
		//获取Set内所有的元素    
		Set<String> members =  jedis.smembers("teachers");    
		Iterator<String> it = members.iterator() ;    
		while(it.hasNext()){    
			System.out.println(it.next());    
		}    
			
	//  jedis.sunion(keys...) 可以将多个Set合并成1个并返回合并后的Set    
			
	}
	
	public void test04(){    
		JedisCluster jedis = getJedis();
        //添加元素，会根据第二个参数排序
        jedis.zadd("emps", 5 , "aaa") ;    
        jedis.zadd("emps", 1 , "bbbb") ;    
        jedis.zadd("emps", 3 , "ccc") ;    
        jedis.zadd("emps", 2 , "ddd") ;    
            
        //获取所有元素
        Set<String> emps = jedis.zrange("emps", 0, -1) ;    
        Iterator<String> it = emps.iterator() ;    
        while(it.hasNext()){    
            System.out.println(it.next());    
        }    
    }
	
	public void test05(){  
		JedisCluster jedis = getJedis();
		Map<String , String > car = new HashMap<String , String >() ;    
		car.put("COLOR", "red") ;    
		car.put("SIZE", "2T");    
		car.put("NO", "8888");    
		   
		//新建一个名为car的map
		jedis.hmset("car", car);    
			
		//获取整个对象    ，输出:
		//key:COLOR value:red
		//key:SIZE value:2T
		//key:NO value:8888
		Map<String, String> result = jedis.hgetAll("car");     
		Iterator<Entry<String, String>>  it = result.entrySet().iterator();    
		while(it.hasNext()){    
			Entry<String, String> entry = it.next() ;      
			System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());    
		}    
			
		//也可以获取制定的属性，第一个参数为map名称，第二个参数为键，返回值为值。输出  NO:8888
		String no = jedis.hget("car", "NO") ;    
		System.out.println("NO:" + no);    
	}
	
	public void InputStream(){
		long time=System.currentTimeMillis();
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			File file = new File("d:"+File.separator+"e.xls");
			outputStream = new FileOutputStream("d:"+File.separator+"f.xls");
			inputStream = new FileInputStream(file);
			byte byt[] = new byte[1024];
			int len=0;
			while((len=inputStream.read(byt))>-1){
				outputStream.write(byt, 0, len);
			}
			inputStream.close();
			outputStream.close();
			long time1=System.currentTimeMillis();
			System.out.println("下载时间为:"+(time1-time));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void setFile(){
		File file = new File("d:"+File.separator+"e.xls");
		try {
			BASE64Encoder encode = new BASE64Encoder(); 
			InputStream inputStream = new FileInputStream(file);
			byte[] byt = new byte[1024];
			int len=0;
			List<String> list = new ArrayList<String>();
			while((len=inputStream.read(byt))>-1){
				String str = encode.encode(byt);
				list.add(str);
			}
			JedisCluster jd = getJedis();
			Gson gson = new Gson();
			jd.set("g", gson.toJson(list));
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getFile(){
		try {
			long time=System.currentTimeMillis();
			OutputStream outputStream = new FileOutputStream("d:"+File.separator+"f.xls");
			JedisCluster jd = getJedis();
			Gson gson = new Gson();
			List<String> list=gson.fromJson(jd.get("g"),new TypeToken<List<String>>(){}.getType());
			BASE64Decoder decoder = new BASE64Decoder();
			for(String str :list){
				byte byt[]=decoder.decodeBuffer(str);
				outputStream.write(byt);
			}
			outputStream.close();
			long time1=System.currentTimeMillis();
			System.out.println("下载时间为:"+(time1-time));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}
	
	public void testRedisTransAction(){
		JedisCluster jedisCluster = getJedis();
		for(int i=0;i<1000;i++){
			jedisCluster.del("abcd"+i);
			System.out.println(jedisCluster.get("abcd"+i));
		}
	}
}