package ch.kmoes.steamdeckintegration.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ch.kmoes.steamdeckintegration.SteamHelper;

@Pseudo
@Mixin(remap = false, targets = "net.minecraft.client.gui.GuiTextField")
public class MixinGuiTextField {

    @Shadow(remap = false)
    public int xPosition;
    @Shadow(remap = false)
    public int yPosition;
    @Shadow(remap = false)
    public int width;
    @Shadow(remap = false)
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
