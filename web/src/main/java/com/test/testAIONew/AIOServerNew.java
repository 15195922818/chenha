package com.test.testAIONew;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * AsynchronousServerSocketChannel
 */
public class AIOServerNew implements Runnable {

	private int port = 8889;
	private int threadSize = 10;
	protected AsynchronousChannelGroup asynchronousChannelGroup;

	protected AsynchronousServerSocketChannel serverChannel;

	public AIOServerNew(int port, int threadSize) {
		this.port = port;
		this.threadSize = threadSize;
	}

	public static void main(String[] args) throws IOException {
		new Thread(new AIOServerNew(8989, 19)).start();
	}

	public void run() {
		try {
			asynchronousChannelGroup = AsynchronousChannelGroup
					.withCachedThreadPool(Executors.newCachedThreadPool(), 10);
			serverChannel = AsynchronousServerSocketChannel
					.open(asynchronousChannelGroup);
			serverChannel.bind(new InetSocketAddress(port));
			System.out.println("listening on port: " + port);
			serverChannel
					.accept(this,
							new CompletionHandler<AsynchronousSocketChannel, AIOServerNew>() {
								final ByteBuffer echoBuffer = ByteBuffer
										.allocateDirect(1024);

								public void completed(
										AsynchronousSocketChannel result,
										AIOServerNew attachment) {
									System.out.println("reading begin...");
									try {
										System.out.println("远程地址："
												+ result.getRemoteAddress());
										echoBuffer.clear();
										result.read(echoBuffer).get();
										echoBuffer.flip();
										System.out.println("received : "
												+ Charset.defaultCharset()
														.decode(echoBuffer));
										String msg = "server test msg-"
												+ Math.random();
										System.out.println("server send data: "
												+ msg);
										result.write(ByteBuffer.wrap(msg
												.getBytes()));
									} catch (IOException e) {
										e.printStackTrace();
									} catch (InterruptedException e) {
										e.printStackTrace();
									} catch (ExecutionException e) {
										e.printStackTrace();
									} finally {
										attachment.serverChannel.accept(
												attachment, this);// 监听新的请求，递归调用。
									}

								}

								public void failed(Throwable exc,
										AIOServerNew attachment) {
									System.out.println("received failed");
									exc.printStackTrace();
									attachment.serverChannel.accept(attachment,
											this);
								}
							});
			System.out.println("Server...continue");
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}