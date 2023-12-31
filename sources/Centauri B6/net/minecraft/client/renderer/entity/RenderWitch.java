package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItemWitch;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.ResourceLocation;

public class RenderWitch extends RenderLiving {
   private static final ResourceLocation witchTextures = new ResourceLocation("textures/entity/witch.png");

   public RenderWitch(RenderManager renderManagerIn) {
      super(renderManagerIn, new ModelWitch(0.0F), 0.5F);
      this.addLayer(new LayerHeldItemWitch(this));
   }

   public void transformHeldFull3DItemLayer() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }

   protected void preRenderCallback(EntityWitch entitylivingbaseIn, float partialTickTime) {
      float f = 0.9375F;
      GlStateManager.scale(f, f, f);
   }

   protected ResourceLocation getEntityTexture(EntityWitch entity) {
      return witchTextures;
   }

   public void doRender(EntityWitch entity, double x, double y, double z, float entityYaw, float partialTicks) {
      ((ModelWitch)this.mainModel).field_82900_g = entity.getHeldItem() != null;
      super.doRender((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
   }
}
