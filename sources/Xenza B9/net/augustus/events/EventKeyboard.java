// 
// Decompiled by Procyon v0.6.0
// 

package net.augustus.events;

public class EventKeyboard extends Event
{
    private final int key;
    
    public EventKeyboard(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
}
