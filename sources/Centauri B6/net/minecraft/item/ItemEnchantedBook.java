package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemEnchantedBook extends Item {
   public void getAll(Enchantment enchantment, List list) {
      for(int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
         list.add(this.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
      }

   }

   public boolean isItemTool(ItemStack stack) {
      return false;
   }

   public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
      super.addInformation(stack, playerIn, tooltip, advanced);
      NBTTagList nbttaglist = this.getEnchantments(stack);
      if(nbttaglist != null) {
         for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            int j = nbttaglist.getCompoundTagAt(i).getShort("id");
            int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
            if(Enchantment.getEnchantmentById(j) != null) {
               tooltip.add(Enchantment.getEnchantmentById(j).getTranslatedName(k));
            }
         }
      }

   }

   public boolean hasEffect(ItemStack stack) {
      return true;
   }

   public EnumRarity getRarity(ItemStack stack) {
      return this.getEnchantments(stack).tagCount() > 0?EnumRarity.UNCOMMON:super.getRarity(stack);
   }

   public ItemStack getEnchantedItemStack(EnchantmentData data) {
      ItemStack itemstack = new ItemStack(this);
      this.addEnchantment(itemstack, data);
      return itemstack;
   }

   public WeightedRandomChestContent getRandom(Random rand) {
      return this.getRandom(rand, 1, 1, 1);
   }

   public WeightedRandomChestContent getRandom(Random rand, int minChance, int maxChance, int weight) {
      ItemStack itemstack = new ItemStack(Items.book, 1, 0);
      EnchantmentHelper.addRandomEnchantment(rand, itemstack, 30);
      return new WeightedRandomChestContent(itemstack, minChance, maxChance, weight);
   }

   public NBTTagList getEnchantments(ItemStack stack) {
      NBTTagCompound nbttagcompound = stack.getTagCompound();
      return nbttagcompound != null && nbttagcompound.hasKey("StoredEnchantments", 9)?(NBTTagList)nbttagcompound.getTag("StoredEnchantments"):new NBTTagList();
   }

   public void addEnchantment(ItemStack stack, EnchantmentData enchantment) {
      NBTTagList nbttaglist = this.getEnchantments(stack);
      boolean flag = true;

      for(int i = 0; i < nbttaglist.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
         if(nbttagcompound.getShort("id") == enchantment.enchantmentobj.effectId) {
            if(nbttagcompound.getShort("lvl") < enchantment.enchantmentLevel) {
               nbttagcompound.setShort("lvl", (short)enchantment.enchantmentLevel);
            }

            flag = false;
            break;
         }
      }

      if(flag) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound1.setShort("id", (short)enchantment.enchantmentobj.effectId);
         nbttagcompound1.setShort("lvl", (short)enchantment.enchantmentLevel);
         nbttaglist.appendTag(nbttagcompound1);
      }

      if(!stack.hasTagCompound()) {
         stack.setTagCompound(new NBTTagCompound());
      }

      stack.getTagCompound().setTag("StoredEnchantments", nbttaglist);
   }
}
