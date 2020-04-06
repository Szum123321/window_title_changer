package net.szum123321.window_title_changer.mixin;

import jdk.internal.jline.internal.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.server.integrated.IntegratedServer;
import net.szum123321.window_title_changer.WindowTitleChanger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    private Window window;

    @Shadow @Nullable
    private IntegratedServer server;

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    public void getAlternativeWindowTitle(CallbackInfoReturnable<String> ci){
        String builder = WindowTitleChanger.resources.getNewTitle();

        builder = builder.replace("{version}", SharedConstants.getGameVersion().getId());

        if(this.server != null && this.server.isRemote()){
            builder = builder.replace("{env}", "server");
        }else{
            builder = builder.replace("{env}", "singleplayer");
        }

        if(WindowTitleChanger.resources.titleIsAvailable())
            ci.setReturnValue(builder);
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
