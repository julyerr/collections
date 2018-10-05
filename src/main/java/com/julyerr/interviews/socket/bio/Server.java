package com.julyerr.interviews.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = server.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        System.out.println("Client request: "+reader.readLine());
        String input = inputReader.readLine();
        while(!input.equals("exit")){
            writer.println(input);
            writer.flush();
            System.out.println("Client request: "+reader.readLine());
            input = inputReader.readLine();
        }
        inputReader.close();
        reader.close();
        writer.close();
        socket.close();
        server.close();
    }
}
