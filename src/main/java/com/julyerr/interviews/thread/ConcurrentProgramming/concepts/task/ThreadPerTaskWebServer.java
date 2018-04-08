package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.julyerr.interviews.thread.ConcurrentProgramming.concepts.task.SingleThreadWebServer.handleRequest;

public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    handleRequest(connection);
                }
            };
            new Thread(task).start();
        }
    }
}
