package ch.kmoes.steamdeckintegration.ipc;

import ch.kmoes.steamdeckintegration.SteamDeckIntegrationMod;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class IPCHelper {

    private final Socket socket;

    // TODO: handle connection refused (e.g. no Steam running)
    public IPCHelper() throws IOException {
        socket = new Socket("127.0.0.1", 57343);
    }

    public void sendIpcPacket(byte[] data, int length) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(data, 0, length);
            SteamDeckIntegrationMod.LOG.debug("sent data via socket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveIpcPacket(byte[] data, int length) {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            int read_bytes = in.read(data, 0, length);
            SteamDeckIntegrationMod.LOG.debug("received data from socket ({}): {}", read_bytes, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
