package net.minecraft.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;

public class WorldProviderEnd extends WorldProvider {
   public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
      int i = 10518688;
      float f = MathHelper.cos(p_76562_1_ * 3.1415927F * 2.0F) * 2.0F + 0.5F;
      f = MathHelper.clamp_float(f, 0.0F, 1.0F);
      float f1 = (float)(i >> 16 & 255) / 255.0F;
      float f2 = (float)(i >> 8 & 255) / 255.0F;
      float f3 = (float)(i & 255) / 255.0F;
      f1 = f1 * (f * 0.0F + 0.15F);
      f2 = f2 * (f * 0.0F + 0.15F);
      f3 = f3 * (f * 0.0F + 0.15F);
      return new Vec3((double)f1, (double)f2, (double)f3);
   }

   public boolean canRespawnHere() {
      return false;
   }

   public IChunkProvider createChunkGenerator() {
      return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
   }

   public int getAverageGroundLevel() {
      return 50;
   }

   public boolean canCoordinateBeSpawn(int x, int z) {
      return this.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().blocksMovement();
   }

   public BlockPos getSpawnCoordinate() {
      return new BlockPos(100, 50, 0);
   }

   public boolean isSurfaceWorld() {
      return false;
   }

   public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_) {
      return 0.0F;
   }

   public String getDimensionName() {
      return "The End";
   }

   public void registerWorldChunkManager() {
      this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
      this.dimensionId = 1;
      this.hasNoSky = true;
   }

   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
      return null;
   }

   public String getInternalNameSuffix() {
      return "_end";
   }

   public boolean isSkyColored() {
      return false;
   }

   public float getCloudHeight() {
      return 8.0F;
   }

   public boolean doesXZShowFog(int x, int z) {
      return true;
   }
}
