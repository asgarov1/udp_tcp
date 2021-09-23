package com.asgarov.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.asgarov.udp.UDPServer.PORT_NUMBER;


public class UDPClient {

    public static final String EXIT_SIGN = "q";

    public static void main(String[] args) throws Exception {
        runClient();
    }

    private static void runClient() throws IOException {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        while (true) {
            byte[] receiveData = new byte[1024];

            System.out.println("Please enter a sentence to send or " + EXIT_SIGN + " to quit");
            String sentence = inFromUser.readLine();
            if (sentence.equals(EXIT_SIGN)) {
                break;
            }
            sendPacket(clientSocket, sentence);

            String modifiedSentence = receiveResponse(clientSocket, receiveData);
            System.out.println("FROM SERVER: " + modifiedSentence);
        }
        clientSocket.close();
    }

    private static String receiveResponse(DatagramSocket clientSocket, byte[] receiveData) throws IOException {
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        return new String(receivePacket.getData());
    }

    private static void sendPacket(DatagramSocket clientSocket, String sentence) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(sentence.getBytes(), sentence.getBytes().length, getLocalhostAddress(), PORT_NUMBER);
        clientSocket.send(sendPacket);
    }

    private static InetAddress getLocalhostAddress() throws UnknownHostException {
        return InetAddress.getByName("localhost");
    }
}
