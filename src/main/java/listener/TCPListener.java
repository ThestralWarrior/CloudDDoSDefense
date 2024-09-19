package listener;

import core.DetectionEngine;
import core.Mitigation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        if(Mitigation.isBlocked(ipAddress)) {
            System.out.println("Blacklisted IP: " + ipAddress + ". Closing Connection.");
            try {
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            boolean attackDetected = engine.runAnalysis(ipAddress, "TCP", 1024);
            if(attackDetected) {
                System.out.println("IP Address " + ipAddress + " has been blocked. Closing connection.");
                try {
                    socket.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                String input = "NULL";
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    input = reader.readLine();
                    reader.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Connected: " + socket.getInetAddress().getHostAddress() + ", Message: " + input);
            }
        }
    }
}
