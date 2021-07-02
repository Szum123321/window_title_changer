package net.szum123321.window_title_changer;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class ResourceProvider {
    private final Path mainPath;
    private final Path iconsFolderPath;

    private boolean changeIcons;

    public ResourceProvider(boolean changeIcons) {
        mainPath = FabricLoader.getInstance().getConfigDir().resolve("WindowTitleChanger/").toAbsolutePath();
        iconsFolderPath = mainPath.resolve("icons");

        createFolders();

        if(changeIcons) this.changeIcons = checkIcons();
    }

    public boolean changeIcons() {
        return changeIcons;
    }

    public InputStream get16Icon() throws IOException {
        return Files.newInputStream(mainPath.resolve("icons/").resolve(WindowTitleChanger.config.icon16x16));
    }

    public InputStream get32Icon() throws IOException {
        return Files.newInputStream(mainPath.resolve("icons/").resolve(WindowTitleChanger.config.icon32x32));
    }

    private void createFolders() {
        if(!mainPath.toFile().exists())
            mainPath.toFile().mkdirs();

        File icons = iconsFolderPath.toFile();

        if(!icons.exists())
            icons.mkdirs();
    }

    private boolean checkIcons() {
        FormatterStringBuilder builder = new FormatterStringBuilder();

        if(WindowTitleChanger.config.icon16x16.equals("")) {
            builder.append("No 16x16 icon provided!");
        } else {
            isValidIcon(iconsFolderPath.resolve(WindowTitleChanger.config.icon16x16).toFile(), builder);
        }

        if(WindowTitleChanger.config.icon32x32.equals("")) {
            builder.append("No 32x32 icon provided!");
        } else {
            isValidIcon(iconsFolderPath.resolve(WindowTitleChanger.config.icon32x32).toFile(), builder);
        }

        if(!builder.isEmpty()) {
            builder.buildStream().forEach(WindowTitleChanger.LOGGER::error);
            WindowTitleChanger.LOGGER.error("Because of the above error, window icon won't be changed!");
            WindowTitleChanger.LOGGER.error("If you're playing on a modpack, please report this error to modpack's creator!");
        }

        return builder.isEmpty();
    }

    private void isValidIcon(File f, FormatterStringBuilder builder) {
        if(!f.exists())
            builder.append("File %s doesn't exists!", f);
        else if(f.isDirectory())
            builder.append("%s is a directory", f);
        else if(!f.getName().substring(f.getName().lastIndexOf(".") + 1).equals("png"))
            builder.append("%s is not a png image!", f);
    }

    private static class FormatterStringBuilder {
        private final StringBuilder builder;

        public FormatterStringBuilder () {
            builder = new StringBuilder();
        }

        public FormatterStringBuilder append (String msg, Object... args) {
            builder.append(String.format(msg, args));
            builder.append("\n");
            return this;
        }

        public boolean isEmpty() {
            return builder.length() == 0;
        }

        public String build() {
            return builder.toString();
        }

        public Stream<String> buildStream() {
            return Arrays.stream(build().split("\n"));
        }
    }
}
