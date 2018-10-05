package com.julyerr.interviews.socket.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
    private Selector selector;
    private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

    public void init() throws IOException {
        this.selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
//        设置非阻塞
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 8080));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void start() throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if (key.isConnectable()) {
                    connect(key);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
//        如果正在链接
        if (channel.isConnectionPending()) {
            if (channel.finishConnect()) {
                channel.configureBlocking(false);
//                对于读事件感兴趣
                channel.register(selector, SelectionKey.OP_READ);
                String request = inputReader.readLine();
                channel.write(ByteBuffer.wrap(request.getBytes()));
            } else {
                key.cancel();
            }
        }
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        String response = new String(buffer.array()).trim();
        System.out.println("Server Response: " + response);
        String nextRequest = inputReader.readLine();
        ByteBuffer outBuffer = ByteBuffer.wrap(nextRequest.getBytes());
        channel.write(outBuffer);
    }

    public static void main(String[] args) throws IOException {
        NioClient client = new NioClient();
        client.init();
        client.start();
    }
}
