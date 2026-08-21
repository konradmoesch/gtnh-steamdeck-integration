package ch.kmoes.steamdeckintegration;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatLogHelper {

    private static final Logger LOGGER = LogManager.getLogger("steamdeck-integration");

    public static void showMessage(String message) {
        LOGGER.debug("showing message in chat: {}", message);
        ChatComponentText chat = new ChatComponentText(message);
        Minecraft mc = Minecraft.getMinecraft();
        mc.ingameGUI.getChatGUI()
            .printChatMessage(chat);
    }
}
