package ch.kmoes.steamdeckintegration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class IPCHelper {

    private final Socket socket;

    public IPCHelper() throws IOException {
        socket = new Socket("127.0.0.1", 57343);
    }

    public void sendIpcPacket(byte[] data, int length) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(data, 0, length);
            System.out.println("sent data via socket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveIpcPacket(byte[] data, int length) {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            int read_bytes = in.read(data, 0, length);
            System.out.println("received data from socket (" + read_bytes + ")");
            for (byte b : data) {
                System.out.printf("#%x ", b);
            }
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
