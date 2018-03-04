package com.julyerr.interviews.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

//处理tcp连接的接口
public interface TCPProtocol {
    void handleAccept(SelectionKey key) throws IOException;
    void handleRead(SelectionKey key) throws IOException;
    void handleWrite(SelectionKey key) throws IOException;
}
