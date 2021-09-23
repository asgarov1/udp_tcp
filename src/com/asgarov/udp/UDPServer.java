package com.asgarov.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    public static final int PORT_NUMBER = 9876;

    public static void main(String[] args) throws Exception {
        runServer(PORT_NUMBER);
    }

    private static void runServer(int portNumber) throws IOException {
        DatagramSocket serverSocket = startServer(portNumber);
        while (true) {
            byte[] receiveData = createABuffer(1024);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            printReceivedMessage(receivePacket);
            String response = capitalizeReceivedSentence(receivePacket);
            sendResponse(serverSocket, receivePacket, response);
        }
    }

    private static byte[] createABuffer(int size) {
        return new byte[size];
    }

    private static DatagramSocket startServer(int portNumber) throws SocketException {
        DatagramSocket serverSocket = new DatagramSocket(portNumber);
        System.out.println("Server is up and running...");
        return serverSocket;
    }

    private static void printReceivedMessage(DatagramPacket receivePacket) {
        System.out.println(new String(receivePacket.getData()));
    }

    private static void sendResponse(DatagramSocket serverSocket, DatagramPacket receivePacket, String response) throws IOException {
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length, IPAddress, port);
        serverSocket.send(sendPacket);
    }

    private static String capitalizeReceivedSentence(DatagramPacket receivePacket) {
        return new String(receivePacket.getData()).toUpperCase();
    }
}
