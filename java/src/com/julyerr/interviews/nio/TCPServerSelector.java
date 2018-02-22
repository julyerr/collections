package com.julyerr.interviews.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TCPServerSelector {
    private static final int BUFSIZE = 256;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) throws Exception {
//        监听端口
        if (args.length < 1) {
            throw new IllegalArgumentException("Paramter(s): <Port> ...");
        }
        Selector selector = Selector.open();
//        可以监听多个端口
        for (String arg :
                args) {
            ServerSocketChannel listnChannel = ServerSocketChannel.open();
            listnChannel.socket().bind(new InetSocketAddress(Integer.parseInt(arg)));
//            设置为非阻塞模式
            listnChannel.configureBlocking(false);
            listnChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        TCPProtocol protocol = (TCPProtocol) new EchoSelectorProtocol(BUFSIZE);
        while (true) {
//            等待TIMEOUT时间返回
            if (selector.select(TIMEOUT) == 0) {
                System.out.println(".");
                continue;
            }
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();
                if (key.isAcceptable()) {
                    protocol.handleAccept(key);
                }
                if (key.isReadable()) {
                    protocol.handleRead(key);
                }
//                通道没有关闭，并且客户端写通道就绪
                if (key.isValid() && key.isWritable()) {
                    protocol.handleWrite(key);
                }
//                移除readOps，下次事件来临，对应的标志位会被重新设置
                keyIter.remove();
            }
        }
    }
}
