package net.szum123321.window_title_changer;

import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.logging.ModLogger;
import net.fabricmc.api.ClientModInitializer;

public class WindowTitleChanger implements ClientModInitializer {
    public static ModLogger logger = new ModLogger("window_title_changer", "Window Title Changer");
    public static ConfigHandler config;
    public static ResourceProvider resources;

    @Override
    public void onInitializeClient() {
        config = ConfigManager.loadConfig(ConfigHandler.class);
        resources = new ResourceProvider();
    }
}
