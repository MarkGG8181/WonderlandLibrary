// 
// Decompiled by Procyon v0.5.36
// 

package Lavish.utils.animations.impl;

import Lavish.utils.animations.Direction;
import Lavish.utils.animations.Animation;

public class SmoothStepAnimation extends Animation
{
    public SmoothStepAnimation(final int ms, final double endPoint) {
        super(ms, endPoint);
    }
    
    public SmoothStepAnimation(final int ms, final double endPoint, final Enum<Direction> direction) {
        super(ms, endPoint, direction);
    }
    
    @Override
    protected double getEquation(final double x) {
        final double x2 = x / this.duration;
        return -2.0 * Math.pow(x2, 3.0) + 3.0 * Math.pow(x2, 2.0);
    }
}
