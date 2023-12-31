/*
 * Decompiled with CFR 0_122.
 */
package me.arithmo.module.impl.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.Multimap;

import me.arithmo.event.Event;
import me.arithmo.event.RegisterEvent;
import me.arithmo.event.impl.EventTick;
import me.arithmo.module.Module;
import me.arithmo.module.data.ModuleData;
import me.arithmo.module.data.Setting;
import me.arithmo.module.impl.combat.AutoArmor;
import me.arithmo.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class Janitor
extends Module {
    public static List<Integer> blacklistedItems = new ArrayList<Integer>();
    private final String INV = "INVONLY";
    private final String TOGGLE = "TOGGLE";
    private Timer timer = new Timer();

    public Janitor(ModuleData data) {
        super(data);
        this.settings.put("INVONLY", new Setting<Boolean>("INVONLY", false, "Only clean when inventory is open."));
        this.settings.put("AUTO", new Setting<Boolean>("AUTO", true, "Turn off when finished."));
        this.settings.put("TOGGLE", new Setting<Boolean>("TOGGLE", false, "Turn off when finished."));
        me.arithmo.management.command.impl.Janitor.loadIDs();
    }

    @RegisterEvent(events={EventTick.class})
    public void onEvent(Event event) {
        boolean hasNoItems = true;
        int i;
        ItemStack is;
        Item item;
        for (i = 0; i < 36; ++i) {
            if (!Janitor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !this.isValid(item = (is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack()).getItem())) continue;
            hasNoItems = false;
        }
    	if (!((Boolean)((Setting)this.settings.get("AUTO")).getValue()).booleanValue()) { 
	        this.setSuffix(blacklistedItems.isEmpty() ? null : "" + blacklistedItems.size() + "");
	        for (i = 0; i < 36; ++i) {
	            if (!Janitor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !this.isValid(item = (is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack()).getItem()) || !this.timer.delay(100.0f)) continue;
	            this.timer.reset();
	            Janitor.mc.playerController.windowClick(Janitor.mc.thePlayer.inventoryContainer.windowId, i, 0, 0, Janitor.mc.thePlayer);
	            Janitor.mc.playerController.windowClick(Janitor.mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, Janitor.mc.thePlayer);
	        }
    	} else {
    		if (!(Boolean)((Setting)this.settings.get("INVONLY")).getValue() || (mc.currentScreen instanceof GuiInventory && (Boolean)((Setting)this.settings.get("INVONLY")).getValue())) {

    			if (!AutoArmor.timer.delay(100f)) {
    				return;
    			}
    			if (timer.delay(135f)) {
    				CopyOnWriteArrayList<Integer> uselessItems = new CopyOnWriteArrayList<Integer>();

    				for (int o = 0; o < 45; ++o) {
    					if (mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
    						ItemStack item1 = mc.thePlayer.inventoryContainer.getSlot(o).getStack();
    						if (mc.thePlayer.inventory.armorItemInSlot(0) == item1
    								|| mc.thePlayer.inventory.armorItemInSlot(1) == item1
    								|| mc.thePlayer.inventory.armorItemInSlot(2) == item1
    								|| mc.thePlayer.inventory.armorItemInSlot(3) == item1)
    							continue;
    						if (item1 != null && item1.getItem() != null && Item.getIdFromItem(item1.getItem()) != 0
    								&& !stackIsUseful(o)) {
    							uselessItems.add(o);
    						}
    					}
    				}

    				if (!uselessItems.isEmpty()) {
    					mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId,
    							uselessItems.get(0), 1, 4, mc.thePlayer);
    					uselessItems.remove(0);
    					timer.reset();
    				} else {
    				}

    			}

    		}
    	}
        if (hasNoItems && ((Boolean)((Setting)this.settings.get("TOGGLE")).getValue()).booleanValue()) {
            this.toggle();
        }
    }

    @Override
    public void onEnable() {
        me.arithmo.management.command.impl.Janitor.saveIDs();
    }

    @Override
    public void onDisable() {
        me.arithmo.management.command.impl.Janitor.saveIDs();
    }

    public boolean isValid(Item item) {
        if (blacklistedItems.contains(Item.getIdFromItem(item))) {
            return (Boolean)((Setting)this.settings.get("INVONLY")).getValue() == false || Janitor.mc.currentScreen instanceof GuiInventory;
        }
        return false;
    }
    
	private boolean stackIsUseful(int i) {
		ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

		boolean hasAlreadyOrBetter = false;

		if (itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemPickaxe
				|| itemStack.getItem() instanceof ItemAxe) {
			for (int o = 0; o < 45; ++o) {
				if (o == i)
					continue;
				if (mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
					ItemStack item = mc.thePlayer.inventoryContainer.getSlot(o).getStack();
					if (item != null && item.getItem() instanceof ItemSword || item.getItem() instanceof ItemAxe
							|| item.getItem() instanceof ItemPickaxe) {
						float damageFound = getItemDamage(itemStack);
						damageFound += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED);

						float damageCurrent = getItemDamage(item);
						damageCurrent += EnchantmentHelper.func_152377_a(item, EnumCreatureAttribute.UNDEFINED);

						if (damageCurrent > damageFound) {
							hasAlreadyOrBetter = true;
							break;
						}
					}
				}
			}
		} else if (itemStack.getItem() instanceof ItemArmor) {
			for (int o = 0; o < 45; ++o) {
				if (i == o)
					continue;
				if (mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
					ItemStack item = mc.thePlayer.inventoryContainer.getSlot(o).getStack();
					if (item != null && item.getItem() instanceof ItemArmor) {

						List<Integer> helmet = Arrays.asList(298, 314, 302, 306, 310);
						List<Integer> chestplate = Arrays.asList(299, 315, 303, 307, 311);
						List<Integer> leggings = Arrays.asList(300, 316, 304, 308, 312);
						List<Integer> boots = Arrays.asList(301, 317, 305, 309, 313);

						if (helmet.contains(Item.getIdFromItem(item.getItem()))
								&& helmet.contains(Item.getIdFromItem(itemStack.getItem()))) {
							if (helmet.indexOf(Item.getIdFromItem(itemStack.getItem())) < helmet
									.indexOf(Item.getIdFromItem(item.getItem()))) {
								hasAlreadyOrBetter = true;
								break;
							}
						} else if (chestplate.contains(Item.getIdFromItem(item.getItem()))
								&& chestplate.contains(Item.getIdFromItem(itemStack.getItem()))) {
							if (chestplate.indexOf(Item.getIdFromItem(itemStack.getItem())) < chestplate
									.indexOf(Item.getIdFromItem(item.getItem()))) {
								hasAlreadyOrBetter = true;
								break;
							}
						} else if (leggings.contains(Item.getIdFromItem(item.getItem()))
								&& leggings.contains(Item.getIdFromItem(itemStack.getItem()))) {
							if (leggings.indexOf(Item.getIdFromItem(itemStack.getItem())) < leggings
									.indexOf(Item.getIdFromItem(item.getItem()))) {
								hasAlreadyOrBetter = true;
								break;
							}
						} else if (boots.contains(Item.getIdFromItem(item.getItem()))
								&& boots.contains(Item.getIdFromItem(itemStack.getItem()))) {
							if (boots.indexOf(Item.getIdFromItem(itemStack.getItem())) < boots
									.indexOf(Item.getIdFromItem(item.getItem()))) {
								hasAlreadyOrBetter = true;
								break;
							}
						}

					}
				}
			}
		}

		for (int o = 0; o < 45; ++o) {
			if (i == o)
				continue;
			if (mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
				ItemStack item = mc.thePlayer.inventoryContainer.getSlot(o).getStack();
				if (item != null && (item.getItem() instanceof ItemSword || item.getItem() instanceof ItemAxe
						|| item.getItem() instanceof ItemBow || item.getItem() instanceof ItemFishingRod
						|| item.getItem() instanceof ItemArmor || item.getItem() instanceof ItemAxe
						|| item.getItem() instanceof ItemPickaxe || Item.getIdFromItem(item.getItem()) == 346)) {
					Item found = (Item) item.getItem();
					if (Item.getIdFromItem(itemStack.getItem()) == Item.getIdFromItem(item.getItem())) {
						hasAlreadyOrBetter = true;
						break;
					}
				}
			}
		}

		if (Item.getIdFromItem(itemStack.getItem()) == 367)
			return false; // rotten flesh
		if (Item.getIdFromItem(itemStack.getItem()) == 30)
			return true; // cobweb
		if (Item.getIdFromItem(itemStack.getItem()) == 259)
			return true; // flint & steel
		if (Item.getIdFromItem(itemStack.getItem()) == 262)
			return true; // arrow
		if (Item.getIdFromItem(itemStack.getItem()) == 264)
			return true; // diamond
		if (Item.getIdFromItem(itemStack.getItem()) == 265)
			return true; // iron
		if (Item.getIdFromItem(itemStack.getItem()) == 346)
			return true; // fishing rod
		if (Item.getIdFromItem(itemStack.getItem()) == 384)
			return true; // bottle o' enchanting
		if (Item.getIdFromItem(itemStack.getItem()) == 345)
			return true; // compass
		if (Item.getIdFromItem(itemStack.getItem()) == 296)
			return true; // wheat
		if (Item.getIdFromItem(itemStack.getItem()) == 336)
			return true; // brick
		if (Item.getIdFromItem(itemStack.getItem()) == 266)
			return true; // gold ingot
		if (Item.getIdFromItem(itemStack.getItem()) == 280)
			return true; // stick
		if (itemStack.hasDisplayName())
			return true;

		if (hasAlreadyOrBetter) {
			return false;
		}

		if (itemStack.getItem() instanceof ItemArmor)
			return true;
		if (itemStack.getItem() instanceof ItemAxe)
			return true;
		if (itemStack.getItem() instanceof ItemBow)
			return true;
		if (itemStack.getItem() instanceof ItemSword)
			return true;
		if (itemStack.getItem() instanceof ItemPotion)
			return true;
		if (itemStack.getItem() instanceof ItemFlintAndSteel)
			return true;
		if (itemStack.getItem() instanceof ItemEnderPearl)
			return true;
		if (itemStack.getItem() instanceof ItemBlock)
			return true;
		if (itemStack.getItem() instanceof ItemFood)
			return true;
		if (itemStack.getItem() instanceof ItemPickaxe)
			return true;
		return false;
	}

	private float getItemDamage(final ItemStack itemStack) {
		final Multimap multimap = itemStack.getAttributeModifiers();
		if (!multimap.isEmpty()) {
			final Iterator iterator = multimap.entries().iterator();
			if (iterator.hasNext()) {
				final Map.Entry entry = (Entry) iterator.next();
				final AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
				double damage;
				if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
					damage = attributeModifier.getAmount();
				} else {
					damage = attributeModifier.getAmount() * 100.0;
				}
				if (attributeModifier.getAmount() > 1.0) {
					return 1.0f + (float) damage;
				}
				return 1.0f;
			}
		}
		return 1.0f;
	}
    
}

