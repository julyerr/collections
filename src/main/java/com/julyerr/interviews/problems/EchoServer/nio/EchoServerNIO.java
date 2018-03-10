package com.julyerr.interviews.problems.EchoServer.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class EchoServerNIO {
    private static final int ECHO_SERVER_PORT = 6789;
    private static final int ECHO_SERVER_TIMEOUT = 5000;
    private static final int BUFFER_SIZE = 1024;

    private static ServerSocketChannel serverChannel = null;
    private static Selector selector = null;    // 多路复用选择器
    private static ByteBuffer buffer = null;    // 缓冲区

    public static void main(String[] args) {
        init();
        listen();
    }

    private static void init() {
        try {
            serverChannel = ServerSocketChannel.open();
            buffer = ByteBuffer.allocate(BUFFER_SIZE);
            serverChannel.bind(new InetSocketAddress(ECHO_SERVER_PORT));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listen() {
        while (true) {
            try {
                if (selector.select(ECHO_SERVER_TIMEOUT) != 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void handleKey(SelectionKey key) {
        SocketChannel socketChannel = null;

        try {
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                serverSocketChannel.register(selector, SelectionKey.OP_READ);
            } else if (key.isReadable()) {
                socketChannel = (SocketChannel) key.channel();
                buffer.clear();
                if (socketChannel.read(buffer) > 0) {
                    buffer.flip();
                    CharBuffer charBuffer = CharsetHelper.decode(buffer);
                    String string = charBuffer.toString();
                    System.out.println("received:" + socketChannel.getRemoteAddress() + ":" + string);
                    socketChannel.write(CharsetHelper.encode(CharBuffer.wrap(string)));
                } else {
                    socketChannel.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
