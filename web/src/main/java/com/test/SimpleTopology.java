package com.test;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.ai.core.util.utils.SimpleBolt;
import com.ai.core.util.utils.SimpleBolt1;
import com.ai.core.util.utils.SimpleSpout;

/**
 * Created by IntelliJ IDEA.
 * User: comaple.zhang
 * Date: 12-8-28
 * Time: 下午2:11
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTopology {

    public static void main(String[] args) {
        try {
            //实例化topologyBu ilder类。
            TopologyBuilder topologyBuilder = new TopologyBuilder();
            //设置喷发节点并分配并发数，该并发数将会控制该对象在集群中的线程数。
            topologyBuilder.setSpout("simple-spout", new SimpleSpout(), 1);
            // 设置数据处理节点，并分配并发数。指定该几点接收喷发节点的策略为随机方式。
            topologyBuilder.setBolt("simple-bolt", new SimpleBolt(), 2).shuffleGrouping("simple-spout");
            //topologyBuilder.setBolt("simple-bolt1", new SimpleBolt1(), 3).fieldsGrouping("simple-bolt",new Fields(""));
            Config config = new Config();
            config.setDebug(false);
            /*config.put("redis-host","192.168.1.129");
            config.put("redis-port","7000");
            config.put("storm.zookeeper.servers","192.168.1.129");
            config.put("storm.zookeeper.servers","2181");*/
            if (args != null && args.length > 0) {
                /*设置该topology在storm集群中要抢占的资源slot数，一个slot对应这supervisor节点上的以个worker进程
                 如果你分配的spot数超过了你的物理节点所拥有的worker数目的话，有可能提交不成功，加入你的集群上面已经有了
                 一些topology而现在还剩下2个worker资源，如果你在代码里分配4个给你的topology的话，那么这个topology可以提交
                 但是提交以后你会发现并没有运行。 而当你kill掉一些topology后释放了一些slot后你的这个topology就会恢复正常运行。
                */
            config.setNumWorkers(1);
            StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
            } else {
                //这里是本地模式下运行的启动代码。
                config.setMaxTaskParallelism(1);
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("simple", config,topologyBuilder.createTopology());
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

class MyFunction extends BaseFunction {
    public void execute(TridentTuple tuple, TridentCollector collector) {
        for(int i=0; i < tuple.getInteger(0); i++) {
            collector.emit(new Values(i));
        }
    }
}