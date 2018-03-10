package com.julyerr.interviews.problems.EchoServer.normal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private static final int ECHO_SERVER_PORT = 6789;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(ECHO_SERVER_PORT)) {
            System.out.println("Server started...");
            while (true) {
                Socket client = serverSocket.accept();
                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        @Override
        public void run() {
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()))){
                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                String msg = bufferedReader.readLine();
                System.out.println("received:"+client.getInetAddress()+":"+msg);
                printWriter.print(msg);
                printWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        private Socket client;

        public ClientHandler(Socket client) {
            this.client = client;
        }
    }
}
