package net.szum123321.window_title_changer;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class ResourceProvider {
    private final Path mainPath;
    private final Path iconsFolderPath;
    private final boolean areIconsAvailable;

    public ResourceProvider() {
        mainPath = FabricLoader.getInstance().getConfigDir().resolve("WindowTitleChanger/").toAbsolutePath();
        iconsFolderPath = mainPath.resolve("icons");

        createFolders();

        areIconsAvailable = checkIcons();
    }

    public boolean titleIsAvailable() {
        return !WindowTitleChanger.config.windowTitle.equals("") && WindowTitleChanger.config.changeTitle;
    }

    public String getNewTitle() {
        return WindowTitleChanger.config.windowTitle;
    }

    public boolean iconsAreAvailableAndShouldBeChanged() {
        return areIconsAvailable && WindowTitleChanger.config.changeIcons;
    }

    public InputStream get16Icon() {
        File f = mainPath.resolve("icons/" + WindowTitleChanger.config.icon16x16).toFile();

        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException ignored) {
            return null;
        }
    }

    public InputStream get32Icon() {
        File f = mainPath.resolve("icons/" + WindowTitleChanger.config.icon32x32).toFile();

        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException ignored) {
            return null;
        }
    }

    private void createFolders() {
        if(!mainPath.toFile().exists())
            mainPath.toFile().mkdirs();

        File icons = iconsFolderPath.toFile();

        if(!icons.exists())
            icons.mkdirs();
    }

    private boolean checkIcons() {
        boolean ok = true;

        if(WindowTitleChanger.config.icon16x16.equals("")) {
            WindowTitleChanger.logger.error("No 16x16 icon provided!");
            ok = false;
        } else {
            File f = iconsFolderPath.resolve(WindowTitleChanger.config.icon16x16).toFile();

            if(!isValidIcon(f)) {
                WindowTitleChanger.logger.error("File %s is not a valid icon!", f);
                ok = false;
            }
        }

        if(WindowTitleChanger.config.icon32x32.equals("")) {
            WindowTitleChanger.logger.error("No 32x32 icon provided!");
            ok = false;
        } else {
            File f = iconsFolderPath.resolve(WindowTitleChanger.config.icon32x32).toFile();

            if(!isValidIcon(f)) {
                WindowTitleChanger.logger.error("File %s is not a valid icon!", f);
                ok = false;
            }
        }

        if(!ok) {
            WindowTitleChanger.logger.error("Because of the above error, window icon won't be changed!");
            WindowTitleChanger.logger.error("If you're playing on a modpack, please report this error to modpack's creator!");
        }

        return ok;
    }

    private boolean isValidIcon(File f) {
        return f.exists() && !f.isDirectory() && f.getName().substring(f.getName().lastIndexOf(".") + 1).equals("png");
    }
}
