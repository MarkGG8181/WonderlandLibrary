// 
// Decompiled by Procyon v0.6.0
// 

package net.augustus.events;

public class EventLadder extends Event
{
    private double motionYSpeed;
    
    public EventLadder(final double motionYSpeed) {
        this.motionYSpeed = motionYSpeed;
    }
    
    public double getMotionYSpeed() {
        return this.motionYSpeed;
    }
    
    public void setMotionYSpeed(final double motionYSpeed) {
        this.motionYSpeed = motionYSpeed;
    }
}
