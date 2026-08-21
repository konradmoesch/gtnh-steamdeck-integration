package ch.kmoes.steamdeckintegration.utils;

import ch.kmoes.steamdeckintegration.SteamDeckIntegrationMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatLogHelper {

    public static void showMessage(String message) {
        SteamDeckIntegrationMod.LOG.debug("showing message in chat: {}", message);
        ChatComponentText chat = new ChatComponentText(message);
        Minecraft mc = Minecraft.getMinecraft();
        mc.ingameGUI.getChatGUI()
            .printChatMessage(chat);
    }
}
