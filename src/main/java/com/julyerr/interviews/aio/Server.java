//package com.julyerr.interviews.aio;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.CharBuffer;
//import java.nio.channels.AsynchronousChannelGroup;
//import java.nio.channels.AsynchronousServerSocketChannel;
//import java.nio.channels.AsynchronousSocketChannel;
//import java.nio.channels.CompletionHandler;
//import java.nio.charset.Charset;
//import java.nio.charset.CharsetDecoder;
//import java.nio.charset.CharsetEncoder;
//import java.util.Date;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.concurrent.TimeUnit;
//
//public class Server {
//    private static Charset charset = Charset.forName("US-ASCII");
//    private static CharsetEncoder encoder = charset.newEncoder();
//
//    public static void main(String[] args) throws Exception {
//        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(4));
//        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress("0.0.0.0", 8013));
//        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
//            @Override
//            public void completed(AsynchronousSocketChannel result, Void attachment) {
//                server.accept(null, this); // 接受下一个连接
//                try {
//                    String now = new Date().toString();
//                    ByteBuffer buffer = encoder.encode(CharBuffer.wrap(now + "\r\n"));
////                    result.write(buffer, null, new CompletionHandler<Integer,Void>(){...}); //callback or
//                    Future<Integer> f = result.write(buffer);
//                    f.get();
//                    System.out.println("sent to client: " + now);
//                    result.close();
//                } catch (IOException | InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void failed(Throwable exc, Void attachment) {
//                exc.printStackTrace();
//            }
//        });
//        group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
//
////        final AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel
////                .open()
////                .bind(new InetSocketAddress("0.0.0.0",8888));
////        channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
////            @Override
////            public void completed(final AsynchronousSocketChannel client, Void attachment) {
////                channel.accept(null, this);
////
////                ByteBuffer buffer = ByteBuffer.allocate(1024);
////                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
////                    @Override
////                    public void completed(Integer result_num, ByteBuffer attachment) {
////                        attachment.flip();
////                        CharBuffer charBuffer = CharBuffer.allocate(1024);
////                        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
////                        decoder.decode(attachment,charBuffer,false);
////                        charBuffer.flip();
////                        String data = new String(charBuffer.array(),0, charBuffer.limit());
////                        System.out.println("read data:" + data);
////                        try{
////                            client.close();
////                        }catch (Exception e){}
////                    }
////
////                    @Override
////                    public void failed(Throwable exc, ByteBuffer attachment) {
////                        System.out.println("read error");
////                    }
////                });
////            }
////
////            @Override
////            public void failed(Throwable exc, Void attachment) {
////                System.out.println("accept error");
////            }
////        });
////
////        while (true){
////            Thread.sleep(1000);
////        }
//    }
//}