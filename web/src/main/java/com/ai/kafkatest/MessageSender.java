package com.ai.kafkatest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by GW on 2017/2/6.
 */
public class MessageSender {

    private KafkaProducer<String,String> producter;

    public MessageSender() {
        Properties properties = new Properties();
        try{
            properties.load(this.getClass().getClassLoader().getResourceAsStream("producer.properties"));
        }catch (Exception e){

        }
        this.producter = new KafkaProducer<String, String>(properties);
    }

    public void sendMsg(String topic, String key , String value){
        sendMsgBatch(topic,key,value);
        producter.flush();
    }

    public void sendMsgBatch(String topic, String key , String value) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, key, value);
        producter.send(producerRecord);
    }

    public static void main(String args[]){
        MessageSender m = new MessageSender();
        m.sendMsg("cache","eee","eee1");
    }
}
