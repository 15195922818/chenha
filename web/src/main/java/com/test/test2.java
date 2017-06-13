package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class test2
{
	private Socket s;
    private BufferedReader br;
    //private BufferedReader line;
    private PrintWriter pw;
    private String line = "";
    public test2() {
        try{
        	System.out.println("test");
            s = new Socket("127.0.0.1",2015);
            pw = new PrintWriter(s.getOutputStream(),true);//用于向服务器发送消息
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));//用于获取服务器返回的数据
            pw.println("hello");
            line = br.readLine();
            System.out.println(line);

            br.close();
            pw.close();
        }catch(IOException ie){
            ie.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        new test2();
    }
}
