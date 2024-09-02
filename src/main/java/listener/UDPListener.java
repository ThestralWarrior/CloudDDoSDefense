package listener;

import core.DetectionEngine;

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
            e.printStackTrace();
        }
    }
    public void handlePacket(DatagramPacket packet) {
        String ipAddress = packet.getAddress().getHostAddress();
        int packetSize = packet.getLength();
        System.out.println("Received packet: " + packet);
        engine.runAnalysis(ipAddress, "UDP", packetSize);
    }
}
