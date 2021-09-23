package com.asgarov.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static com.asgarov.tcp.TCPServer.PORT;


public class TCPClient {

    public static final String EXIT_SIGN = "q";

    public static void main(String[] args) throws Exception {
        runClient();
    }

    private static void runClient() throws IOException {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try (Socket clientSocket = new Socket("localhost", PORT)) {

                String sentence = getUserInput(inFromUser);
                if (sentence.equals(EXIT_SIGN)) {
                    break;
                }
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println(sentence);

                String response = new String(clientSocket.getInputStream().readAllBytes());
                System.out.println("FROM " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ": " + response);
            }
        }
    }

    private static String getUserInput(BufferedReader inFromUser) throws IOException {
        System.out.println("Please enter a sentence to send or " + EXIT_SIGN + " to quit");
        return inFromUser.readLine();
    }
}
