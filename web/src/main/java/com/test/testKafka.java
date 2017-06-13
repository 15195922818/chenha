package com.test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.ai.kafkatest.MessageSender;
import com.google.gson.Gson;

public class testKafka {
	
	public static void main(String[] args) {
		
		/*AbstractMessageReciver abstractMessageReciver = new AbstractMessageReciver(){
			@Override
			protected void processMsg(String key, String value) {
				try {
					OutputStream os = new FileOutputStream("e:"+File.separator+"g.txt");
					Gson gson = new Gson();
					byte[] byt = new byte[1024];
					BASE64Decoder decode = new BASE64Decoder(); 
					List<String> list = gson.fromJson(value,new TypeToken<List<String>>(){}.getType());
					for(int i=0;i<list.size();i++){
						byt=decode.decodeBuffer(list.get(i));
						if(list.size()-1==i){
							os.write(byt,0,Integer.valueOf(key));
						}else{
							os.write(byt);
						}
					}
					os.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch(IOException e1){
					e1.printStackTrace();
				}
			}
		};

		MessageSender ms = new MessageSender();
		File file = new File("d:"+File.separator+"g.txt");
		try {
			BASE64Encoder encode = new BASE64Encoder(); 
			InputStream inputStream = new FileInputStream(file);
			byte[] byt = new byte[1024];
			int len=0;
			List<String> list = new ArrayList<String>();
			Gson gson = new Gson();
			int l=0;
			while((len=inputStream.read(byt))>-1){
				String str = encode.encode(byt);
				list.add(str);
				l=len;
			}
			for(int i=0;i<10000;i++){
			ms.sendMsg("file_syn17", String.valueOf(l), gson.toJson(list));
			}
			inputStream.close();
		}catch(Exception e){
		}*/
		MessageSender ms = new MessageSender();
		for(int i=0;i<1;i++){
			Map<String,Object> map= new HashMap<String,Object>();
			map.put("resultDesc", "resultDesc");
			//map.put("resultCode", "0001");
			map.put("code", "payOrderSave");
			map.put("resultCode", "0002");
			//map.put("code", "payOrderSave");
			map.put("catalogCode", "trade");
			map.put("serviceId", "00000001");
			map.put("tradeNum", "1");
			map.put("tradeAmount", "100");
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("occurTime", d.format(new Date()));
			map.put("payTypeId", "1");
			map.put("tradeType", "52");
			map.put("serviceName", "业务平台1");
			map.put("payTypeName", "支付方式1");
			map.put("reusultDesc", "错误描述1");
			map.put("notifyTypeName", "通知类型1");
			map.put("ip","192.168.1.11");
			map.put("provinceCode","0020");
			Gson gson = new Gson();
			String str=gson.toJson(map);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ms.sendMsg("Monitor", "h", str);
		}
	}
}

abstract class AbstractMessageReciver {

    private KafkaConsumer<String,String> consumer;

    public AbstractMessageReciver() {
        this("file_syn17");
    }

    public AbstractMessageReciver(final String topic) {
        Properties properties = new Properties();
        try{
            properties.load(this.getClass().getClassLoader().getResourceAsStream("consumer.properties"));
        }catch (Exception e){

        }
        this.consumer = new KafkaConsumer<String, String>(properties);

        new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.subscribe(Arrays.asList(topic));
                while(true){
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records){
                        processMsg(record.key(),record.value());
                    }
                }
            }
        }).start();
    }

    protected abstract void processMsg(String key, String value);
}
