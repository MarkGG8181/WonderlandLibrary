package net.minecraft.world.gen.feature;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPosition;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenFire extends WorldGenerator
{
    public boolean generate(World worldIn, Random rand, BlockPosition position)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPosition blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.netherrack)
            {
                worldIn.setBlockState(blockpos, Blocks.fire.getDefaultState(), 2);
            }
        }

        return true;
    }
}
