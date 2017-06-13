package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class testInputStreamReader {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		File file = new File("D:"+File.separator+"123.txt");
		InputStream in;
		InputStreamReader inr;
		OutputStream os;
		OutputStreamWriter osw;
		try {
			in = new FileInputStream(file);
			inr = new InputStreamReader(in,"UTF-8");
			os = new FileOutputStream("D:"+File.separator+"aaa.txt");
			osw = new OutputStreamWriter(os);
			int len = 0;
			char cha[] = new char[1024];
			while((len=inr.read(cha)) >0)
			{
				osw.write(cha,0,len);
			}
			osw.flush();
			in.close();
			inr.close();
			os.close();
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}
}
