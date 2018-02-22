package com.julyerr.interviews.nio;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonBlocking {
    public static void main(String args[]) throws Exception {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
        }

        String server = args[0];
        byte[] argument = args[1].getBytes();

        int serverPort = (args.length == 3) ? Integer.parseInt(args[2]) : 8888;
        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking(false);

//        非阻塞模式，客户端连接需要循环判断处理
        if (!clntChan.connect(new InetSocketAddress(server, serverPort))) {
            while (!clntChan.finishConnect()) {
                System.out.println(".");
            }
        }
        ByteBuffer writeBuf = ByteBuffer.wrap(argument);
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
        int totalBytesRecvd = 0;
        int bytesRecvd;
        while (totalBytesRecvd < argument.length) {
//            写数据
            if (writeBuf.hasRemaining()) {
                clntChan.write(writeBuf);
            }
//            持续读取数据
            if ((bytesRecvd = clntChan.read(readBuf)) == -1) {
                throw new SocketException("Connection closed permaturely");
            }
            totalBytesRecvd += bytesRecvd;
            System.out.println(".");
        }
        System.out.println("Received: " + new String(readBuf.array(), 0, totalBytesRecvd));
        ;
//        关闭连接
        clntChan.close();
    }
}
