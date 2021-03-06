package me.cumhax.chipshack.module.misc;

import java.util.ArrayList;
import me.cumhax.chipshack.event.PacketEvent;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class PacketCanceller
extends Module {
    private final Setting CancelCPacketInput = new Setting("Input",this, false);
    private final Setting CancelPosition = new Setting("Position", this, false);
    private final Setting CancelPositionRotation = new Setting("PositionRotation", this, false);
    private final Setting CancelRotation = new Setting("Rotation", this, false);
    private final Setting CancelCPacketPlayerAbilities = new Setting("PlayerAbilities", this, false);
    private final Setting CancelCPacketPlayerDigging = new Setting("PlayerDigging", this, false);
    private final Setting CancelCPacketPlayerTryUseItem = new Setting("PlayerTryUseItem", this, false);
    private final Setting CancelCPacketPlayerTryUseItemOnBlock = new Setting("PlayerTryUseItemOnBlock", this, false);
    private final Setting CancelCPacketEntityAction = new Setting("EntityAction", this, false);
    private final Setting CancelCPacketUseEntity = new Setting("UseEntity", this, false);
    private final Setting CancelCPacketVehicleMove = new Setting("VehicleMove", this, false);
    private int PacketsCanelled = 0;
    private ArrayList<Packet> PacketsToIgnore = new ArrayList();

    public PacketCanceller() {
        super("PacketCanceller", "", Category.MISC);

    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.PacketsCanelled = 0;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketInput && this.CancelCPacketInput.getBooleanValue() || event.getPacket() instanceof CPacketPlayer.Position && this.CancelPosition.getBooleanValue() || event.getPacket() instanceof CPacketPlayer.PositionRotation && this.CancelPositionRotation.getBooleanValue() || event.getPacket() instanceof CPacketPlayer.Rotation && this.CancelRotation.getBooleanValue() || event.getPacket() instanceof CPacketPlayerAbilities && this.CancelCPacketPlayerAbilities.getBooleanValue() || event.getPacket() instanceof CPacketPlayerDigging && this.CancelCPacketPlayerDigging.getBooleanValue() || event.getPacket() instanceof CPacketPlayerTryUseItem && this.CancelCPacketPlayerTryUseItem.getBooleanValue() || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && this.CancelCPacketPlayerTryUseItemOnBlock.getBooleanValue() || event.getPacket() instanceof CPacketEntityAction && this.CancelCPacketEntityAction.getBooleanValue() || event.getPacket() instanceof CPacketUseEntity && this.CancelCPacketUseEntity.getBooleanValue() || event.getPacket() instanceof CPacketVehicleMove && this.CancelCPacketVehicleMove.getBooleanValue()) {
            if (this.PacketsToIgnore.contains(event.getPacket())) {
                this.PacketsToIgnore.remove(event.getPacket());
                return;
            }
            ++this.PacketsCanelled;
            event.setCanceled(true);
            return;
        }
    }

    public void AddIgnorePacket(Packet p_Packet) {
        this.PacketsToIgnore.add(p_Packet);
    }
}
