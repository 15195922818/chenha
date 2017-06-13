package com.test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class testFileChannelTransferTo {
	
	static int i = 0;
	
	public static void main(String[] args) {
		try {
			new testFileChannelTransferTo().copy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copy() throws Exception {
		long t1 = System.currentTimeMillis();
		String source = "d:/333.txt";
		String dest = "d:/aaa" + i++ +".txt";

		RandomAccessFile inputStream = new RandomAccessFile("d:/333.txt","rw");
		RandomAccessFile outputStream = new RandomAccessFile("d:/aaa" + i++ +".txt","rw");
		FileChannel iChannel = inputStream.getChannel();
		FileChannel oChannel = outputStream.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(81920);
		byte[] byt = new byte[81920];
		iChannel.transferTo(0, iChannel.size(), oChannel);//连接两个通道，并且从in通道读取，然后写入out通道
		System.out.println(1111111111);
		inputStream.close();
		outputStream.close();
		long t2 = System.currentTimeMillis();
		System.out.println( t2- t1);
	}
}
