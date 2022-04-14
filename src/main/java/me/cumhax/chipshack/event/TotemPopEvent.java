package me.cumhax.chipshack.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TotemPopEvent
extends Event {
    public Entity entity;
    public int popCount;

    public TotemPopEvent(Entity entity, int popCount) {
        this.entity = entity;
        this.popCount = popCount;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public int getPopCount() {
        return this.popCount;
    }
}
