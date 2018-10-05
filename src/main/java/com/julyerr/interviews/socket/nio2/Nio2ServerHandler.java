package com.julyerr.interviews.socket.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.ExecutionException;

public class Nio2ServerHandler {
    private AsynchronousServerSocketChannel serverSocketChannel;

    class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
        private AsynchronousServerSocketChannel serverSocketChannel;
        private ByteBuffer buffer = ByteBuffer.allocate(1024);
        private CharBuffer charBuffer;
        private CharsetDecoder decoder = Charset.defaultCharset().newDecoder();

        public ServerCompletionHandler(AsynchronousServerSocketChannel serverSocketChannel) {
            this.serverSocketChannel = serverSocketChannel;
        }

        @Override
        public void completed(AsynchronousSocketChannel result, Void attachment) {
//            立即接收下一个请求
            try {
                serverSocketChannel.accept(null, this);
                while (result.read(buffer).get() != -1) {
                    buffer.flip();
                    charBuffer = decoder.decode(buffer);
                    String request = charBuffer.toString().trim();
                    System.out.println("Client request: " + request);
                    ByteBuffer outBuffer = ByteBuffer.wrap(request.getBytes());
                    result.write(outBuffer);
                    if (buffer.hasRemaining()) {
                        buffer.compact();
                    } else {
                        buffer.clear();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            } finally {
                try {
                    result.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void failed(Throwable exc, Void attachment) {
            throw new RuntimeException("Connect failed");
        }
    }

    public void init() throws IOException {
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        if (serverSocketChannel.isOpen()) {
            serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8080));
        } else {
            throw new RuntimeException("Channel isn't open");
        }
    }

    public void start() {
        System.out.println("Waiting for client request");
        serverSocketChannel.accept(null, new ServerCompletionHandler(serverSocketChannel));
    }

    public static void main(String[] args) throws IOException {
        Nio2ServerHandler serverHandler = new Nio2ServerHandler();
        serverHandler.init();
        serverHandler.start();
    }
}
