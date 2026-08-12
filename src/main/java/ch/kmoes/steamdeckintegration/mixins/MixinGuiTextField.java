package ch.kmoes.steamdeckintegration.mixins;

import net.minecraft.client.gui.GuiTextField;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ch.kmoes.steamdeckintegration.SteamHelper;

@Mixin(GuiTextField.class)
public class MixinGuiTextField {

    @Shadow
    public int xPosition;
    @Shadow
    public int yPosition;
    @Shadow
    public int width;
    @Shadow
    public int height;

    @Inject(method = "setFocused", at = @At("TAIL"))
    private void steamdeckintegration$showKeyboard(boolean p_146195_1_, CallbackInfo ci) {
        System.out.println("New focus: " + p_146195_1_);
        if (p_146195_1_) {
            SteamHelper.openKeyboard(xPosition, yPosition, width, height);
            System.out.println("Keyboard would be shown!");
        }
    }
}
