// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

public interface ICustomTexture
{
    int getTextureId();
    
    int getTextureUnit();
    
    void deleteTexture();
    
    default int getTarget() {
        return 3553;
    }
}
