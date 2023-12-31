package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class BlockStoneSlabNew extends BlockSlab {
   public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockStoneSlabNew.EnumType.class);

   public MapColor getMapColor(IBlockState var1) {
      return ((BlockStoneSlabNew.EnumType)var1.getValue(VARIANT)).func_181068_c();
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockStoneSlabNew.EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return this.isDouble() ? new BlockState(this, new IProperty[]{SEAMLESS, VARIANT}) : new BlockState(this, new IProperty[]{HALF, VARIANT});
   }

   public String getUnlocalizedName(int var1) {
      return super.getUnlocalizedName() + "." + BlockStoneSlabNew.EnumType.byMetadata(var1).getUnlocalizedName();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      if (var1 != Item.getItemFromBlock(Blocks.double_stone_slab2)) {
         BlockStoneSlabNew.EnumType[] var7;
         int var6 = (var7 = BlockStoneSlabNew.EnumType.values()).length;

         for(int var5 = 0; var5 < var6; ++var5) {
            BlockStoneSlabNew.EnumType var4 = var7[var5];
            var3.add(new ItemStack(var1, 1, var4.getMetadata()));
         }
      }

   }

   public BlockStoneSlabNew() {
      super(Material.rock);
      IBlockState var1 = this.blockState.getBaseState();
      if (this.isDouble()) {
         var1 = var1.withProperty(SEAMLESS, false);
      } else {
         var1 = var1.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.setDefaultState(var1.withProperty(VARIANT, BlockStoneSlabNew.EnumType.RED_SANDSTONE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState().withProperty(VARIANT, BlockStoneSlabNew.EnumType.byMetadata(var1 & 7));
      if (this.isDouble()) {
         var2 = var2.withProperty(SEAMLESS, (var1 & 8) != 0);
      } else {
         var2 = var2.withProperty(HALF, (var1 & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
      }

      return var2;
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.getUnlocalizedName() + ".red_sandstone.name");
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.stone_slab2);
   }

   public Object getVariant(ItemStack var1) {
      return BlockStoneSlabNew.EnumType.byMetadata(var1.getMetadata() & 7);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.stone_slab2);
   }

   public IProperty getVariantProperty() {
      return VARIANT;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockStoneSlabNew.EnumType)var1.getValue(VARIANT)).getMetadata();
      if (this.isDouble()) {
         if ((Boolean)var1.getValue(SEAMLESS)) {
            var3 |= 8;
         }
      } else if (var1.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
         var3 |= 8;
      }

      return var3;
   }

   public static enum EnumType implements IStringSerializable {
      private final String name;
      private static final BlockStoneSlabNew.EnumType[] ENUM$VALUES = new BlockStoneSlabNew.EnumType[]{RED_SANDSTONE};
      private static final BlockStoneSlabNew.EnumType[] META_LOOKUP = new BlockStoneSlabNew.EnumType[values().length];
      RED_SANDSTONE(0, "red_sandstone", BlockSand.EnumType.RED_SAND.getMapColor());

      private final int meta;
      private final MapColor field_181069_e;

      static {
         BlockStoneSlabNew.EnumType[] var3;
         int var2 = (var3 = values()).length;

         for(int var1 = 0; var1 < var2; ++var1) {
            BlockStoneSlabNew.EnumType var0 = var3[var1];
            META_LOOKUP[var0.getMetadata()] = var0;
         }

      }

      public MapColor func_181068_c() {
         return this.field_181069_e;
      }

      public String toString() {
         return this.name;
      }

      private EnumType(int var3, String var4, MapColor var5) {
         this.meta = var3;
         this.name = var4;
         this.field_181069_e = var5;
      }

      public static BlockStoneSlabNew.EnumType byMetadata(int var0) {
         if (var0 < 0 || var0 >= META_LOOKUP.length) {
            var0 = 0;
         }

         return META_LOOKUP[var0];
      }

      public String getUnlocalizedName() {
         return this.name;
      }

      public int getMetadata() {
         return this.meta;
      }

      public String getName() {
         return this.name;
      }
   }
}
