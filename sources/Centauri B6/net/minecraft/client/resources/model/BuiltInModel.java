package net.minecraft.client.resources.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;

public class BuiltInModel implements IBakedModel {
   private ItemCameraTransforms cameraTransforms;

   public BuiltInModel(ItemCameraTransforms p_i46086_1_) {
      this.cameraTransforms = p_i46086_1_;
   }

   public boolean isGui3d() {
      return true;
   }

   public List getGeneralQuads() {
      return null;
   }

   public List getFaceQuads(EnumFacing p_177551_1_) {
      return null;
   }

   public boolean isBuiltInRenderer() {
      return true;
   }

   public ItemCameraTransforms getItemCameraTransforms() {
      return this.cameraTransforms;
   }

   public TextureAtlasSprite getParticleTexture() {
      return null;
   }

   public boolean isAmbientOcclusion() {
      return false;
   }
}
