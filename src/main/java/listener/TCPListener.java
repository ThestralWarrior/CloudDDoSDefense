package listener;

import core.DetectionEngine;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPListener extends Thread {
    private static final int PORT = 8080;
    DetectionEngine engine;
    public TCPListener(DetectionEngine engine) {
        this.engine = engine;
    }
    @Override
    public void run() {
        startListening();
    }
    public void startListening() {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            while(true) {
                Socket socket = serverSocket.accept();
                handleSocket(socket);
            }
        } catch(Exception e) {
            System.err.println("Error while trying to listen to TCP.");
        }
    }
    public void handleSocket(Socket socket) {
        String ipAddress = socket.getInetAddress().getHostAddress();
        System.out.println("Connected: " + socket.getInetAddress().getHostAddress());
        engine.runAnalysis(ipAddress, "TCP", 1024);
    }
}
