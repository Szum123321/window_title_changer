package net.szum123321.window_title_changer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;

public class ResourceProvider {
    private final Path mainPath;
    private boolean ok;

    public ResourceProvider(){
        mainPath = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("WindowTitleChanger/");

        if(!mainPath.toFile().exists()){
            mainPath.toFile().mkdirs();
        }

        File icons = mainPath.resolve("icons/").toFile();

        if(!icons.exists())
            icons.mkdirs();
    }

    public boolean titleIsAvailable() {
        return !WindowTitleChanger.config.windowTitle.equals("") && WindowTitleChanger.config.changeTitle;
    }

    public String getNewTitle() {
        return WindowTitleChanger.config.windowTitle;
    }

    public boolean iconsAreAvailableAndShouldBeChanged() {
        File f1 = mainPath.resolve("icons/" + WindowTitleChanger.config.icon16x16).toFile();
        File f2 = mainPath.resolve("icons/" + WindowTitleChanger.config.icon32x32).toFile();

        if(!(f1.exists() && f2.exists()) && WindowTitleChanger.config.changeIcons){
            WindowTitleChanger.logger.error("Error! no icons found! Default icons will be used instead.");
        }

        return f1.exists() && f2.exists() && WindowTitleChanger.config.changeIcons;
    }

    public Optional<InputStream> get16Icon() {
        File f = mainPath.resolve("icons/" + WindowTitleChanger.config.icon16x16).toFile();

        if(!f.exists())
            return Optional.empty();

        try {
            return Optional.of(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<InputStream> get32Icon() {
        File f = mainPath.resolve("icons/" + WindowTitleChanger.config.icon32x32).toFile();

        if(!f.exists())
            return Optional.empty();

        try {
            return Optional.of(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
