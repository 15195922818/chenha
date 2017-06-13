package com.test;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class demo {
	// 会话超时时间，设置为与系统默认时间一致
	private static final int SESSION_TIMEOUT = 30000;

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
		zk = new ZooKeeper("130.51.23.111:2181,130.51.23.12:2181,130.51.23.34:2181", 300000, wh);
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
	
	private void ZKCreatePersistentNodes() throws IOException, InterruptedException,KeeperException 
	{
		zk.create("/persistent", "myData_PERSISTENT".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		
	}
	
	private void ZKCreateTemporaryNodes() throws IOException, InterruptedException,KeeperException 
	{
		zk.create("/temporary", "myData_TEMPORARY".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		
	}
	
	public List getChildren(String path,Stat s) throws KeeperException, InterruptedException{
		return zk.getChildren(path, wh,s);
	}

	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		demo dm = new demo();
		dm.createZKInstance();
		Stat s = new Stat();
		String path="/zk";
		List list = dm.getChildren(path,s);
		dm.ZKClose();
	}
}