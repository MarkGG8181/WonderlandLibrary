package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMesa extends BiomeGenBase {
   private NoiseGeneratorPerlin field_150623_aE;
   private IBlockState[] field_150621_aC;
   private NoiseGeneratorPerlin field_150625_aG;
   private boolean field_150620_aI;
   private long field_150622_aD;
   private NoiseGeneratorPerlin field_150624_aF;
   private boolean field_150626_aH;

   public void genTerrainBlocks(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      if (this.field_150621_aC == null || this.field_150622_aD != var1.getSeed()) {
         this.func_150619_a(var1.getSeed());
      }

      if (this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != var1.getSeed()) {
         Random var8 = new Random(this.field_150622_aD);
         this.field_150623_aE = new NoiseGeneratorPerlin(var8, 4);
         this.field_150624_aF = new NoiseGeneratorPerlin(var8, 1);
      }

      this.field_150622_aD = var1.getSeed();
      double var22 = 0.0D;
      int var10;
      int var11;
      if (this.field_150626_aH) {
         var10 = (var4 & -16) + (var5 & 15);
         var11 = (var5 & -16) + (var4 & 15);
         double var12 = Math.min(Math.abs(var6), this.field_150623_aE.func_151601_a((double)var10 * 0.25D, (double)var11 * 0.25D));
         if (var12 > 0.0D) {
            double var14 = 0.001953125D;
            double var16 = Math.abs(this.field_150624_aF.func_151601_a((double)var10 * var14, (double)var11 * var14));
            var22 = var12 * var12 * 2.5D;
            double var18 = Math.ceil(var16 * 50.0D) + 14.0D;
            if (var22 > var18) {
               var22 = var18;
            }

            var22 += 64.0D;
         }
      }

      var10 = var4 & 15;
      var11 = var5 & 15;
      int var23 = var1.func_181545_F();
      IBlockState var13 = Blocks.stained_hardened_clay.getDefaultState();
      IBlockState var24 = this.fillerBlock;
      int var15 = (int)(var6 / 3.0D + 3.0D + var2.nextDouble() * 0.25D);
      boolean var25 = Math.cos(var6 / 3.0D * 3.141592653589793D) > 0.0D;
      int var17 = -1;
      boolean var26 = false;

      for(int var19 = 255; var19 >= 0; --var19) {
         if (var3.getBlockState(var11, var19, var10).getBlock().getMaterial() == Material.air && var19 < (int)var22) {
            var3.setBlockState(var11, var19, var10, Blocks.stone.getDefaultState());
         }

         if (var19 <= var2.nextInt(5)) {
            var3.setBlockState(var11, var19, var10, Blocks.bedrock.getDefaultState());
         } else {
            IBlockState var20 = var3.getBlockState(var11, var19, var10);
            if (var20.getBlock().getMaterial() == Material.air) {
               var17 = -1;
            } else if (var20.getBlock() == Blocks.stone) {
               IBlockState var21;
               if (var17 == -1) {
                  var26 = false;
                  if (var15 <= 0) {
                     var13 = null;
                     var24 = Blocks.stone.getDefaultState();
                  } else if (var19 >= var23 - 4 && var19 <= var23 + 1) {
                     var13 = Blocks.stained_hardened_clay.getDefaultState();
                     var24 = this.fillerBlock;
                  }

                  if (var19 < var23 && (var13 == null || var13.getBlock().getMaterial() == Material.air)) {
                     var13 = Blocks.water.getDefaultState();
                  }

                  var17 = var15 + Math.max(0, var19 - var23);
                  if (var19 < var23 - 1) {
                     var3.setBlockState(var11, var19, var10, var24);
                     if (var24.getBlock() == Blocks.stained_hardened_clay) {
                        var3.setBlockState(var11, var19, var10, var24.getBlock().getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
                     }
                  } else if (this.field_150620_aI && var19 > 86 + var15 * 2) {
                     if (var25) {
                        var3.setBlockState(var11, var19, var10, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
                     } else {
                        var3.setBlockState(var11, var19, var10, Blocks.grass.getDefaultState());
                     }
                  } else if (var19 <= var23 + 3 + var15) {
                     var3.setBlockState(var11, var19, var10, this.topBlock);
                     var26 = true;
                  } else {
                     if (var19 >= 64 && var19 <= 127) {
                        if (var25) {
                           var21 = Blocks.hardened_clay.getDefaultState();
                        } else {
                           var21 = this.func_180629_a(var4, var19, var5);
                        }
                     } else {
                        var21 = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
                     }

                     var3.setBlockState(var11, var19, var10, var21);
                  }
               } else if (var17 > 0) {
                  --var17;
                  if (var26) {
                     var3.setBlockState(var11, var19, var10, Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
                  } else {
                     var21 = this.func_180629_a(var4, var19, var5);
                     var3.setBlockState(var11, var19, var10, var21);
                  }
               }
            }
         }
      }

   }

   public WorldGenAbstractTree genBigTreeChance(Random var1) {
      return this.worldGeneratorTrees;
   }

   private IBlockState func_180629_a(int var1, int var2, int var3) {
      int var4 = (int)Math.round(this.field_150625_aG.func_151601_a((double)var1 * 1.0D / 512.0D, (double)var1 * 1.0D / 512.0D) * 2.0D);
      return this.field_150621_aC[(var2 + var4 + 64) % 64];
   }

   public int getFoliageColorAtPos(BlockPos var1) {
      return 10387789;
   }

   public BiomeGenMesa(int var1, boolean var2, boolean var3) {
      super(var1);
      this.field_150626_aH = var2;
      this.field_150620_aI = var3;
      this.setDisableRain();
      this.setTemperatureRainfall(2.0F, 0.0F);
      this.spawnableCreatureList.clear();
      this.topBlock = Blocks.sand.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND);
      this.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
      this.theBiomeDecorator.treesPerChunk = -999;
      this.theBiomeDecorator.deadBushPerChunk = 20;
      this.theBiomeDecorator.reedsPerChunk = 3;
      this.theBiomeDecorator.cactiPerChunk = 5;
      this.theBiomeDecorator.flowersPerChunk = 0;
      this.spawnableCreatureList.clear();
      if (var3) {
         this.theBiomeDecorator.treesPerChunk = 5;
      }

   }

   private void func_150619_a(long var1) {
      this.field_150621_aC = new IBlockState[64];
      Arrays.fill(this.field_150621_aC, Blocks.hardened_clay.getDefaultState());
      Random var3 = new Random(var1);
      this.field_150625_aG = new NoiseGeneratorPerlin(var3, 1);

      int var4;
      for(var4 = 0; var4 < 64; ++var4) {
         var4 += var3.nextInt(5) + 1;
         if (var4 < 64) {
            this.field_150621_aC[var4] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
         }
      }

      var4 = var3.nextInt(4) + 2;

      int var5;
      int var6;
      int var7;
      int var8;
      for(var5 = 0; var5 < var4; ++var5) {
         var6 = var3.nextInt(3) + 1;
         var7 = var3.nextInt(64);

         for(var8 = 0; var7 + var8 < 64 && var8 < var6; ++var8) {
            this.field_150621_aC[var7 + var8] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW);
         }
      }

      var5 = var3.nextInt(4) + 2;

      int var9;
      for(var6 = 0; var6 < var5; ++var6) {
         var7 = var3.nextInt(3) + 2;
         var8 = var3.nextInt(64);

         for(var9 = 0; var8 + var9 < 64 && var9 < var7; ++var9) {
            this.field_150621_aC[var8 + var9] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BROWN);
         }
      }

      var6 = var3.nextInt(4) + 2;

      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var3.nextInt(3) + 1;
         var9 = var3.nextInt(64);

         for(int var10 = 0; var9 + var10 < 64 && var10 < var8; ++var10) {
            this.field_150621_aC[var9 + var10] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
         }
      }

      var7 = var3.nextInt(3) + 3;
      var8 = 0;

      for(var9 = 0; var9 < var7; ++var9) {
         byte var12 = 1;
         var8 += var3.nextInt(16) + 4;

         for(int var11 = 0; var8 + var11 < 64 && var11 < var12; ++var11) {
            this.field_150621_aC[var8 + var11] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);
            if (var8 + var11 > 1 && var3.nextBoolean()) {
               this.field_150621_aC[var8 + var11 - 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
            }

            if (var8 + var11 < 63 && var3.nextBoolean()) {
               this.field_150621_aC[var8 + var11 + 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
            }
         }
      }

   }

   public void decorate(World var1, Random var2, BlockPos var3) {
      super.decorate(var1, var2, var3);
   }

   public int getGrassColorAtPos(BlockPos var1) {
      return 9470285;
   }

   protected BiomeGenBase createMutatedBiome(int var1) {
      boolean var2 = this.biomeID == BiomeGenBase.mesa.biomeID;
      BiomeGenMesa var3 = new BiomeGenMesa(var1, var2, this.field_150620_aI);
      if (!var2) {
         var3.setHeight(height_LowHills);
         var3.setBiomeName(this.biomeName + " M");
      } else {
         var3.setBiomeName(this.biomeName + " (Bryce)");
      }

      var3.func_150557_a(this.color, true);
      return var3;
   }
}
