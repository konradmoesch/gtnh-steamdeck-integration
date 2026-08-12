package ch.kmoes.steamdeckintegration.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ch.kmoes.steamdeckintegration.SteamHelper;

@Pseudo
@Mixin(remap = false, targets = "appeng.client.gui.widgets.MEGuiTextField")
public class MixinMEGuiTextField {

    @Shadow(remap = false)
    public int x;
    @Shadow(remap = false)
    public int y;

    @Inject(method = "setFocused", at = @At("TAIL"))
    private void steamdeckintegration$showKeyboard(boolean focus, CallbackInfo ci) {
        System.out.println("New focus: " + focus);
        if (focus) {
            SteamHelper.openKeyboard(x, y);
            System.out.println("Keyboard would be shown!");
        }
    }
}
