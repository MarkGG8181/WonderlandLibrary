// 
// Decompiled by Procyon v0.6.0
// 

package org.newdawn.slick.tests;

import org.newdawn.slick.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.Graphics;
import java.io.IOException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.BasicGame;

public class PedigreeTest extends BasicGame
{
    private Image image;
    private GameContainer container;
    private ParticleSystem trail;
    private ParticleSystem fire;
    private float rx;
    private float ry;
    
    public PedigreeTest() {
        super("Pedigree Test");
        this.ry = 900.0f;
    }
    
    public void init(final GameContainer container) throws SlickException {
        this.container = container;
        try {
            this.fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
            this.trail = ParticleIO.loadConfiguredSystem("testdata/smoketrail.xml");
        }
        catch (final IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
        this.image = new Image("testdata/rocket.png");
        this.spawnRocket();
    }
    
    private void spawnRocket() {
        this.ry = 700.0f;
        this.rx = (float)(Math.random() * 600.0 + 100.0);
    }
    
    public void render(final GameContainer container, final Graphics g) {
        ((ConfigurableEmitter)this.trail.getEmitter(0)).setPosition(this.rx + 14.0f, this.ry + 35.0f);
        this.trail.render();
        this.image.draw((float)(int)this.rx, (float)(int)this.ry);
        g.translate(400.0f, 300.0f);
        this.fire.render();
    }
    
    public void update(final GameContainer container, final int delta) {
        this.fire.update(delta);
        this.trail.update(delta);
        this.ry -= delta * 0.25f;
        if (this.ry < -100.0f) {
            this.spawnRocket();
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new PedigreeTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (final SlickException e) {
            e.printStackTrace();
        }
    }
    
    public void keyPressed(final int key, final char c) {
        if (key == 1) {
            this.container.exit();
        }
    }
}
