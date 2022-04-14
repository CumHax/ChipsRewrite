package me.cumhax.chipshack.module.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.cumhax.chipshack.event.ModuleToggleEvent;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.util.LoggerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatNotify extends Module {
    public ChatNotify() {
        super("ChatNotify", "Sends a message when you toggle a module.", Category.CHAT);
    }

    @SubscribeEvent
    public void onModuleEnable(ModuleToggleEvent.Pre event) {
        LoggerUtil.sendMessage((Object)ChatFormatting.BOLD + event.getModule().getName() + (Object)ChatFormatting.GREEN + " ENABLED!");
    }

    @SubscribeEvent
    public void onModuleDisable(ModuleToggleEvent.Post event) {
        LoggerUtil.sendMessage((Object)ChatFormatting.BOLD + event.getModule().getName() + (Object)ChatFormatting.RED + " DISABLED!");
    }
}
