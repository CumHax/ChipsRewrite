package me.cumhax.chipshack.module.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.cumhax.chipshack.event.PlayerDeathEvent;
import me.cumhax.chipshack.event.TotemPopEvent;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.util.LoggerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PopCounter
extends Module {
    public PopCounter() {
        super("PopCounter", "Says in chat when someone pops.", Category.CHAT);
    }

    @SubscribeEvent
    public void onPop(TotemPopEvent event) {
        if (event.getPopCount() == 1) {
            LoggerUtil.sendMessage((Object)ChatFormatting.AQUA + event.getEntity().getDisplayName().getFormattedText() + (Object)ChatFormatting.GRAY + " has just popped a totem.", event.getEntity().getEntityId());
        } else if (event.getPopCount() > 1) {
            LoggerUtil.sendMessage((Object)ChatFormatting.AQUA + event.getEntity().getDisplayName().getFormattedText() + (Object)ChatFormatting.GRAY + " has just popped " + (Object)ChatFormatting.RED + event.getPopCount() + (Object)ChatFormatting.GRAY + " totems.", event.getEntity().getEntityId());
        }
    }

    @SubscribeEvent
    public void onDeath(PlayerDeathEvent event) {
        LoggerUtil.sendMessage((Object)ChatFormatting.AQUA + event.getEntity().getDisplayName().getFormattedText() + (Object)ChatFormatting.GRAY + " has just died what a retard.", event.getEntity().getEntityId());
    }
}
