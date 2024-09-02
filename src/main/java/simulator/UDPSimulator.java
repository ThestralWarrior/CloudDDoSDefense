package simulator;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSimulator {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8081;

    public void simulateConnection() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            byte[] buffer = "Hello, this is a test UDP packet.".getBytes();
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, SERVER_PORT);
            socket.send(packet);
            System.out.println("Packet sent to " + SERVER_ADDRESS + ":" + SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 20; i++) {
            UDPSimulator simulator = new UDPSimulator();
            simulator.simulateConnection();
        }
    }
}
