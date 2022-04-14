package me.cumhax.chipshack.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerDeathEvent
extends Event {
    public Entity entity;

    public PlayerDeathEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
