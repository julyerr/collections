package com.julyerr.interviews.socket.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.rmi.RemoteException;
import java.util.concurrent.*;

public class Nio2ServerFuture {
    private ExecutorService taskExecutor;
    private AsynchronousServerSocketChannel serverSocketChannel;

    class Worker implements Callable<String> {
        private CharBuffer charBuffer;
        private CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        private AsynchronousSocketChannel channel;

        public Worker(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public String call() throws Exception {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer).get() != -1) {
                buffer.flip();
                charBuffer = decoder.decode(buffer);
                String request = charBuffer.toString().trim();
                System.out.println("Client request: " + request);
                ByteBuffer outBuffer = ByteBuffer.wrap(request.getBytes());
                channel.write(outBuffer);
                if (buffer.hasRemaining()) {
                    buffer.compact();
                } else {
                    buffer.clear();
                }
            }
            channel.close();
            return "ok";
        }
    }

    public void init() throws IOException {
        taskExecutor = Executors.newCachedThreadPool();
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        if (serverSocketChannel.isOpen()) {
            serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8080));
        } else {
            throw new RemoteException("Channel don't open");
        }
    }

    public void start() {
        System.out.println("Waiting for client request...");
        while (true) {
            Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
            try {
                AsynchronousSocketChannel channel = future.get();
                taskExecutor.submit(new Worker(channel));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                taskExecutor.shutdown();
                while (!taskExecutor.isTerminated()) {
                }
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Nio2ServerFuture serverFuture = new Nio2ServerFuture();
        serverFuture.init();
        serverFuture.start();
    }
}
