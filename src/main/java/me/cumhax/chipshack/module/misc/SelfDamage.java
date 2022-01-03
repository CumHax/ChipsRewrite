package me.cumhax.chipshack.module.misc;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;

public class SelfDamage extends Module {

    private final Setting jump = new Setting("Jumps", this, 3, 3, 50);
    private final Setting timer = new Setting("JumpTimer", this, 3, 1, 1000);

    private int jumpCount;

    public SelfDamage() {
        super("SelfHurt", "SelfDamage", Category.MISC);
    }

    public void onEnable() {
        this.jumpCount = 0;
    }

    public void onDisable() {
        mc.timer.tickLength = 1;
    }

    public void update() {
        if(mc.player == null && mc.world == null) return;

        if(jumpCount < jump.getValDouble()) {
            mc.timer.tickLength = (float) timer.getValDouble();
            mc.player.onGround = false;
        }

        if(mc.player.onGround) {
            if(jumpCount < jump.getValDouble()) {
                mc.player.jump();
                jumpCount++;
            }


        }
    }
}
 
