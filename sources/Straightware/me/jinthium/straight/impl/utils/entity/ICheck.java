package me.jinthium.straight.impl.utils.entity;

import net.minecraft.entity.Entity;
@FunctionalInterface
public interface ICheck {
    boolean validate(Entity entity);
}