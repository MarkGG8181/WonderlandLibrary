/*
 * Decompiled with CFR 0_132 Helper by Lightcolour E-mail wyy-666@hotmail.com.
 */
package me.AveReborn.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.block.Block;

public class EventRenderBlock
implements Event {
    private int x;
    private int y;
    private int z;
    private Block block;

    public EventRenderBlock(int x2, int y2, int z2, Block block) {
        this.x = x2;
        this.y = y2;
        this.z = z2;
        this.block = block;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public Block getBlock() {
        return this.block;
    }
}

