// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.entity;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public interface IEntityMultiPart
{
    World getWorld();
    
    boolean attackEntityFromPart(final MultiPartEntityPart p0, final DamageSource p1, final float p2);
}
