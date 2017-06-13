package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class testBufferedReader {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		File file = new File("D:"+File.separator+"123.txt");
		InputStream in;
		InputStreamReader inr;
		BufferedReader br;
		OutputStream os;
		OutputStreamWriter osw;
		BufferedWriter bw;
		try {
			in = new FileInputStream(file);
			inr = new InputStreamReader(in);
			br = new BufferedReader(inr);
			os = new FileOutputStream("D:"+File.separator+"aaa.txt");
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
			String line;
			StringBuffer str = new StringBuffer();
			int i =0;
			while((line=br.readLine()) != null)
			{
				str.append(line+"\n");
			}
			bw.write(String.valueOf(str));
			System.out.println(str.length());
			bw.flush();
			in.close();
			inr.close();
			br.close();
			os.close();
			osw.close();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}
}
