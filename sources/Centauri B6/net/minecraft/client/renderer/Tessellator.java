package net.minecraft.client.renderer;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;

public class Tessellator {
   private WorldRenderer worldRenderer;
   private WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
   private static final Tessellator instance = new Tessellator(2097152);

   public Tessellator(int bufferSize) {
      this.worldRenderer = new WorldRenderer(bufferSize);
   }

   public static Tessellator getInstance() {
      return instance;
   }

   public void draw() {
      this.worldRenderer.finishDrawing();
      this.vboUploader.func_181679_a(this.worldRenderer);
   }

   public WorldRenderer getWorldRenderer() {
      return this.worldRenderer;
   }
}
