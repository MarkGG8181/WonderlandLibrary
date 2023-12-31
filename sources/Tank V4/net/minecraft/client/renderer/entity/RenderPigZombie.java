package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;

public class RenderPigZombie extends RenderBiped {
   private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");

   protected ResourceLocation getEntityTexture(EntityLiving var1) {
      return this.getEntityTexture((EntityPigZombie)var1);
   }

   public RenderPigZombie(RenderManager var1) {
      super(var1, new ModelZombie(), 0.5F, 1.0F);
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerBipedArmor(this, this) {
         final RenderPigZombie this$0;

         {
            this.this$0 = var1;
         }

         protected void initArmor() {
            this.field_177189_c = new ModelZombie(0.5F, true);
            this.field_177186_d = new ModelZombie(1.0F, true);
         }
      });
   }

   protected ResourceLocation getEntityTexture(EntityPigZombie var1) {
      return ZOMBIE_PIGMAN_TEXTURE;
   }
}
