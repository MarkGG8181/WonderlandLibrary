// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.world.gen.feature;

import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import java.util.Random;
import net.minecraft.world.World;

public class WorldGenReed extends WorldGenerator
{
    @Override
    public boolean generate(final World worldIn, final Random rand, final BlockPos position) {
        for (int i = 0; i < 20; ++i) {
            final BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
            if (worldIn.isAirBlock(blockpos)) {
                final BlockPos blockpos2 = blockpos.down();
                if (worldIn.getBlockState(blockpos2.west()).getMaterial() == Material.WATER || worldIn.getBlockState(blockpos2.east()).getMaterial() == Material.WATER || worldIn.getBlockState(blockpos2.north()).getMaterial() == Material.WATER || worldIn.getBlockState(blockpos2.south()).getMaterial() == Material.WATER) {
                    for (int j = 2 + rand.nextInt(rand.nextInt(3) + 1), k = 0; k < j; ++k) {
                        if (Blocks.REEDS.canBlockStay(worldIn, blockpos)) {
                            worldIn.setBlockState(blockpos.up(k), Blocks.REEDS.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
