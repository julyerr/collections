package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LifecycleWebServer {
    private final ExecutorService exec = Executors.newFixedThreadPool(10);

    public void start() throws IOException{
        ServerSocket serverSocket = new ServerSocket(80);
        while(!exec.isShutdown()){
            final Socket connection = serverSocket.accept();
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    handleRequest(connection);
                }
            });
        }
    }

    public void stop(){
        exec.shutdown();
    }

    void handleRequest(Socket connection){
        Request request = readRequest(connection);
        if(isShutdownRequest(request)){
            stop();
        }else{
            dispathRequest(request);
        }
    }

    class Request{}
    private Request readRequest(Socket socket){
        return new Request();
    }

    private void dispathRequest(Request request){

    }

    private boolean isShutdownRequest(Request request){
        return false;
    }
}
