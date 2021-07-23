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

import java.io.IOException;
import java.io.InputStream;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    private IntegratedServer server;

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    public void getAlternativeWindowTitle(CallbackInfoReturnable<String> ci){
        String builder = WindowTitleChanger.config.windowTitle;
        
        public static final MinecraftClient MC = MinecraftClient.getInstance();

        builder = builder.replace("{version}", SharedConstants.getGameVersion().getId());

        boolean issinglePlayer = MC.isInSingleplayer();
        
    	String serverip = "";
    	if (MC.getCurrentServerEntry() != null) {
    		serverip = MC.getCurrentServerEntry().address;
    	}
       
        if(MC.getCurrentServerEntry() != null) {
            builder = builder.replace("{env}", serverip);
        } else if(issinglePlayer) {
            builder = builder.replace("{env}", "singleplayer");
        }
        else {
        	builder = builder.replace("{env}", "Main Menu");
        }

        if(WindowTitleChanger.config.changeTitle) ci.setReturnValue(builder);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setIcon(Ljava/io/InputStream;Ljava/io/InputStream;)V"))
    public void setAlternativeWindowIcon(Window window, InputStream inputStream1, InputStream inputStream2) throws IOException {
        if(WindowTitleChanger.resources.changeIcons()) {
            window.setIcon(
                    WindowTitleChanger.resources.get16Icon(),
                    WindowTitleChanger.resources.get32Icon()
            );
        } else {
            window.setIcon(inputStream1, inputStream2);
        }
    }
}
