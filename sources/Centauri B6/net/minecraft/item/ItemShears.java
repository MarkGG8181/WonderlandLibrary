package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemShears extends Item {
   public ItemShears() {
      this.setMaxStackSize(1);
      this.setMaxDamage(238);
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
      if(blockIn.getMaterial() != Material.leaves && blockIn != Blocks.web && blockIn != Blocks.tallgrass && blockIn != Blocks.vine && blockIn != Blocks.tripwire && blockIn != Blocks.wool) {
         return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
      } else {
         stack.damageItem(1, playerIn);
         return true;
      }
   }

   public float getStrVsBlock(ItemStack stack, Block block) {
      return block != Blocks.web && block.getMaterial() != Material.leaves?(block == Blocks.wool?5.0F:super.getStrVsBlock(stack, block)):15.0F;
   }

   public boolean canHarvestBlock(Block blockIn) {
      return blockIn == Blocks.web || blockIn == Blocks.redstone_wire || blockIn == Blocks.tripwire;
   }
}
