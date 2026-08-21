package ch.kmoes.steamdeckintegration.utils;

import ch.kmoes.steamdeckintegration.SteamDeckIntegrationMod;
import ch.kmoes.steamdeckintegration.ipc.IPCHelper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class SteamHelper {

    private static boolean steamAvailable;

    public static boolean isOnSteamDeck() {
        return onSteamDeck;
    }

    public static boolean isSteamAvailable() {
        return steamAvailable;
    }

    private static boolean onSteamDeck;

    private static IPCHelper ipcHelper;

    public static void init() {
        try {
            ipcHelper = new IPCHelper();
            // Test if steam running
            String installPath = sendGetInstallPathPacket(ipcHelper);
            SteamDeckIntegrationMod.LOG.debug("install path: {}", installPath);
            steamAvailable = true;
            // TODO: Test whether on steam deck
            onSteamDeck = true;
            SteamDeckIntegrationMod.LOG.info("Successfully connected to Steam");
        } catch (IOException e) {
            SteamDeckIntegrationMod.LOG.warn("no connection to steam");
            steamAvailable = false;
            onSteamDeck = false;
        }
    }

    public static void openKeyboard(int x, int y, int width, int height) {
        if (steamAvailable && onSteamDeck) {
            sendShowFloatingGamepadTextInputPacket(ipcHelper, 0, x, y, width, height);
        }
    }

    private static String sendGetInstallPathPacket(IPCHelper ipcHelper) {
        byte[] packet1 = { 0x0e, 0x00, 0x00, 0x00 };
        ipcHelper.sendIpcPacket(packet1, packet1.length);
        byte[] packet2 = { 0x01, 0x04, 0x00, 0x00, 0x00, 0x00, (byte) 0xdc, 0x36, 0x72, (byte) 0xab, 0x46, 0x51, 0x07,
            (byte) 0xad };
        ipcHelper.sendIpcPacket(packet2, packet2.length);

        byte[] response1 = new byte[4];
        ipcHelper.receiveIpcPacket(response1, 4);
        ByteBuffer responseBuffer = ByteBuffer.wrap(response1);
        responseBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int responseLength = responseBuffer.getInt();

        byte[] response2 = new byte[responseLength];
        ipcHelper.receiveIpcPacket(response2, responseLength);
        ByteBuffer response2Buffer = ByteBuffer.wrap(response2);
        response2Buffer.order(ByteOrder.LITTLE_ENDIAN);
        String installPath = StandardCharsets.UTF_8.decode(response2Buffer)
            .toString();
        return installPath;
    }

    private static void sendShowFloatingGamepadTextInputPacket(IPCHelper ipcHelper, int inputMode, int x, int y,
        int width, int height) {
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
    }
}
