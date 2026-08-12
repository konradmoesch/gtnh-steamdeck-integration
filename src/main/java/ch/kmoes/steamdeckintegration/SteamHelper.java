package ch.kmoes.steamdeckintegration;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SteamHelper {

    private static boolean steamAvailable;
    private static boolean onSteamDeck;

    public static void init() {
        // TODO: test for running steam and steam deck here
        steamAvailable = true;
        onSteamDeck = true;
    }

    public static void openKeyboard(int x, int y) {
        if (steamAvailable && onSteamDeck) {
            showFloatingGamepadTextInput(0, x, y, 100, 10);
        }
    }

    private static void showFloatingGamepadTextInput(int inputMode, int x, int y, int width, int height) {
        try {
            IPCHelper ipcHelper = new IPCHelper();
            byte[] packet1 = { 0x26, 0x00, 0x00, 0x00 };
            ByteBuffer b = ByteBuffer.allocate(38);
            byte[] header = { 0x01, 0x04, 0x00, 0x00, 0x00, 0x00, 0x39, 0x03, (byte) 0x82, 0x73 };
            byte[] trailer = { 0x00, 0x00, 0x00, 0x00, (byte) 0x97, 0x2a, 0x53, 0x75 };
            b.order(ByteOrder.LITTLE_ENDIAN);
            b.put(header);
            b.putInt(inputMode);
            b.putInt(x);
            b.putInt(y);
            b.putInt(width);
            b.putInt(height);
            b.put(trailer);
            byte[] packet2 = b.array();
            ipcHelper.sendIpcPacket(packet1, packet1.length);
            ipcHelper.sendIpcPacket(packet2, packet2.length);
            byte[] response1 = new byte[5];
            byte[] response2 = new byte[1];
            ipcHelper.receiveIpcPacket(response1, 5);
            ipcHelper.receiveIpcPacket(response2, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
