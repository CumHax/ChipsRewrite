package me.cumhax.chipshack.event;

import me.cumhax.chipshack.module.Module;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ModuleToggleEvent
extends Event {
    public Module module;

    public ModuleToggleEvent(Module module) {
        this.module = module;
    }

    public static class Post
    extends ModuleToggleEvent {
        public Post(Module module) {
            super(module);
        }

        public Module getModule() {
            return this.module;
        }
    }

    public static class Pre
    extends ModuleToggleEvent {
        public Pre(Module module) {
            super(module);
        }

        public Module getModule() {
            return this.module;
        }
    }
}
