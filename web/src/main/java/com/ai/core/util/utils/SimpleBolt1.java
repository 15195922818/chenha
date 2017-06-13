package com.ai.core.util.utils;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
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
public class SimpleBolt1 extends BaseBasicBolt {
	
    public void execute(Tuple input, BasicOutputCollector collector) {
		try {
			String msg = input.getString(0);
			String msg1 = input.getString(1);
			if (msg != null) {
				// System.out.println("msg="+msg);
				collector.emit(new Values(msg + "msg is processed!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("info"));
    }

}