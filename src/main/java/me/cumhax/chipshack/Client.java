package me.cumhax.chipshack;

import me.cumhax.chipshack.event.EventHandler;
import me.cumhax.chipshack.command.CommandManager;
import me.cumhax.chipshack.gui.clickgui.ClickGUI;
import me.cumhax.chipshack.gui.clickgui2.ClickGUI2;
import me.cumhax.chipshack.gui.clickgui2.font.CustomFontRenderer2;
import me.cumhax.chipshack.manager.FriendManager;
import me.cumhax.chipshack.module.ModuleManager;
import me.cumhax.chipshack.setting.SettingManager;
import me.cumhax.chipshack.util.font.CustomFontRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import me.cumhax.chipshack.manager.ConfigManager;

import java.awt.*;
import java.io.IOException;

@Mod(modid = "chipshack", name = "ChipsHack", version = "0.3")
public class Client
{
	public static ModuleManager moduleManager;
	public static SettingManager settingManager;
	public static CustomFontRenderer customFontRenderer;
	public static ClickGUI clickGUI;
	public static CustomFontRenderer2 customFontRenderer2;
	public static ClickGUI2 clickGUI2;
	public static CommandManager commandManager;
	public static FriendManager friendManager;

	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event) throws IOException {
		commandManager = new CommandManager();
		settingManager = new SettingManager();
		moduleManager = new ModuleManager();
		friendManager = new FriendManager();
		customFontRenderer2 = new CustomFontRenderer2(new Font("Arial", Font.PLAIN, 19), true, false);
		customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 19), true, false);
		clickGUI = new ClickGUI();
		clickGUI2 = new ClickGUI2();

		ConfigManager.loadConfig();

		Runtime.getRuntime().addShutdownHook(new ConfigManager());
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	public static ModuleManager getModuleManager()
	{
		return moduleManager;
	}
	public static FriendManager getFriendManager()
	{
		return friendManager;
	}
}
