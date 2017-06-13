package com.test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class testNIO {
	
	public static void main(String[] args) {
			new testNIO().testSeek();
	}
	
	public void testSeek(){
		try {
			ByteBuffer by = ByteBuffer.allocate(1000);
			System.out.println(new String(by.array()));
			
			File file = new File("F://a.log");
			RandomAccessFile rf = new RandomAccessFile(file, "r");
			rf.seek(0);
			boolean flag = true;
			while(flag){
				String line = rf.readLine();
				long pointer = rf.getFilePointer();
				System.out.println(pointer);
				if(line == null){
					flag = false;
				}else{
					if(line.trim().equals(""))
						continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
