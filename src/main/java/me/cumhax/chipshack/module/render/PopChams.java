package me.cumhax.chipshack.module.render;

import java.util.HashMap;
import java.util.Map;
import me.cumhax.chipshack.event.PlayerDeathEvent;
import me.cumhax.chipshack.event.TotemPopEvent;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.util.font.RenderUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class PopChams
extends Module {
    public Setting timeToRemove = new Setting("TimeToRemove", this, 1500.0f, 0.0f, 3000.0f);
    public Setting self = new Setting("Self", this, true);
    public Setting renderMode =new Setting ("RenderMode", this, Arrays.asList("Wireframe", "Fill", "Both"));
    public Setting color = new Setting("Color", this, 0.0f, 125.0f, 255.0f, 45.0f);
    public Setting rainbow = new Setting("Rainbow", this, false);
    public Setting rainbowSpeed = new Setting("RainbowSpeed", this, 6.0f, 1.0f, 10.0f);
    public Setting lineWidth = new Setting("LineWidth", this, 2.0f, 1.0f, 4.0f);
    private static final HashMap<EntityOtherPlayerMP, Long> popFakePlayerMap = new HashMap();

    public PopChams() {
        super("PopChams", "Renders player pops.", Category.RENDER);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (this.nullCheck()) {
            return;
        }
        for (Map.Entry<EntityOtherPlayerMP, Long> entry : new HashMap<EntityOtherPlayerMP, Long>(popFakePlayerMap).entrySet()) {
            float r = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getRed() : this.color.getR()) / 255.0f;
            float g = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getGreen() : this.color.getG()) / 255.0f;
            float b = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getBlue() : this.color.getB()) / 255.0f;
            if (System.currentTimeMillis() - entry.getValue() > (long)this.timeToRemove.getValue()) {
                popFakePlayerMap.remove((Object)entry.getKey());
                continue;
            }
            GL11.glPushMatrix();
            GL11.glDepthRange((double)0.01, (double)1.0);
            if (this.renderMode.getValueEnum() == RenderMode.Wireframe || this.renderMode.getValueEnum() == RenderMode.Both) {
                GL11.glDisable((int)2896);
                GL11.glDisable((int)3553);
                GL11.glPolygonMode((int)1032, (int)6913);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glLineWidth((float)this.lineWidth.getValue());
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glColor4f((float)r, (float)g, (float)b, (float)1.0f);
                this.renderEntityStatic((Entity)entry.getKey(), event.getPartialTicks(), true);
                GL11.glHint((int)3154, (int)4352);
                GL11.glPolygonMode((int)1032, (int)6914);
                GL11.glEnable((int)2896);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3553);
            }
            if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
                GL11.glPushAttrib((int)-1);
                GL11.glEnable((int)3008);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glEnable((int)3042);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
                GL11.glLineWidth((float)1.5f);
                GL11.glColor4f((float)r, (float)g, (float)b, (float)(this.color.getA() / 255.0f));
                this.renderEntityStatic((Entity)entry.getKey(), event.getPartialTicks(), true);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2896);
                GL11.glDisable((int)3042);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glPopAttrib();
            }
            GL11.glDepthRange((double)0.0, (double)1.0);
            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void onPop(TotemPopEvent event) {
        Entity entity;
        if (PopChams.mc.world.getEntityByID(event.getEntity().getEntityId()) != null && (entity = PopChams.mc.world.getEntityByID(event.getEntity().getEntityId())) instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (entity == PopChams.mc.player && !this.self.getValue()) {
                return;
            }
            EntityOtherPlayerMP fakeEntity = new EntityOtherPlayerMP((World)PopChams.mc.world, player.getGameProfile());
            fakeEntity.copyLocationAndAnglesFrom((Entity)player);
            fakeEntity.rotationYawHead = player.rotationYawHead;
            fakeEntity.prevRotationYawHead = player.rotationYawHead;
            fakeEntity.rotationYaw = player.rotationYaw;
            fakeEntity.prevRotationYaw = player.rotationYaw;
            fakeEntity.rotationPitch = player.rotationPitch;
            fakeEntity.prevRotationPitch = player.rotationPitch;
            fakeEntity.cameraYaw = fakeEntity.rotationYaw;
            fakeEntity.cameraPitch = fakeEntity.rotationPitch;
            fakeEntity.setSneaking(player.isSneaking());
            popFakePlayerMap.put(fakeEntity, System.currentTimeMillis());
        }
    }

    @SubscribeEvent
    public void onDeath(PlayerDeathEvent event) {
        Entity entity;
        if (PopChams.mc.world.getEntityByID(event.getEntity().getEntityId()) != null && (entity = PopChams.mc.world.getEntityByID(event.getEntity().getEntityId())) instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (entity == PopChams.mc.player) {
                return;
            }
            EntityOtherPlayerMP fakeEntity = new EntityOtherPlayerMP((World)PopChams.mc.world, player.getGameProfile());
            fakeEntity.copyLocationAndAnglesFrom((Entity)player);
            fakeEntity.rotationYawHead = player.rotationYawHead;
            fakeEntity.prevRotationYawHead = player.rotationYawHead;
            fakeEntity.rotationYaw = player.rotationYaw;
            fakeEntity.prevRotationYaw = player.rotationYaw;
            fakeEntity.rotationPitch = player.rotationPitch;
            fakeEntity.prevRotationPitch = player.rotationPitch;
            fakeEntity.cameraYaw = fakeEntity.rotationYaw;
            fakeEntity.cameraPitch = fakeEntity.rotationPitch;
            fakeEntity.setSneaking(player.isSneaking());
            popFakePlayerMap.put(fakeEntity, System.currentTimeMillis());
        }
    }

    public void renderEntityStatic(Entity entityIn, float partialTicks, boolean p_188388_3_) {
        if (entityIn.ticksExisted == 0) {
            entityIn.lastTickPosX = entityIn.posX;
            entityIn.lastTickPosY = entityIn.posY;
            entityIn.lastTickPosZ = entityIn.posZ;
        }
        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)partialTicks;
        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)partialTicks;
        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)partialTicks;
        float f = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
        int i = entityIn.getBrightnessForRender();
        if (entityIn.isBurning()) {
            i = 0xF000F0;
        }
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords((int)OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        mc.getRenderManager().renderEntity(entityIn, d0 - PopChams.mc.getRenderManager().viewerPosX, d1 - PopChams.mc.getRenderManager().viewerPosY, d2 - PopChams.mc.getRenderManager().viewerPosZ, f, partialTicks, p_188388_3_);
    }
        public static enum RenderMode {
        Wireframe,
        Fill,
        Both;
        }
}
