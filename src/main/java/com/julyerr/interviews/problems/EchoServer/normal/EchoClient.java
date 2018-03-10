package com.julyerr.interviews.problems.EchoServer.normal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost",6789);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input something");
        String msg = scanner.nextLine();
        scanner.close();

        PrintWriter printWriter = new PrintWriter(client.getOutputStream());
        printWriter.println(msg);
        printWriter.flush();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println(bufferedReader.read());
        client.close();
    }
}
