package com.test;

import java.net.UnknownHostException;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class testMongoDB {
	
	static Mongo mongo;
	
	static{
		// 创建一个Mongo实例，默认使用本地"127.0.0.1", 27017
		// 使用IP+端口号创建
		try {
			mongo = new Mongo("127.0.0.1", 27017);
		} catch (UnknownHostException e) {
			System.out.println("连接失败");
		} catch (MongoException e) {
			System.out.println("连接失败");
		} catch (Exception e) {
			System.out.println("连接失败");
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		new testMongoDB().read();
	}
	
	public void wirte(){
		DB db = mongo.getDB("mylearndb");  
		DBCollection fruitShop = db.getCollection("fruitshop");  
		DBObject shop1 = new BasicDBObject();  
		shop1.put("a","aa");
		fruitShop.insert(shop1);
	}
	
	public void read(){
		DB db = mongo.getDB("mylearndb");
		DBCollection fruitShop = db.getCollection("fruitshop");  
		DBCursor cursor=fruitShop.find();
		Iterator<DBObject> it = cursor.iterator();
		/*DBObject dob = it.next();
		System.out.println(dob.get("b"));
		System.out.println(dob.get("a"));*/
		while(it.hasNext()){
			DBObject dob = it.next();
			dob.put("c", "cc");
			System.out.println(JSON.serialize(dob.toString()));
		}
	}
}
