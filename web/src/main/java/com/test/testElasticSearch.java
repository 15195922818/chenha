package com.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class testElasticSearch {
	
	public static void main(String[] args) {
		new testElasticSearch().query();
	}
	
	public void add(){
		Settings settings = ImmutableSettings.settingsBuilder().put("elasticsearch", true).build();  
		TransportClient client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("172.16.101.73", 9300));
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("user", "kimchy");
		json.put("postDate", new Date());
		json.put("message", "trying out Elastic Search");
		IndexResponse response = client.prepareIndex("twitter", "tweet", "1").setSource(json).execute().actionGet();
	}
	
	public void query(){
		Settings settings = ImmutableSettings.settingsBuilder().put("elasticsearch", true).build();  
		TransportClient client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("172.16.101.73", 9300));
		GetResponse response = client.prepareGet("twitter", "tweet", "1").execute().actionGet();
		Map<String, Object> rpMap = response.getSource();
	    if (rpMap == null) {
	    	System.out.println("empty");
		   return;
		}
		Iterator<Entry<String, Object>> rpItor = rpMap.entrySet().iterator();
		while (rpItor.hasNext()) {
			Entry<String, Object> rpEnt = rpItor.next();
			System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
		}
	}
	
	public void delete(){
		Settings settings = ImmutableSettings.settingsBuilder().put("elasticsearch", true).build();  
		TransportClient client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.1.120", 9300));
		DeleteResponse response = client.prepareDelete("twitter", "tweet", "1")  
                .execute().actionGet();  
        System.out.println(response.getId());  
	}
}
