package ru.smertnix.celestial.ui.clickgui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import ru.smertnix.celestial.utils.render.RenderUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class Component  {

    public final Component parent;
    protected final List<Component> components = new ArrayList<>();
    private final String name;
    private int x;
    private int y;
    private int width;
    private int height;
    public float a = 10;
    public float anim = 10;
    public Component(Component parent, String name, int x, int y, int width, int height) {
        this.parent = parent;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Component getParent() {
        return parent;
    }

    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        for (Component child : components) {
            child.drawComponent(scaledResolution, mouseX, mouseY);
        }
    }

    public void onMouseClick(int mouseX, int mouseY, int button) {
        for (Component child : components) {
            child.onMouseClick(mouseX, mouseY, button);
        }
    }

    public void onMouseRelease(int button) {
        for (Component child : components) {
            child.onMouseRelease(button);
        }
    }

    public void onKeyPress(int keyCode) {
            for (Component child : components) {
                child.onKeyPress(keyCode);
            }
    }

    public String getName() {
        return name;
    }

    public int getX() {
        Component familyMember = parent;
        int familyTreeX = x;

        while (familyMember != null) {
            familyTreeX += familyMember.x;
            familyMember = familyMember.parent;
        }

        return familyTreeX;
    }

    public void setX(int x) {
        this.x = x;
    }

    protected boolean isHovered(double mouseX, double mouseY) {
        double x;
        double y;
        return (mouseX >= (x = getX()) && mouseY >= (y = getY()) && mouseX < x + getWidth() && mouseY < y + getHeight());
    }
    public int getY() {
        Component familyMember = parent;
        int familyTreeY = y;

        while (familyMember != null) {
            familyTreeY += familyMember.y;
            familyMember = familyMember.parent;
        }

        return familyTreeY;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width - 10;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return (int) (height + 4);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Component> getComponents() {
        return components;
    }
}
