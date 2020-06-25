package net.szum123321.window_title_changer;

import blue.endless.jankson.Comment;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;

@SuppressWarnings("CanBeFinal")
@ConfigFile(name = "./WindowTitleChanger/config")
public class ConfigHandler {
	@Comment("\nShould window title be changed?\n")
	public Boolean changeTitle = true;

	@Comment("\nThis is what window title will say.\n" +
			"You can add '{version}' to display your minecraft version.\n")
	public String windowTitle = "Minecraft";

	@Comment("\nShould window icon be changed?\n" +
			"REMEMBER! You have to provide both 16x16 and 32x32 icon in .png format!\n")
	public Boolean changeIcons = false;

	public String icon16x16 = "";
	public String icon32x32 = "";
}
