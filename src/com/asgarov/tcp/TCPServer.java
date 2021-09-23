package com.asgarov.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static final int PORT = 9877;

    public static void main(String[] args) throws IOException {
        runServer(PORT);
    }

    private static void runServer(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server is up and running...");
        while (true) {
            try (Socket serverSocket = server.accept()) {
                String message = getMessage(serverSocket);

                System.out.println("Received: " + message + ", from " + serverSocket.getInetAddress() + ":" + serverSocket.getPort());
                PrintWriter writer = new PrintWriter(serverSocket.getOutputStream(), true);

                String response = message.toUpperCase();
                writer.println(response);
                System.out.println("Sent back: " + response);
                System.out.println();
            }
        }
    }

    private static String getMessage(Socket serverSocket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        return reader.readLine();
    }
}
