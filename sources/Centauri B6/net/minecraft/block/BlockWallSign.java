package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockWallSign.1;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallSign extends BlockSign {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);

   public BlockWallSign() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumFacing)state.getValue(FACING)).getIndex();
   }

   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
      EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
      if(!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().getMaterial().isSolid()) {
         this.dropBlockAsItem(worldIn, pos, state, 0);
         worldIn.setBlockToAir(pos);
      }

      super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
   }

   public IBlockState getStateFromMeta(int meta) {
      EnumFacing enumfacing = EnumFacing.getFront(meta);
      if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, enumfacing);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING});
   }

   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
      EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue(FACING);
      float f = 0.28125F;
      float f1 = 0.78125F;
      float f2 = 0.0F;
      float f3 = 1.0F;
      float f4 = 0.125F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      switch(1.$SwitchMap$net$minecraft$util$EnumFacing[enumfacing.ordinal()]) {
      case 1:
         this.setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
         break;
      case 2:
         this.setBlockBounds(f2, f, 0.0F, f3, f1, f4);
         break;
      case 3:
         this.setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
         break;
      case 4:
         this.setBlockBounds(0.0F, f, f2, f4, f1, f3);
      }

   }
}
