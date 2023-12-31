// 
// Decompiled by Procyon v0.6.0
// 

package org.newdawn.slick.tests;

import org.newdawn.slick.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;

public class ImageMemTest extends BasicGame
{
    public ImageMemTest() {
        super("Image Memory Test");
    }
    
    public void init(final GameContainer container) throws SlickException {
        try {
            Image img = new Image(2400, 2400);
            img.getGraphics();
            img.destroy();
            img = new Image(2400, 2400);
            img.getGraphics();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void render(final GameContainer container, final Graphics g) {
    }
    
    public void update(final GameContainer container, final int delta) {
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new ImageMemTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (final SlickException e) {
            e.printStackTrace();
        }
    }
}
