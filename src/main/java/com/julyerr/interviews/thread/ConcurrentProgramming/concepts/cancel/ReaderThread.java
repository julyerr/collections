package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.cancel;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException{
        this.socket =socket;
        this.in = socket.getInputStream();
    }

    public void interrupt(){
        try {
            socket.close();
        } catch (IOException e) {

        }finally {
//            中断该线程
            super.interrupt();
        }
    }

    @Override
    public void run() {
        byte[] buf = new byte[1024];
        try {
            while(true){
                int count = in.read(buf);
                if(count<0){
                    break;
                }else if(count>0){
                    processBuffer(buf,count);
                }
            }
        } catch (IOException e) {
//            允许线程退出
        }
    }

    private void processBuffer(byte[] buf,int count){
    }
}
