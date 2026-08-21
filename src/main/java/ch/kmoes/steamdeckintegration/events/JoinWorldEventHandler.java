package ch.kmoes.steamdeckintegration.events;

import ch.kmoes.steamdeckintegration.utils.ChatLogHelper;
import ch.kmoes.steamdeckintegration.utils.SteamHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class JoinWorldEventHandler {

    private boolean shown = false;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (shown) {
            return;
        }
        if (!(event.entity instanceof EntityPlayer player)) {
            return;
        }

        if (player != Minecraft.getMinecraft().thePlayer) {
            return;
        }

        shown = true;

        if (SteamHelper.isSteamAvailable() && SteamHelper.isOnSteamDeck()) {
            ChatLogHelper.showMessage("Steam Deck integration loaded");
        }
    }
}
