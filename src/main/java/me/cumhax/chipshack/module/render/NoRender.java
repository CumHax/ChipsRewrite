package me.cumhax.chipshack.module.render;

import me.cumhax.chipshack.event.EventTarget;
import me.cumhax.chipshack.event.EventReceivePacket;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoRender extends Module {

    private final Setting mob = new Setting("Mob", this, false);
    private final Setting gentity = new Setting("GEntity", this, false);
    public final Setting armor = new Setting("Armor", this, false);
    public final Setting armorTrans = new Setting("Armor Transparency", this, false);
    public final Setting alpha = new Setting("Transparency", this, 255, 0, 255);
    private final Setting object = new Setting("Object", this, false);
    private final Setting xp = new Setting("XP", this, false);
    private final Setting paint = new Setting("Paintings", this, false);
    private final Setting fire = new Setting("Fire", this, true);

    public NoRender() {
        super("NoRender", "Prevents rendering of certain things",  Category.RENDER);
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        Packet packet = event.getPacket();
        if ((packet instanceof SPacketSpawnMob && mob.getValue()) ||
                (packet instanceof SPacketSpawnGlobalEntity && gentity.getValue()) ||
                (packet instanceof SPacketSpawnObject && object.getValue()) ||
                (packet instanceof SPacketSpawnExperienceOrb && xp.getValue()) ||
                (packet instanceof SPacketSpawnPainting && paint.getValue()))
            event.setCancelled(true);
    }

    @SubscribeEvent
    public void onBlockOverlay(RenderBlockOverlayEvent event) {
        if (fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) event.setCanceled(true);
    }
}
