package com.test.testAIO;

import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.AsynchronousSocketChannel;  
  
public class AIOClient2 {  
  
    public static void main(String... args) throws Exception {  
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();  
        client.connect(new InetSocketAddress("localhost", 9888));  
    	client.write(ByteBuffer.wrap(("test2").getBytes())).get();  
    }  
}