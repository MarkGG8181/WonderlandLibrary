// 
// Decompiled by Procyon v0.6.0
// 

package net.augustus.events;

public class EventSilent extends Event
{
    private int slotID;
    
    public EventSilent(final int slotID) {
        this.slotID = slotID;
    }
    
    public int getSlotID() {
        return this.slotID;
    }
    
    public void setSlotID(final int slotID) {
        this.slotID = slotID;
    }
}
