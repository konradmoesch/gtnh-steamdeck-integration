package ch.kmoes.steamdeckintegration.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ch.kmoes.steamdeckintegration.utils.SteamHelper;

@Mixin(GuiTextField.class)
public abstract class MixinGuiTextField {

    @Shadow
    public int xPosition;
    @Shadow
    public int yPosition;
    @Shadow
    public int width;
    @Shadow
    public int height;

    @Shadow
    public abstract boolean getVisible();

    @Shadow
    private boolean isEnabled;

    @Inject(method = "setFocused", at = @At("TAIL"))
    private void steamdeckintegration$showKeyboard(boolean p_146195_1_, CallbackInfo ci) {
        if (p_146195_1_ & this.getVisible() & this.isEnabled) {
            Minecraft mc = Minecraft.getMinecraft();

            if (mc.currentScreen != null && mc.currentScreen.getClass()
                .getName()
                .equals("journeymap.client.ui.fullscreen.Fullscreen")) {

                System.out.println("=== JOURNEYMAP TEXTFIELD setFocused ===");

                new Exception("GuiTextField setFocused stack").printStackTrace();
            }

            ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            int scaleFactor = sr.getScaleFactor();
            SteamHelper.openKeyboard(
                xPosition * scaleFactor,
                yPosition * scaleFactor,
                width * scaleFactor,
                height * scaleFactor);
            System.out.println("Keyboard would be shown!");
        }
    }
}
