package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityGiantZombie extends EntityMob {
   public EntityGiantZombie(World worldIn) {
      super(worldIn);
      this.setSize(this.width * 6.0F, this.height * 6.0F);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0D);
   }

   public float getEyeHeight() {
      return 10.440001F;
   }

   public float getBlockPathWeight(BlockPos pos) {
      return this.worldObj.getLightBrightness(pos) - 0.5F;
   }
}
