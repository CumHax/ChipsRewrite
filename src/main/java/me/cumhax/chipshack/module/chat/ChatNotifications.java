package me.cumhax.chipshack.module.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.cumhax.chipshack.event.DeathEvent;
import me.cumhax.chipshack.event.ModuleToggleEvent;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.setting.Setting
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatNotifications extends Module {
    public Setting modules = new Setting("Modules", this, false);
 //   public Setting totemPops = new Setting("Totem Pops", this, false);
    public Setting deaths = new Setting("Deaths", this, false);
	
		public ChatNotifications()
	{
		super("ChatNotify", "", Category.CHAT);
		}

    @SubscribeEvent
    public void onModuleEnableEvent(ModuleToggleEvent.Enable event){
        if(modules.getValue())
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString("[ChipsRewrite] " + ChatFormatting.BOLD + event.getModule().getName() + ChatFormatting.RESET + " has been " + ChatFormatting.GREEN + "Enabled" + ChatFormatting.RESET + "."), 1);
    }

    @SubscribeEvent
    public void onModuleDisableEvent(ModuleToggleEvent.Disable event){
        if(modules.getValue())
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString("[ChipsRewrite] " + ChatFormatting.BOLD + event.getModule().getName() + ChatFormatting.RESET + " has been " + ChatFormatting.RED + "Disabled" + ChatFormatting.RESET + "."), 1);
    }

    @SubscribeEvent
    public void onDeathEvent(DeathEvent event){
        if(deaths.getValue())
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString("[ChipsRewrite] " + ChatFormatting.BOLD + event.getPlayer().getName() + ChatFormatting.RESET + " has just died."), 1);

    }
}
