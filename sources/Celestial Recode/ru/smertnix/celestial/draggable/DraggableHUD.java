package ru.smertnix.celestial.draggable;

import com.google.common.collect.Lists;

import ru.smertnix.celestial.draggable.component.DraggableComponent;
import ru.smertnix.celestial.draggable.component.impl.*;

import java.util.List;

public class DraggableHUD {

    private DraggableScreen screen;
    private final List<DraggableComponent> components;

    public DraggableHUD() {
        screen = new DraggableScreen();
        components = Lists.newArrayList();
        components.add(new DraggableTargetHUD());
        components.add(new DraggableWaterMark());
        components.add(new DraggablePotionHUD());
        components.add(new DraggableTimer());
        components.add(new DraggableArray());
    }

    public DraggableScreen getScreen() {
        return screen;
    }

    public List<DraggableComponent> getComponents() {
        return components;
    }

    public DraggableComponent getDraggableComponentByClass(Class<? extends DraggableComponent> classs) {
        for (DraggableComponent draggableComponent : components) {
            if (draggableComponent.getClass() == classs) {
                return draggableComponent;
            }
        }

        return null;
    }

}