package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;

public class RenderLeashKnot extends Render {
   private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
   private ModelLeashKnot leashKnotModel = new ModelLeashKnot();

   public RenderLeashKnot(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   protected ResourceLocation getEntityTexture(EntityLeashKnot entity) {
      return leashKnotTextures;
   }

   public void doRender(EntityLeashKnot entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      GlStateManager.translate((float)x, (float)y, (float)z);
      float f = 0.0625F;
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      GlStateManager.enableAlpha();
      this.bindEntityTexture(entity);
      this.leashKnotModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f);
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }
}
