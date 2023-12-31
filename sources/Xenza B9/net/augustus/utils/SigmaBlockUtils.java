// 
// Decompiled by Procyon v0.6.0
// 

package net.augustus.utils;

import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.client.Minecraft;

public class SigmaBlockUtils
{
    private static Minecraft mc;
    private static List<Block> blacklistedBlocks;
    
    public static float[] getRotationsNeeded(final BlockPos pos) {
        final double diffX = pos.getX() + 0.5 - SigmaBlockUtils.mc.thePlayer.posX;
        final double diffY = pos.getY() + 0.5 - SigmaBlockUtils.mc.thePlayer.posY + SigmaBlockUtils.mc.thePlayer.height;
        final double diffZ = pos.getZ() + 0.5 - SigmaBlockUtils.mc.thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { SigmaBlockUtils.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - SigmaBlockUtils.mc.thePlayer.rotationYaw), SigmaBlockUtils.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - SigmaBlockUtils.mc.thePlayer.rotationPitch) };
    }
    
    public static float[] updateDirections(final BlockPos pos) {
        final float[] looks = getRotationsNeeded(pos);
        if (SigmaBlockUtils.mc.thePlayer.isCollidedVertically) {
            SigmaBlockUtils.mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer.C05PacketPlayerLook(looks[0], looks[1], SigmaBlockUtils.mc.thePlayer.onGround));
        }
        return looks;
    }
    
    public static void updateTool(final BlockPos pos) {
        final Block block = SigmaBlockUtils.mc.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0f;
        int bestItemIndex = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = SigmaBlockUtils.mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
                strength = itemStack.getStrVsBlock(block);
                bestItemIndex = i;
            }
        }
        if (bestItemIndex != -1) {
            SigmaBlockUtils.mc.thePlayer.inventory.currentItem = bestItemIndex;
        }
    }
    
    public static boolean isInLiquid() {
        if (SigmaBlockUtils.mc.thePlayer.isInWater()) {
            return true;
        }
        boolean inLiquid = false;
        final int y = (int)SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().minY;
        for (int x = MathHelper.floor_double(SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                final Block block = SigmaBlockUtils.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && block.getMaterial() != Material.air) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static boolean isOnLiquid() {
        if (SigmaBlockUtils.mc.thePlayer == null) {
            return false;
        }
        boolean onLiquid = false;
        final int y = (int)SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -0.01, 0.0).minY;
        for (int x = MathHelper.floor_double(SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                final Block block = SigmaBlockUtils.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && block.getMaterial() != Material.air) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
    
    public static boolean isOnLiquid(final double profondeur) {
        boolean onLiquid = false;
        if (SigmaBlockUtils.mc.theWorld.getBlockState(new BlockPos(SigmaBlockUtils.mc.thePlayer.posX, SigmaBlockUtils.mc.thePlayer.posY - profondeur, SigmaBlockUtils.mc.thePlayer.posZ)).getBlock().getMaterial().isLiquid()) {
            onLiquid = true;
        }
        return onLiquid;
    }
    
    public static boolean isTotalOnLiquid(final double profondeur) {
        for (double x = SigmaBlockUtils.mc.thePlayer.boundingBox.minX; x < SigmaBlockUtils.mc.thePlayer.boundingBox.maxX; x += 0.009999999776482582) {
            for (double z = SigmaBlockUtils.mc.thePlayer.boundingBox.minZ; z < SigmaBlockUtils.mc.thePlayer.boundingBox.maxZ; z += 0.009999999776482582) {
                final Block block = SigmaBlockUtils.mc.theWorld.getBlockState(new BlockPos(x, SigmaBlockUtils.mc.thePlayer.posY - profondeur, z)).getBlock();
                if (!(block instanceof BlockLiquid) && !(block instanceof BlockAir)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isOnGround(final double height) {
        return !SigmaBlockUtils.mc.theWorld.getCollidingBoundingBoxes(SigmaBlockUtils.mc.thePlayer, SigmaBlockUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    public static List<Block> getBlacklistedBlocks() {
        return SigmaBlockUtils.blacklistedBlocks;
    }
    
    static {
        SigmaBlockUtils.mc = Minecraft.getMinecraft();
        SigmaBlockUtils.blacklistedBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever);
    }
}
