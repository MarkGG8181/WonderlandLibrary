// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.item.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class RecipesArmor
{
    private String[][] recipePatterns;
    private Item[][] recipeItems;
    private static final String __OBFID = "CL_00000080";
    
    public RecipesArmor() {
        this.recipePatterns = new String[][] { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
        this.recipeItems = new Item[][] { { Items.leather, Items.iron_ingot, Items.diamond, Items.gold_ingot }, { Items.leather_helmet, Items.iron_helmet, Items.diamond_helmet, Items.golden_helmet }, { Items.leather_chestplate, Items.iron_chestplate, Items.diamond_chestplate, Items.golden_chestplate }, { Items.leather_leggings, Items.iron_leggings, Items.diamond_leggings, Items.golden_leggings }, { Items.leather_boots, Items.iron_boots, Items.diamond_boots, Items.golden_boots } };
    }
    
    public void addRecipes(final CraftingManager p_77609_1_) {
        for (int var2 = 0; var2 < this.recipeItems[0].length; ++var2) {
            final Item var3 = this.recipeItems[0][var2];
            for (int var4 = 0; var4 < this.recipeItems.length - 1; ++var4) {
                final Item var5 = this.recipeItems[var4 + 1][var2];
                p_77609_1_.addRecipe(new ItemStack(var5), this.recipePatterns[var4], 'X', var3);
            }
        }
    }
}
