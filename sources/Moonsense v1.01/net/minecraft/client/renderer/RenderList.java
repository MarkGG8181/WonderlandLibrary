// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.renderer;

import java.util.Iterator;
import optifine.Config;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;

public class RenderList extends ChunkRenderContainer
{
    @Override
    public void func_178001_a(final EnumWorldBlockLayer p_178001_1_) {
        if (this.field_178007_b) {
            if (this.field_178009_a.size() == 0) {
                return;
            }
            for (final RenderChunk var3 : this.field_178009_a) {
                final ListedRenderChunk var4 = (ListedRenderChunk)var3;
                GlStateManager.pushMatrix();
                this.func_178003_a(var3);
                GL11.glCallList(var4.func_178600_a(p_178001_1_, var4.func_178571_g()));
                GlStateManager.popMatrix();
            }
            if (Config.isMultiTexture()) {
                GlStateManager.bindCurrentTexture();
            }
            GlStateManager.func_179117_G();
            this.field_178009_a.clear();
        }
    }
}
