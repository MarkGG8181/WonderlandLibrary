package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityTNTPrimed extends Entity {
   public int fuse;
   private EntityLivingBase tntPlacedBy;

   public EntityTNTPrimed(World worldIn) {
      super(worldIn);
      this.preventEntitySpawning = true;
      this.setSize(0.98F, 0.98F);
   }

   public EntityTNTPrimed(World worldIn, double p_i1730_2_, double p_i1730_4_, double p_i1730_6_, EntityLivingBase p_i1730_8_) {
      this(worldIn);
      this.setPosition(p_i1730_2_, p_i1730_4_, p_i1730_6_);
      float f = (float)(Math.random() * 3.141592653589793D * 2.0D);
      this.motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
      this.motionY = 0.20000000298023224D;
      this.motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
      this.fuse = 80;
      this.prevPosX = p_i1730_2_;
      this.prevPosY = p_i1730_4_;
      this.prevPosZ = p_i1730_6_;
      this.tntPlacedBy = p_i1730_8_;
   }

   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
      this.fuse = tagCompund.getByte("Fuse");
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public float getEyeHeight() {
      return 0.0F;
   }

   protected void entityInit() {
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY -= 0.03999999910593033D;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.9800000190734863D;
      this.motionY *= 0.9800000190734863D;
      this.motionZ *= 0.9800000190734863D;
      if(this.onGround) {
         this.motionX *= 0.699999988079071D;
         this.motionZ *= 0.699999988079071D;
         this.motionY *= -0.5D;
      }

      if(this.fuse-- <= 0) {
         this.setDead();
         if(!this.worldObj.isRemote) {
            this.explode();
         }
      } else {
         this.handleWaterMovement();
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
      }

   }

   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
      tagCompound.setByte("Fuse", (byte)this.fuse);
   }

   private void explode() {
      float f = 4.0F;
      this.worldObj.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, f, true);
   }

   public EntityLivingBase getTntPlacedBy() {
      return this.tntPlacedBy;
   }
}
