package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob {
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (!this.worldObj.isRemote) {
         int var1 = MathHelper.floor_double(this.posX);
         int var2 = MathHelper.floor_double(this.posY);
         int var3 = MathHelper.floor_double(this.posZ);
         if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
         }

         if (this.worldObj.getBiomeGenForCoords(new BlockPos(var1, 0, var3)).getFloatTemperature(new BlockPos(var1, var2, var3)) > 1.0F) {
            this.attackEntityFrom(DamageSource.onFire, 1.0F);
         }

         for(int var4 = 0; var4 < 4; ++var4) {
            var1 = MathHelper.floor_double(this.posX + (double)((float)(var4 % 2 * 2 - 1) * 0.25F));
            var2 = MathHelper.floor_double(this.posY);
            var3 = MathHelper.floor_double(this.posZ + (double)((float)(var4 / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos var5 = new BlockPos(var1, var2, var3);
            if (this.worldObj.getBlockState(var5).getBlock().getMaterial() == Material.air && this.worldObj.getBiomeGenForCoords(new BlockPos(var1, 0, var3)).getFloatTemperature(var5) < 0.8F && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, var5)) {
               this.worldObj.setBlockState(var5, Blocks.snow_layer.getDefaultState());
            }
         }
      }

   }

   public EntitySnowman(World var1) {
      super(var1);
      this.setSize(0.7F, 1.9F);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
      this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(4, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.mobSelector));
   }

   protected Item getDropItem() {
      return Items.snowball;
   }

   public void attackEntityWithRangedAttack(EntityLivingBase var1, float var2) {
      EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
      double var4 = var1.posY + (double)var1.getEyeHeight() - 1.100000023841858D;
      double var6 = var1.posX - this.posX;
      double var8 = var4 - var3.posY;
      double var10 = var1.posZ - this.posZ;
      float var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10) * 0.2F;
      var3.setThrowableHeading(var6, var8 + (double)var12, var10, 1.6F, 12.0F);
      this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      this.worldObj.spawnEntityInWorld(var3);
   }

   public float getEyeHeight() {
      return 1.7F;
   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(16);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Items.snowball, 1);
      }

   }
}
