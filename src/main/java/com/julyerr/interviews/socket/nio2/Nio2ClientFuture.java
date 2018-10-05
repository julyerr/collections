package com.julyerr.interviews.socket.nio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public class Nio2ClientFuture {
    private AsynchronousSocketChannel channel;
    private CharBuffer charBuffer;
    private CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
    private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    public void init() throws IOException, ExecutionException, InterruptedException {
        channel = AsynchronousSocketChannel.open();
        if (channel.isOpen()) {
//            创建异步通道
            channel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * 1024);
            channel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * 1024);
            channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            Void connect = channel.connect(new InetSocketAddress("127.0.0.1", 8080)).get();
            if (connect != null) {
                throw new RuntimeException("Connect to Server failed");
            }
        } else {
            throw new RemoteException("Channel isn't open");
        }
    }

    public void start() throws IOException, ExecutionException, InterruptedException {
        System.out.println("Please input request");
        String request = inputReader.readLine();
        channel.write(ByteBuffer.wrap(request.getBytes()));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        读取服务响应
        while (channel.read(buffer).get() != -1) {
            buffer.flip();
            charBuffer = decoder.decode(buffer);
            String response = charBuffer.toString().trim();
            System.out.println("Server reponse: " + response);
            if (buffer.hasRemaining()) {
                buffer.compact();
            } else {
                buffer.clear();
            }
            request = inputReader.readLine();
            channel.write(ByteBuffer.wrap(request.getBytes()));
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        Nio2ClientFuture clientFuture = new Nio2ClientFuture();
        clientFuture.init();
        clientFuture.start();
    }
}
