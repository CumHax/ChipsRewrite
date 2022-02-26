package me.cumhax.chipshack.module.misc;

import me.cumhax.chipshack.mixin.mixins.accessor.IMinecraft;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class FastUse extends Module {

	Setting all = new Setting("All", this, false);
	Setting bow = new Setting("Bow", this, true);
	Setting exp = new Setting("Exp", this, true);
	Setting endCrystals = new Setting("End Crystals", this, true);
	Setting fireworks = new Setting("Fireworks", this, true);
	
	public FastUse() 
        {
        super("FastUse", "", Category.MISC);
	}

	@SubscribeEvent
	public void onTick(final TickEvent.ClientTickEvent event) {
		if(mc.world == null) return;
		if(all.getBooleanValue()) {
			((IMinecraft) mc).setRightClickDelayTimer(0);
		}
		if(exp.getBooleanValue() && mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || exp.getBooleanValue() && mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
			((IMinecraft) mc.setRightClickDelayTimer(0);
		}
		if(endCrystals.getBooleanValue() && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || endCrystals.getBooleanValue() && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
			((IMinecraft) mc).setRightClickDelayTimer(0);
		}
		if(fireworks.getBooleanValue() && mc.player.getHeldItemMainhand().getItem() == Items.FIREWORKS || fireworks.getBooleanValue() && mc.player.getHeldItemOffhand().getItem() == Items.FIREWORKS) {
			((IMinecraft) mc).setRightClickDelayTimer(0);
		}
	}
}
