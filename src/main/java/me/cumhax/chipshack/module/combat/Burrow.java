package me.cumhax.chipshack.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import java.util.Iterator;
import net.minecraft.block.Block;
import cat.yoink.dream.mixin.mixins.accessor.IMinecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import me.cumhax.chipshack.util.InteractUtil;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.client.Minecraft;
import me.cumhax.chipshack.setting.SettingType;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.util.TimerUtil;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.module.Module;

public class Burrow extends Module
{
    private final Setting rotate = new Setting("Rotate", this, true);
    private final Setting delay = new Setting("PLace Delay", this, 3, 0, 20);
    int lastHotbarSlot;
    int playerHotbarSlot;
    boolean isSneaking;
    TimerUtil timer;
    
	public Burrow()
	{
		super("Burrow", "", Category.COMBAT);
        this.timer = new TimerUtil();
    }
    
    @Override
    public void onEnable() {
        if (this.mc.player == null) {
            this.disable();
        }
        else {
            this.playerHotbarSlot = this.mc.player.inventory.currentItem;
            this.lastHotbarSlot = -1;
            this.mc.player.jump();
            this.timer.reset();
        }
    }
    
    @Override
    public void onDisable() {
        if (this.mc.player != null) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                this.mc.player.inventory.currentItem = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
            this.playerHotbarSlot = -1;
            this.lastHotbarSlot = -1;
        }
    }
    
    private boolean placeBlock(final BlockPos var1) {
        final Block var2 = this.mc.world.getBlockState(var1).getBlock();
        if (!(var2 instanceof BlockAir) && !(var2 instanceof BlockLiquid)) {
            return false;
        }
        for (final Entity var4 : this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(var1))) {
            if (!(var4 instanceof EntityItem) && !(var4 instanceof EntityXPOrb)) {
                return false;
            }
        }
        final EnumFacing var5 = InteractUtil.getPlaceableSide(var1);
        if (var5 == null) {
            return false;
        }
        final BlockPos var6 = var1.offset(var5);
        final EnumFacing var7 = var5.getOpposite();
        if (!InteractUtil.canBeClicked(var6)) {
            return false;
        }
        final Vec3d var8 = new Vec3d((Vec3i)var6).add(0.5, 0.5, 0.5).add(new Vec3d(var7.getDirectionVec()).scale(0.5));
        final Block var9 = this.mc.world.getBlockState(var6).getBlock();
        final int var10 = this.findObiInHotbar();
        if (var10 == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != var10) {
            this.mc.player.inventory.currentItem = var10;
            this.lastHotbarSlot = var10;
        }
        if ((!this.isSneaking && InteractUtil.blackList.contains(var9)) || InteractUtil.shulkerList.contains(var9)) {
            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if ((boolean)this.rotate.getBooleanValue()) {
            InteractUtil.faceVectorPacketInstant(var8);
        }
        this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, var6, var7, var8, EnumHand.MAIN_HAND);
        this.mc.player.swingArm(EnumHand.MAIN_HAND);
        ((IMinecraft)this.mc).setRightClickDelayTimer(4);
        return true;
    }
    
    public int findObiInHotbar() {
        int var1 = -1;
        for (int var2 = 0; var2 < 9; ++var2) {
            final ItemStack var3 = this.mc.player.inventory.getStackInSlot(var2);
            if (var3 != ItemStack.EMPTY && var3.getItem() instanceof ItemBlock) {
                final Block var4 = ((ItemBlock)var3.getItem()).getBlock();
                if (var4 instanceof BlockObsidian) {
                    var1 = var2;
                    break;
                }
            }
        }
        return var1;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.isSingleplayer()) {
            final String text = "[" + ChatFormatting.GREEN + "moneymod+2" + ChatFormatting.RESET + "]" + ChatFormatting.RED + " DOESNT WORK IN SINGLEPLAYER!";
            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(text), 5936);
            this.disable();
            return;
        }
        if (this.timer.hasReached(this.delay.getIntegerValue() * 50)) {
            final BlockPos var1 = new BlockPos(0, -1, 0);
            final BlockPos var2 = new BlockPos(this.mc.player.getPositionVector()).add(var1.getX(), var1.getY(), var1.getZ());
            if (this.placeBlock(var2)) {
                if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                    this.mc.player.inventory.currentItem = this.playerHotbarSlot;
                    this.lastHotbarSlot = this.playerHotbarSlot;
                }
                if (this.isSneaking) {
                    this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    this.isSneaking = false;
                }
                this.mc.player.onGround = false;
                this.mc.player.motionY = 20.0;
            }
            this.disable();
        }
    }
}
