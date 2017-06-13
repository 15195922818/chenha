package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class testInputStream {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		File file = new File("F:"+File.separator+"a.log");
		Boolean b = file.isHidden();
		Boolean c = file.canWrite();
		Boolean d = file.canExecute();
		String par = file.getParent();
		File fi[] = file.listRoots();
		InputStream fis;
		FileOutputStream fos;
		InputStream is;
		try {
			is = new FileInputStream(file);
			
			fos = new FileOutputStream("F:"+File.separator+"aaa.txt");
			byte[] byt = new byte[81920];
			int len=0;
			int i=0;
			FileChannel oChannel = fos.getChannel();
			while((len=is.read(byt)) >0)
			{
				fos.write(byt,0,len);
				/*ByteBuffer b1 = ByteBuffer.wrap(byt);
				oChannel.write(b1);*/
			}
			System.out.println(1111111111);
			fos.flush();
			is.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}
	
	public static void inputStream() {
		long t1 = System.currentTimeMillis();
		File file = new File("D:"+File.separator+"123.txt");
		Boolean b = file.isHidden();
		Boolean c = file.canWrite();
		Boolean d = file.canExecute();
		String par = file.getParent();
		File fi[] = file.listRoots();
		InputStream fis;
		OutputStream fos;
		InputStream is;
		try {
			is = new FileInputStream(file);
			
			fos = new FileOutputStream("D:"+File.separator+"aaa.txt");
			byte[] byt = new byte[81920];
			int len=0;
			int i=0;
			while((len=is.read(byt)) >0)
			{
				fos.write(byt,0,len);
			}
			System.out.println(1111111111);
			fos.flush();
			is.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}
}
