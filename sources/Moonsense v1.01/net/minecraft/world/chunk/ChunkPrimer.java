// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;

public class ChunkPrimer
{
    private final short[] data;
    private final IBlockState defaultState;
    private static final String __OBFID = "CL_00002007";
    
    public ChunkPrimer() {
        this.data = new short[65536];
        this.defaultState = Blocks.air.getDefaultState();
    }
    
    public IBlockState getBlockState(final int x, final int y, final int z) {
        final int var4 = x << 12 | z << 8 | y;
        return this.getBlockState(var4);
    }
    
    public IBlockState getBlockState(final int index) {
        if (index >= 0 && index < this.data.length) {
            final IBlockState var2 = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[index]);
            return (var2 != null) ? var2 : this.defaultState;
        }
        throw new IndexOutOfBoundsException("The coordinate is out of range");
    }
    
    public void setBlockState(final int x, final int y, final int z, final IBlockState state) {
        final int var5 = x << 12 | z << 8 | y;
        this.setBlockState(var5, state);
    }
    
    public void setBlockState(final int index, final IBlockState state) {
        if (index >= 0 && index < this.data.length) {
            this.data[index] = (short)Block.BLOCK_STATE_IDS.get(state);
            return;
        }
        throw new IndexOutOfBoundsException("The coordinate is out of range");
    }
}
