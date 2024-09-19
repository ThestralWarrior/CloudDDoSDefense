package simulator;

import java.io.OutputStream;
import java.net.Socket;

public class TCPSimulator {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public void simulateConnection(int num) {
        Socket socket = null;
        OutputStream outputStream = null;
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            outputStream = socket.getOutputStream();
            String message = "Hi, sending this via TCP (" + num + ")" ;
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch(Exception e) {
            System.err.println("Error while trying to perform TCP simulation.");
        } finally {
            try {
                if(outputStream != null) {
                    outputStream.close();
                }
                if(socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch(Exception e) {
                System.err.println("Error while trying to close TCP-related connections.");
            }
        }
    }
    public static void main(String[] args) {
        for(int i = 0; i < 15; i++) {
            TCPSimulator sim = new TCPSimulator();
            sim.simulateConnection(i + 1);
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.err.println("Error while getting tcp simulator to wait.");
            }
        }
    }
}
