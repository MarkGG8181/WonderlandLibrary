// 
// Decompiled by Procyon v0.6.0
// 

package org.newdawn.slick;

import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;
import org.newdawn.slick.opengl.ImageData;

public class ImageBuffer implements ImageData
{
    private int width;
    private int height;
    private int texWidth;
    private int texHeight;
    private byte[] rawData;
    
    public ImageBuffer(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.texWidth = this.get2Fold(width);
        this.texHeight = this.get2Fold(height);
        this.rawData = new byte[this.texWidth * this.texHeight * 4];
    }
    
    public byte[] getRGBA() {
        return this.rawData;
    }
    
    public int getDepth() {
        return 32;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getTexHeight() {
        return this.texHeight;
    }
    
    public int getTexWidth() {
        return this.texWidth;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public ByteBuffer getImageBufferData() {
        final ByteBuffer scratch = BufferUtils.createByteBuffer(this.rawData.length);
        scratch.put(this.rawData);
        scratch.flip();
        return scratch;
    }
    
    public void setRGBA(final int x, final int y, final int r, final int g, final int b, final int a) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            throw new RuntimeException("Specified location: " + x + "," + y + " outside of image");
        }
        final int ofs = (x + y * this.texWidth) * 4;
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            this.rawData[ofs] = (byte)r;
            this.rawData[ofs + 1] = (byte)g;
            this.rawData[ofs + 2] = (byte)b;
            this.rawData[ofs + 3] = (byte)a;
        }
        else {
            this.rawData[ofs] = (byte)r;
            this.rawData[ofs + 1] = (byte)g;
            this.rawData[ofs + 2] = (byte)b;
            this.rawData[ofs + 3] = (byte)a;
        }
    }
    
    public Image getImage() {
        return new Image(this);
    }
    
    public Image getImage(final int filter) {
        return new Image(this, filter);
    }
    
    private int get2Fold(final int fold) {
        int ret;
        for (ret = 2; ret < fold; ret *= 2) {}
        return ret;
    }
}
