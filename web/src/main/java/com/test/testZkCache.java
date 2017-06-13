package com.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.ai.web.entity.Question;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class testZkCache {
	public static void main(String[] args) {
		testZkCache dm = new testZkCache();
		try {
			dm.createZKInstance();
			//dm.createZk();
			
			byte[] s=dm.zk.getData("/mapcache/PcsTPayTypeJoinRelMap", dm.wh, null);
			System.out.println(new String(s));
			dm.ZKClose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 会话超时时间，设置为与系统默认时间一致
	private static final int SESSION_TIMEOUT = 30000;
	
	private static Map<String,Object> map = new HashMap<String,Object>();

	// 创建 ZooKeeper 实例
	ZooKeeper zk;

	// 创建 Watcher 实例
	Watcher wh = new Watcher() {
		public void process(org.apache.zookeeper.WatchedEvent event) {
			System.out.println(event.toString());
		}
	};

	// 初始化 ZooKeeper 实例
	private void createZKInstance() throws IOException {
		zk = new ZooKeeper("192.168.1.120:2181", 300000, wh);
	}
	
	private void createZk() throws KeeperException, InterruptedException{
		
		
		List<Question> list = new ArrayList<Question>();
		Question question = new Question();
		question.setQuestionId("0000");
		Question question1 = new Question();
		question1.setQuestionId("1111");
		list.add(question);
		list.add(question1);
		map.put("list", list);
		
		Gson gson = new Gson();
		String str = gson.toJson(map);
		

		zk.create("/test123", str.getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		
		System.out.println("\n2. 查看是否创建成功： ");
		String str1 = new String(zk.getData("/test123", false, null));
		Map<String,List<Question>> map1=gson.fromJson(str1,new TypeToken<Map<String,List<Question>>>() {}.getType());
		List<Question> list2 =map1.get("list");
		
		System.out.println("list2.get(1)=====");
		System.out.println(list2.get(1).getQuestionId());
		System.out.println(new String(zk.getData("/test123", false, null)));
	}
	
	private String getZk()throws KeeperException, InterruptedException{
		System.out.println("\n2. 参数值为 ");
		System.out.println(new String(zk.getData("/test123", false, null)));
		return new String(zk.getData("/test123", false, null));
	}
	
	private void deleteZk()throws KeeperException, InterruptedException{
		System.out.println("\n5. 删除节点 ");
		zk.delete("/test123", -1);
	}
	
	private void updateZk()throws KeeperException, InterruptedException{
		zk.setData("/test123", "chenhongan".getBytes(), -1);
		System.out.println("\n2. 参数值为 ");
		System.out.println(new String(zk.getData("/test123", false, null)));
	}

	private void ZKOperations() throws IOException, InterruptedException,
			KeeperException {
		System.out.println("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
		zk.create("/zoo2", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);

		System.out.println("\n2. 查看是否创建成功： ");
		System.out.println(new String(zk.getData("/zoo2", false, null)));

		System.out.println("\n3. 修改节点数据 ");
		zk.setData("/zoo2", "shenlan211314".getBytes(), -1);

		System.out.println("\n4. 查看是否修改成功： ");
		System.out.println(new String(zk.getData("/zoo2", false, null)));

		System.out.println("\n5. 删除节点 ");
		zk.delete("/zoo2", -1);

		System.out.println("\n6. 查看节点是否被删除： ");
		System.out.println(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
	}
	
	private void ZKClose() throws InterruptedException {
		zk.close();
	}
}
