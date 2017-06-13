package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class testThreadCopyFile {
	public static void main(String[] args) {
		File file= new File("D://123.txt");
		ByteBuffer byteBuffer = ByteBuffer.allocate(81920);
		Info1 info = new Info1();
		info.setTime(System.currentTimeMillis());
		info.setFile(file);
		info.setByteBuffer(byteBuffer);
		InputStreamThread ist = new InputStreamThread(info);
		Thread t = new Thread(ist);
		t.start();
		OutputStreamThread ost = new OutputStreamThread(info);
		Thread t1 = new Thread(ost);
		t1.start();
	}
}

class Info1{
	public File file;
	
	public ByteBuffer byteBuffer;
	
	public boolean isThreadRun = true;
	
	public boolean allWhileFlag = true;
	
	public long time;
	
	public void setFile(File file){
		this.file = file;
	}
	
	public File getFile(){
		return file;
	}
	
	public void setByteBuffer(ByteBuffer byteBuffer){
		this.byteBuffer = byteBuffer;
	}
	
	public ByteBuffer getByteBuffer(){
		return byteBuffer;
	}
	
	public void getIsInsThreadRun(boolean isThreadRun){
		this.isThreadRun = isThreadRun;
	}
	
	public boolean getIsInsThreadRun(){
		return isThreadRun;
	}
	
	public void setAllWhileFlag(boolean allWhileFlag){
		this.allWhileFlag = allWhileFlag;
	}
	
	public boolean getAllWhileFlag(){
		return allWhileFlag;
	}
	
	public void setTime(long time){
		this.time = time;
	}
	
	public long getTime(){
		return time;
	}
}

class InputStreamThread extends Thread{
	
	private Info1 info;
	
	public InputStreamThread(Info1 info){
		this.info = info;
	}
	
	public InputStreamThread(){}
	
	public void run(){
		File file = info.getFile();
		
		FileInputStream fis = null;
		FileChannel fc = null;
		try {
			fis = new FileInputStream(file);
			fc = fis.getChannel();
			int i=0;
			while(info.getAllWhileFlag()){
				if(info.isThreadRun){
					info.getByteBuffer().clear();
					int r = fc.read(info.getByteBuffer());
					//System.out.println("read");
					info.isThreadRun = false;
					if(r == -1)
					{
						info.setAllWhileFlag(false);
					}
				}else{
					//System.out.println("read废操作:"+i++);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("InputStreamThread----finally");
		  
		}
	}
}

class OutputStreamThread extends Thread{
	
	private Info1 info;
	
	public OutputStreamThread(Info1 info){
		this.info = info;
	}
	
	public OutputStreamThread(){}
	
	public void run(){
		File file = new File("D://aaaaa.txt");
		FileOutputStream fos = null;
		FileChannel fc = null;
		try {
			fos = new FileOutputStream(file);
			fc = fos.getChannel();
			int i=0;
			while(info.getAllWhileFlag()){
				if(!info.isThreadRun){
					info.getByteBuffer().flip();
					fc.write(info.getByteBuffer());
					//System.out.println("write");
					info.isThreadRun = true;
				}else{
					//System.out.println("write废操作:"+i++);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("OutputStreamThread----finally");
			try {
				fc.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long time = info.getTime();
		long time1 = System.currentTimeMillis();
		System.out.println("执行时间为:"+(time1-time));
	}
}