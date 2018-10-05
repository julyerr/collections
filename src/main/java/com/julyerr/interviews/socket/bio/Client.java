package com.julyerr.interviews.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8080);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String input = inputReader.readLine();
        while(!input.equals("exit")){
            writer.println(input);
            writer.flush();
            System.out.println("server response: "+reader.readLine());
            input  = inputReader.readLine();
        }
        writer.close();
        reader.close();
        socket.close();
    }
}
