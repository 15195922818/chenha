package com.ai.kafkatest;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Created by GW on 2017/2/6.
 */
public abstract class AbstractMessageReciver {

    private KafkaConsumer<String,String> consumer;

    public AbstractMessageReciver() {
        this("test");
    }

    public AbstractMessageReciver(final String topic) {
        Properties properties = new Properties();
        try{
            properties.load(this.getClass().getClassLoader().getResourceAsStream("consumer.properties"));
        }catch (Exception e){

        }
        this.consumer = new KafkaConsumer<String, String>(properties);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            	System.out.println(topic);
                consumer.subscribe(Arrays.asList(topic));
                while(true){
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records){
                        processMsg(record.key(),record.value());
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

    protected abstract void processMsg(String key, String value);
    
    public static void main(String[] args) {
    	System.out.println(1);
		new AbstractMessageReciver("cache1"){
            @Override
            protected void processMsg(String key, String value) {
                System.out.println("key:" + key + ";value:" + value + "已存入CacheUtil.cacheMap缓存中");
            }
        };;
	}
}
