package com.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class testNIOselector {
	public static void main(String[] args) {
		try {
			//FileInputStream fis = new FileInputStream("d:\\123.txt");
			//FileOutputStream fos = new FileOutputStream("d:\\aaa.txt");
			Selector selector = Selector.open();
			ServerSocketChannel selectableChannel = ServerSocketChannel.open();
			selectableChannel.socket().bind (new InetSocketAddress (1234));
			selectableChannel.configureBlocking(false);
			
			SelectionKey key = selectableChannel.register(selector,16);
			Set<SelectionKey> set = selector.selectedKeys();
			Iterator<SelectionKey> it = set.iterator();
			
			while(true)
			{
				SocketChannel sc = selectableChannel.accept();
				if (sc == null) {
					try {
						Thread.sleep (2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println ("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
					ByteBuffer buf = ByteBuffer.allocate(48);
					int bytesRead = sc.read(buf);
					sc.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
