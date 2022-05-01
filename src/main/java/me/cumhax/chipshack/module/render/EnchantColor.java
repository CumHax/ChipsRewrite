package me.cumhax.chipshack.module.render;

import java.awt.Color;

import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.module.Category;
public class EnchantColor extends Module {

	
	private final Setting red = new Setting("Red", this, 255, 0, 255);
        private final Setting green = new Setting("Green", this, 20, 0, 255);
        private final Setting blue = new Setting("Blue", this, 20, 0, 255);
	private final Setting rainbow = new Setting("Rainbow", this, false);

	public EnchantColor() 
            {
        super("EnchantColor", "kekw", Category.RENDER);

	}

	public static Color getColor(final long offset, final float fade) {
		if (((EnchantColor) ModuleManager.getModule("EnchantColor")).rainbow.getBooleanValue() == false) {
			return new Color(((EnchantColor) ModuleManager.getModule("EnchantColor")).r.getIntegerValue() / 255,
					((EnchantColor) ModuleManager.getModule("EnchantColor")).g.getIntegerValue() / 255,
					((EnchantColor) ModuleManager.getModule("EnchantColor")).b.getIntegerValue() / 255);
		}
		final float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
		final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
		final Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade,
				c.getAlpha() / 255.0f);
	}
}
