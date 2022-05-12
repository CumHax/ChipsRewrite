package me.cumhax.chipshack.module.combat;

import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.util.InventoryUtil;
import me.cumhax.chipshack.util.PlayerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class QuickEXP
extends Module {
    private final Setting mode = new Setting("Mode", this, Arrays.asList("Packet", "AutoMend", "Throw"));  
	private final Setting delay = new Setting("Throw Delay", this, 0, 0, 4, 0);
    	private final Setting footEXP = new Setting("FootEXP", this, true);


    public QuickEXP() {
        super("QuickEXP", "Throws EXP much faster", Category.COMBAT);
    }


    @Override
    public void onUpdate() {
        if (this.Null()) {
            return;
        }
        Item itemMainHand = QuickEXP.mc.player.getHeldItemMainhand().getItem();
        Item itemONotMainHand = QuickEXP.mc.player.getHeldItemOffhand().getItem();
        boolean expInMainHand = itemMainHand instanceof ItemExpBottle;
        boolean expNotInMainHand = itemONotMainHand instanceof ItemExpBottle;
        if (expInMainHand || expNotInMainHand) {
            QuickEXP.mc.rightClickDelayTimer = (int)delay.getValue();
        }
        if (QuickEXP.mc.player.isSneaking() && 0 < PlayerUtil.getArmorDurability() && (mode.getValue() == 0 || mode.getValue() == 1)) {
            switch (mode.getValue()) {
                case 1: {
                    QuickEXP.mc.player.inventory.currentItem = InventoryUtil.getHotbarItemSlot(Items.EXPERIENCE_BOTTLE);
                    break;
                }
                case 0: {
                    InventoryUtil.switchToSlotGhost(InventoryUtil.getHotbarItemSlot(Items.EXPERIENCE_BOTTLE));
                }
            }
            if (footEXP.getValue()) {
                QuickEXP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 90.0f, true));
            }
            mc.rightClickMouse();
        }
    }
}
