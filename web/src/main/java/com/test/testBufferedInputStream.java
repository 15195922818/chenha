package com.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class testBufferedInputStream {
	
	static int i = 0;
	
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		File file = new File("D:"+File.separator+"123.txt");
		InputStream in;
		BufferedInputStream bis;
		OutputStream os;
		BufferedOutputStream bos;
		try {
			in = new FileInputStream(file);
			bis = new BufferedInputStream(in);
			os = new FileOutputStream("D:"+File.separator+"aaa" + i++ + ".txt"); 
			bos = new BufferedOutputStream(os);
			int len=0;
			byte byt[] = new byte[8192];
			while((len=bis.read(byt)) >0)
			{
				bos.write(byt,0,len);
			}
			bos.flush();
			in.close();
			bis.close();
			os.close();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}
	
	public void BufferedInputStream(){
		long t1 = System.currentTimeMillis();
		File file = new File("D:"+File.separator+"123.txt");
		InputStream in;
		BufferedInputStream bis;
		OutputStream os;
		BufferedOutputStream bos;
		try {
			in = new FileInputStream(file);
			bis = new BufferedInputStream(in);
			os = new FileOutputStream("D:"+File.separator+"aaa" + i++ + ".txt"); 
			bos = new BufferedOutputStream(os);
			int len=0;
			byte byt[] = new byte[81920];
			while((len=bis.read(byt)) >0)
			{
				bos.write(byt,0,len);
			}
			bos.flush();
			in.close();
			bis.close();
			os.close();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}
}
