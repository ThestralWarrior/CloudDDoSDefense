package core;

import ddos.AttackDetector;
import ddos.TcpFloodDetector;
import ddos.UdpFloodDetector;
import listener.TCPListener;
import listener.UDPListener;

import java.util.ArrayList;
import java.util.List;

public class DDoSDefender {
    public static void main(String[] args) {
        List<AttackDetector> attackDetectors = new ArrayList<>();
        TcpFloodDetector tcp = new TcpFloodDetector();
        UdpFloodDetector udp = new UdpFloodDetector();
        attackDetectors.add(tcp);
        attackDetectors.add(udp);

        DetectionEngine engine = new DetectionEngine(attackDetectors);
        TCPListener tcplistener = new TCPListener(engine);
        UDPListener udpListener = new UDPListener(engine);

        System.out.println("About to start listening for TCP connections & UDP packets...");
        tcplistener.start();
        udpListener.start();
    }
}
