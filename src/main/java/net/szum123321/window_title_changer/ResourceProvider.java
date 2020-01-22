package net.szum123321.window_title_changer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class ResourceProvider {
    private Path mainPath;
    private ConfigClass cc;
    private boolean ok;

    public ResourceProvider(){
        mainPath = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("WindowTitleChanger/");

        if(!mainPath.toFile().exists()){
            mainPath.toFile().mkdirs();
        }

        ok = false;
    }

    public void init() throws IOException {
        try{
            ok = true;
            File configFile = mainPath.resolve(WindowTitleChanger.MOD_ID + ".json").toFile();

            Gson parser = new GsonBuilder().setPrettyPrinting().create();

            if(!configFile.exists()){
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();

                FileWriter fw = new FileWriter(configFile);
                fw.write(parser.toJson(new ConfigClass()));
                fw.flush();
                fw.close();
            }

            cc = parser.fromJson(new FileReader(configFile), ConfigClass.class);

            File icons = mainPath.resolve("icons/").toFile();

            if(!icons.exists())
                icons.mkdirs();
        }catch (Exception e){
            ok = false;
            throw e;
        }
    }

    public boolean titleIsAvailable(){
        if(!ok)
            return false;

        return !cc.windowTitle.equals("") && cc.changeTitle;
    }

    public String getNewTitle(){
        if(!ok)
            return "";

        return cc.windowTitle;
    }

    public boolean iconsAreAvailable(){
        if(!ok)
            return false;

        File f1 = mainPath.resolve("icons/" + cc.icon16x16).toFile();
        File f2 = mainPath.resolve("icons/" + cc.icon32x32).toFile();

        if(!(f1.exists() && f2.exists()) && cc.changeIcons){
            WindowTitleChanger.logger.error("Error! no icons found!");
        }

        return f1.exists() && f2.exists() && cc.changeIcons;
    }

    public InputStream get16Icon() throws FileNotFoundException {
        File f = mainPath.resolve("icons/" + cc.icon16x16).toFile();

        return new FileInputStream(f);
    }

    public InputStream get32Icon() throws FileNotFoundException {
        File f = mainPath.resolve("icons/" + cc.icon32x32).toFile();

        return new FileInputStream(f);
    }

    private static class ConfigClass{
        public Boolean changeTitle = true;

        public String windowTitle = "Minecraft";

        public Boolean changeIcons = false;

        public String icon16x16 = "";
        public String icon32x32 = "";
    }
}
