package net.szum123321.window_title_changer.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.util.Window;
import net.szum123321.window_title_changer.WindowTitleChanger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    Window window;

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    public void getAlternativeWindowTitle(CallbackInfoReturnable<String> ci){

        if(WindowTitleChanger.resources.titleIsAvailable())
            ci.setReturnValue(WindowTitleChanger.resources.getNewTitle());
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void setAlternativeWindowIcon(CallbackInfo ci){
        try{
            if(WindowTitleChanger.resources.iconsAreAvailable()){
                window.setIcon(WindowTitleChanger.resources.get16Icon(), WindowTitleChanger.resources.get32Icon());
            }
        }catch (Exception e){
            WindowTitleChanger.logger.error(e.getMessage());
        }
    }
}
