package com.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class testFileChannel {
	
	static int i = 0;
	
	public static void main(String[] args) {
		try {
			new testFileChannel().copy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copy() throws Exception {
		long t1 = System.currentTimeMillis();
		String source = "d:/123.txt";
		String dest = "d:/aaa" + i++ +".txt";

		FileInputStream inputStream = new FileInputStream(source);
		FileOutputStream outputStream = new FileOutputStream(dest);

		FileChannel iChannel = inputStream.getChannel();
		FileChannel oChannel = outputStream.getChannel();
		
		

		ByteBuffer buffer = ByteBuffer.allocate(81920);
		byte[] byt = new byte[81920];
		while (true) {
			buffer.clear();// pos=0,limit=capcity，作用是让ichannel从pos开始放数据
			int r = iChannel.read(buffer);
			if (r == -1)// 到达文件末尾
				break;
			buffer.limit(buffer.position());//
			buffer.position(0);// 这两步相当于 buffer.flip();
			oChannel.write(buffer);// 它们的作用是让ochanel写入pos - limit之间的数据
		}
		System.out.println(1111111111);
		inputStream.close();
		outputStream.close();
		long t2 = System.currentTimeMillis();
		System.out.println( t2- t1);
	}
}
