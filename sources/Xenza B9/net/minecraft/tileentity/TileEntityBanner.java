// 
// Decompiled by Procyon v0.6.0
// 

package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import com.google.common.collect.Lists;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.item.EnumDyeColor;
import java.util.List;
import net.minecraft.nbt.NBTTagList;

public class TileEntityBanner extends TileEntity
{
    private int baseColor;
    private NBTTagList patterns;
    private boolean field_175119_g;
    private List<EnumBannerPattern> patternList;
    private List<EnumDyeColor> colorList;
    private String patternResourceLocation;
    
    public void setItemValues(final ItemStack stack) {
        this.patterns = null;
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10)) {
            final NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("BlockEntityTag");
            if (nbttagcompound.hasKey("Patterns")) {
                this.patterns = (NBTTagList)nbttagcompound.getTagList("Patterns", 10).copy();
            }
            if (nbttagcompound.hasKey("Base", 99)) {
                this.baseColor = nbttagcompound.getInteger("Base");
            }
            else {
                this.baseColor = (stack.getMetadata() & 0xF);
            }
        }
        else {
            this.baseColor = (stack.getMetadata() & 0xF);
        }
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = "";
        this.field_175119_g = true;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        func_181020_a(compound, this.baseColor, this.patterns);
    }
    
    public static void func_181020_a(final NBTTagCompound p_181020_0_, final int p_181020_1_, final NBTTagList p_181020_2_) {
        p_181020_0_.setInteger("Base", p_181020_1_);
        if (p_181020_2_ != null) {
            p_181020_0_.setTag("Patterns", p_181020_2_);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.baseColor = compound.getInteger("Base");
        this.patterns = compound.getTagList("Patterns", 10);
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = null;
        this.field_175119_g = true;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.pos, 6, nbttagcompound);
    }
    
    public int getBaseColor() {
        return this.baseColor;
    }
    
    public static int getBaseColor(final ItemStack stack) {
        final NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
        return (nbttagcompound != null && nbttagcompound.hasKey("Base")) ? nbttagcompound.getInteger("Base") : stack.getMetadata();
    }
    
    public static int getPatterns(final ItemStack stack) {
        final NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
        return (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) ? nbttagcompound.getTagList("Patterns", 10).tagCount() : 0;
    }
    
    public List<EnumBannerPattern> getPatternList() {
        this.initializeBannerData();
        return this.patternList;
    }
    
    public NBTTagList func_181021_d() {
        return this.patterns;
    }
    
    public List<EnumDyeColor> getColorList() {
        this.initializeBannerData();
        return this.colorList;
    }
    
    public String func_175116_e() {
        this.initializeBannerData();
        return this.patternResourceLocation;
    }
    
    private void initializeBannerData() {
        if (this.patternList == null || this.colorList == null || this.patternResourceLocation == null) {
            if (!this.field_175119_g) {
                this.patternResourceLocation = "";
            }
            else {
                this.patternList = (List<EnumBannerPattern>)Lists.newArrayList();
                this.colorList = (List<EnumDyeColor>)Lists.newArrayList();
                this.patternList.add(EnumBannerPattern.BASE);
                this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
                this.patternResourceLocation = "b" + this.baseColor;
                if (this.patterns != null) {
                    for (int i = 0; i < this.patterns.tagCount(); ++i) {
                        final NBTTagCompound nbttagcompound = this.patterns.getCompoundTagAt(i);
                        final EnumBannerPattern tileentitybanner$enumbannerpattern = EnumBannerPattern.getPatternByID(nbttagcompound.getString("Pattern"));
                        if (tileentitybanner$enumbannerpattern != null) {
                            this.patternList.add(tileentitybanner$enumbannerpattern);
                            final int j = nbttagcompound.getInteger("Color");
                            this.colorList.add(EnumDyeColor.byDyeDamage(j));
                            this.patternResourceLocation = this.patternResourceLocation + tileentitybanner$enumbannerpattern.getPatternID() + j;
                        }
                    }
                }
            }
        }
    }
    
    public static void removeBannerData(final ItemStack stack) {
        final NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
        if (nbttagcompound != null && nbttagcompound.hasKey("Patterns", 9)) {
            final NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
            if (nbttaglist.tagCount() > 0) {
                nbttaglist.removeTag(nbttaglist.tagCount() - 1);
                if (nbttaglist.hasNoTags()) {
                    stack.getTagCompound().removeTag("BlockEntityTag");
                    if (stack.getTagCompound().hasNoTags()) {
                        stack.setTagCompound(null);
                    }
                }
            }
        }
    }
    
    public enum EnumBannerPattern
    {
        BASE("base", "b"), 
        SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "), 
        SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"), 
        SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "), 
        SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "), 
        STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"), 
        STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "), 
        STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "), 
        STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"), 
        STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "), 
        STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "), 
        STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"), 
        STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "), 
        STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "), 
        CROSS("cross", "cr", "# #", " # ", "# #"), 
        STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "), 
        TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"), 
        TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "), 
        TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "), 
        TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "), 
        DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "), 
        DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"), 
        DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "), 
        DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "), 
        CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "), 
        RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "), 
        HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "), 
        HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "), 
        HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"), 
        HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"), 
        BORDER("border", "bo", "###", "# #", "###"), 
        CURLY_BORDER("curly_border", "cbo", new ItemStack(Blocks.vine)), 
        CREEPER("creeper", "cre", new ItemStack(Items.skull, 1, 4)), 
        GRADIENT("gradient", "gra", "# #", " # ", " # "), 
        GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"), 
        BRICKS("bricks", "bri", new ItemStack(Blocks.brick_block)), 
        SKULL("skull", "sku", new ItemStack(Items.skull, 1, 1)), 
        FLOWER("flower", "flo", new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta())), 
        MOJANG("mojang", "moj", new ItemStack(Items.golden_apple, 1, 1));
        
        private String patternName;
        private String patternID;
        private String[] craftingLayers;
        private ItemStack patternCraftingStack;
        
        private EnumBannerPattern(final String name, final String id) {
            this.craftingLayers = new String[3];
            this.patternName = name;
            this.patternID = id;
        }
        
        private EnumBannerPattern(final String name, final String id, final ItemStack craftingItem) {
            this(name, id);
            this.patternCraftingStack = craftingItem;
        }
        
        private EnumBannerPattern(final String name, final String id, final String craftingTop, final String craftingMid, final String craftingBot) {
            this(name, id);
            this.craftingLayers[0] = craftingTop;
            this.craftingLayers[1] = craftingMid;
            this.craftingLayers[2] = craftingBot;
        }
        
        public String getPatternName() {
            return this.patternName;
        }
        
        public String getPatternID() {
            return this.patternID;
        }
        
        public String[] getCraftingLayers() {
            return this.craftingLayers;
        }
        
        public boolean hasValidCrafting() {
            return this.patternCraftingStack != null || this.craftingLayers[0] != null;
        }
        
        public boolean hasCraftingStack() {
            return this.patternCraftingStack != null;
        }
        
        public ItemStack getCraftingStack() {
            return this.patternCraftingStack;
        }
        
        public static EnumBannerPattern getPatternByID(final String id) {
            for (final EnumBannerPattern tileentitybanner$enumbannerpattern : values()) {
                if (tileentitybanner$enumbannerpattern.patternID.equals(id)) {
                    return tileentitybanner$enumbannerpattern;
                }
            }
            return null;
        }
    }
}
