package ch.kmoes.steamdeckintegration.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "appeng.client.gui.widgets.MEGuiTextField")
public class MixinMEGuiTextField {

    @Inject(method = "setFocused", at = @At("TAIL"))
    private void steamdeckintegration$showKeyboard(boolean focus, CallbackInfo ci) {
        System.out.println("New focus: " + focus);
        if (focus) {
            System.out.println("Keyboard would be shown!");
        }
    }
}
