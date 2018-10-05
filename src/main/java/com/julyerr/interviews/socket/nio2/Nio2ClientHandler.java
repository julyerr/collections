package com.julyerr.interviews.socket.nio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

public class Nio2ClientHandler {
    class ClientCompletionHandler implements CompletionHandler<Void, Void> {
        private AsynchronousSocketChannel channel;
        private CharBuffer charBuffer = null;
        private CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        public ClientCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        //        有数据的时候自动调用
        @Override
        public void completed(Void result, Void attachment) {
            try {
                System.out.println("Input request:");
                String request = inputReader.readLine();
                channel.write(ByteBuffer.wrap(request.getBytes()));
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (channel.read(buffer).get() != -1) {
                    buffer.flip();
                    charBuffer = decoder.decode(buffer);
                    System.out.println("Server response: " + charBuffer.toString().trim());
                    if (buffer.hasRemaining()) {
                        buffer.compact();
                    } else {
                        buffer.clear();
                    }
                    request = inputReader.readLine();
                    channel.write(ByteBuffer.wrap(request.getBytes()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void failed(Throwable exc, Void attachment) {
            throw new RuntimeException("Connect to server failed");
        }
    }

    public void start() throws IOException, InterruptedException {
        AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
        if (channel.isOpen()) {
            channel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * 1024);
            channel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * 1024);
            channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            channel.connect(new InetSocketAddress("127.0.0.1", 8080), null,
                    new ClientCompletionHandler(channel));
            while (true) {
                Thread.sleep(5000);
            }
        } else {
            throw new RemoteException("Channel isn't open");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Nio2ClientHandler clientHandler = new Nio2ClientHandler();
        clientHandler.start();
    }
}
