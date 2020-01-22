package net.szum123321.window_title_changer;

import io.github.cottonmc.cotton.logging.ModLogger;
import net.fabricmc.api.ClientModInitializer;

import java.io.IOException;


public class WindowTitleChanger implements ClientModInitializer {
    public static final String MOD_ID = "window_title_changer";
    public static ResourceProvider resources;
    public static ModLogger logger;

    @Override
    public void onInitializeClient() {
        logger = new ModLogger(this.getClass());

        logger.info("Loading Window Title Changer by Szum123321");

        resources = new ResourceProvider();

        try {
            resources.init();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
