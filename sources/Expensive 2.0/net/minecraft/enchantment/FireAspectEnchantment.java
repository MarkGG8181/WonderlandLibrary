package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

public class FireAspectEnchantment extends Enchantment
{
    protected FireAspectEnchantment(Rarity rarityIn, EquipmentSlotType... slots)
    {
        super(rarityIn, EnchantmentType.WEAPON, slots);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 20 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 2;
    }
}
