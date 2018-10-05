//package com.julyerr.interviews.aio;
//
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.AsynchronousSocketChannel;
//import java.nio.channels.CompletionHandler;
//import java.util.concurrent.Future;
//
//public class Client {
//    public static void main(String[] args) throws Exception {
//        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
//        Future<Void> future = client.connect(new InetSocketAddress("127.0.0.1", 8013));
//        future.get();
//
//        ByteBuffer buffer = ByteBuffer.allocate(100);
//        client.read(buffer, null, new CompletionHandler<Integer, Void>() {
//            @Override
//            public void completed(Integer result, Void attachment) {
//                System.out.println("client received: " + new String(buffer.array()));
//
//            }
//            @Override
//            public void failed(Throwable exc, Void attachment) {
//                exc.printStackTrace();
//                try {
//                    client.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        Thread.sleep(10000);
//
////        AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
////        channel.connect(new InetSocketAddress("127.0.0.1",8888)).get();
////        ByteBuffer buffer = ByteBuffer.wrap("中文,你好".getBytes());
////        Future future = channel.write(buffer);
////
////        future.get();
////        System.out.println("send ok");
//    }
//}
