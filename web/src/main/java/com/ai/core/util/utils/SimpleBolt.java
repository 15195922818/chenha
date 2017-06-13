package com.ai.core.util.utils;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * 接收喷发节点(Spout)发送的数据进行简单的处理后，发射出去。
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class SimpleBolt extends BaseBasicBolt {
	
    public void execute(Tuple input, BasicOutputCollector collector) {
		try {
			String msg = input.getString(0);
			throw new Exception();
			/*if (msg != null) {
				// System.out.println("msg="+msg);
				collector.emit(new Values(msg + "msg is processed!","test"));
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("info","te"));
    }

}