package listener;

import core.DetectionEngine;
import core.Mitigation;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPListener extends Thread {
    private static final int PORT = 8081;
    private static final int BUFFER_SIZE = 1024;
    DetectionEngine engine;
    public UDPListener(DetectionEngine engine) {
        this.engine = engine;
    }
    @Override
    public void run() {
        startListening();
    }
    public void startListening() {
        try(DatagramSocket datagramSocket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            while(true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                handlePacket(packet);
            }
        } catch(Exception e) {
            System.err.println("Error while trying to listen to UDP.");
        }
    }
    public void handlePacket(DatagramPacket packet) {
        String ipAddress = packet.getAddress().getHostAddress();
        if(Mitigation.isBlocked(ipAddress)) {
            System.out.println("Received packet from blocked IP " + ipAddress + ". Ignoring packet.");
        } else {
            int packetSize = packet.getLength();
            String message = new String(packet.getData(), 0, packetSize);
            boolean attackDetected = engine.runAnalysis(ipAddress, "UDP", packetSize);
            if (attackDetected) {
                System.out.println("IP Address " + ipAddress + " has been blocked. Ignoring packet.");
            } else System.out.println("Received packet: " + packet + ", Message: " + message);
        }
    }
}
