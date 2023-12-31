// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.inventory;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerPlayer extends Container
{
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
    private static final String __OBFID = "CL_00001754";
    
    public ContainerPlayer(final InventoryPlayer p_i1819_1_, final boolean p_i1819_2_, final EntityPlayer p_i1819_3_) {
        this.craftMatrix = new InventoryCrafting(this, 2, 2);
        this.craftResult = new InventoryCraftResult();
        this.isLocalWorld = p_i1819_2_;
        this.thePlayer = p_i1819_3_;
        this.addSlotToContainer(new SlotCrafting(p_i1819_1_.player, this.craftMatrix, this.craftResult, 0, 144, 36));
        for (int var4 = 0; var4 < 2; ++var4) {
            for (int var5 = 0; var5 < 2; ++var5) {
                this.addSlotToContainer(new Slot(this.craftMatrix, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 4; ++var4) {
            final int var6 = var4;
            this.addSlotToContainer(new Slot(p_i1819_1_, p_i1819_1_.getSizeInventory() - 1 - var4, 8, 8 + var4 * 18) {
                private static final String __OBFID = "CL_00001755";
                
                @Override
                public int getSlotStackLimit() {
                    return 1;
                }
                
                @Override
                public boolean isItemValid(final ItemStack stack) {
                    return stack != null && ((stack.getItem() instanceof ItemArmor) ? (((ItemArmor)stack.getItem()).armorType == var6) : ((stack.getItem() == Item.getItemFromBlock(Blocks.pumpkin) || stack.getItem() == Items.skull) && var6 == 0));
                }
                
                @Override
                public String func_178171_c() {
                    return ItemArmor.EMPTY_SLOT_NAMES[var6];
                }
            });
        }
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.addSlotToContainer(new Slot(p_i1819_1_, var5 + (var4 + 1) * 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.addSlotToContainer(new Slot(p_i1819_1_, var4, 8 + var4 * 18, 142));
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory p_75130_1_) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer p_75134_1_) {
        super.onContainerClosed(p_75134_1_);
        for (int var2 = 0; var2 < 4; ++var2) {
            final ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);
            if (var3 != null) {
                p_75134_1_.dropPlayerItemWithRandomChoice(var3, false);
            }
        }
        this.craftResult.setInventorySlotContents(0, null);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(index);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (index == 0) {
                if (!this.mergeItemStack(var5, 9, 45, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            }
            else if (index >= 1 && index < 5) {
                if (!this.mergeItemStack(var5, 9, 45, false)) {
                    return null;
                }
            }
            else if (index >= 5 && index < 9) {
                if (!this.mergeItemStack(var5, 9, 45, false)) {
                    return null;
                }
            }
            else if (var3.getItem() instanceof ItemArmor && !this.inventorySlots.get(5 + ((ItemArmor)var3.getItem()).armorType).getHasStack()) {
                final int var6 = 5 + ((ItemArmor)var3.getItem()).armorType;
                if (!this.mergeItemStack(var5, var6, var6 + 1, false)) {
                    return null;
                }
            }
            else if (index >= 9 && index < 36) {
                if (!this.mergeItemStack(var5, 36, 45, false)) {
                    return null;
                }
            }
            else if (index >= 36 && index < 45) {
                if (!this.mergeItemStack(var5, 9, 36, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 9, 45, false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            }
            else {
                var4.onSlotChanged();
            }
            if (var5.stackSize == var3.stackSize) {
                return null;
            }
            var4.onPickupFromSlot(playerIn, var5);
        }
        return var3;
    }
    
    @Override
    public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return p_94530_2_.inventory != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
    }
}
