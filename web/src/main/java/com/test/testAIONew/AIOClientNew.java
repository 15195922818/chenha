package com.test.testAIONew;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * AsynchronousSocketChannel
 */
public class AIOClientNew implements Runnable {

	private AsynchronousSocketChannel client;
	private String host;
	private int port;

	public AIOClientNew(String host, int port) throws IOException {
		this.client = AsynchronousSocketChannel.open();
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) {
		try {
			new Thread(new AIOClientNew("127.0.0.1", 8989)).start();
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		client.connect(new InetSocketAddress(host, port), null,
				new CompletionHandler<Void, Object>() {
					public void completed(Void result, Object attachment) {
						String msg = "client test msg-" + Math.random();
						client.write(ByteBuffer.wrap(msg.getBytes()));
						System.out.println("client send data:" + msg);
					}

					public void failed(Throwable exc, Object attachment) {
						System.out.println("client send field...");
					}
				});

		final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		client.read(byteBuffer, this, new CompletionHandler<Integer, Object>() {
			public void completed(Integer result, Object attachment) {
				System.out.println(result);
				System.out.println("client read data: "
						+ new String(byteBuffer.array()));
			}

			public void failed(Throwable exc, Object attachment) {
				System.out.println("read faield");
			}
		});
		System.out.println("Client...continue");
	}
}