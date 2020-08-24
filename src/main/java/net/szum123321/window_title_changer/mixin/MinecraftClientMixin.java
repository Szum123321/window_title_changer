package net.szum123321.window_title_changer.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.server.integrated.IntegratedServer;
import net.szum123321.window_title_changer.WindowTitleChanger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    private IntegratedServer server;

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    public void getAlternativeWindowTitle(CallbackInfoReturnable<String> ci){
        String builder = WindowTitleChanger.config.windowTitle;

        builder = builder.replace("{version}", SharedConstants.getGameVersion().getId());

        if(this.server != null && this.server.isRemote()){
            builder = builder.replace("{env}", "server");
        }else{
            builder = builder.replace("{env}", "singleplayer");
        }

        if(WindowTitleChanger.config.changeTitle)
            ci.setReturnValue(builder);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setIcon(Ljava/io/InputStream;Ljava/io/InputStream;)V"))
    public void setAlternativeWindowIcon(Window window, InputStream inputStream1, InputStream inputStream2) {
        if(WindowTitleChanger.resources.iconsAreAvailableAndShouldBeChanged()) {
            window.setIcon(
                    WindowTitleChanger.resources.get16Icon(),
                    WindowTitleChanger.resources.get32Icon()
            );
        } else {
            window.setIcon(inputStream1, inputStream2);
        }
    }
}
