package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import org.lwjgl.util.glu.Project;

public class GuiEnchantment extends GuiContainer {
   public float field_147076_A;
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
   public int field_147073_u;
   public float field_147069_w;
   public float field_147080_z;
   private final InventoryPlayer playerInventory;
   ItemStack field_147077_B;
   public float field_147082_x;
   public float field_147071_v;
   public float field_147081_y;
   private Random random = new Random();
   private final IWorldNameable field_175380_I;
   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private static final ModelBook MODEL_BOOK = new ModelBook();
   private ContainerEnchantment container;

   public GuiEnchantment(InventoryPlayer var1, World var2, IWorldNameable var3) {
      super(new ContainerEnchantment(var1, var2));
      this.playerInventory = var1;
      this.container = (ContainerEnchantment)this.inventorySlots;
      this.field_175380_I = var3;
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      int var4 = (width - this.xSize) / 2;
      int var5 = (height - this.ySize) / 2;

      for(int var6 = 0; var6 < 3; ++var6) {
         int var7 = var1 - (var4 + 60);
         int var8 = var2 - (var5 + 14 + 19 * var6);
         if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.container.enchantItem(Minecraft.thePlayer, var6)) {
            Minecraft.playerController.sendEnchantPacket(this.container.windowId, var6);
         }
      }

   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
      int var4 = (width - this.xSize) / 2;
      int var5 = (height - this.ySize) / 2;
      drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
      GlStateManager.pushMatrix();
      GlStateManager.matrixMode(5889);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      ScaledResolution var6 = new ScaledResolution(this.mc);
      GlStateManager.viewport((var6.getScaledWidth() - 320) / 2 * var6.getScaleFactor(), (ScaledResolution.getScaledHeight() - 240) / 2 * var6.getScaleFactor(), 320 * var6.getScaleFactor(), 240 * var6.getScaleFactor());
      GlStateManager.translate(-0.34F, 0.23F, 0.0F);
      Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
      float var7 = 1.0F;
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.translate(0.0F, 3.3F, -16.0F);
      GlStateManager.scale(var7, var7, var7);
      float var8 = 5.0F;
      GlStateManager.scale(var8, var8, var8);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
      GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
      float var9 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * var1;
      GlStateManager.translate((1.0F - var9) * 0.2F, (1.0F - var9) * 0.1F, (1.0F - var9) * 0.25F);
      GlStateManager.rotate(-(1.0F - var9) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
      float var10 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * var1 + 0.25F;
      float var11 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * var1 + 0.75F;
      var10 = (var10 - (float)MathHelper.truncateDoubleToInt((double)var10)) * 1.6F - 0.3F;
      var11 = (var11 - (float)MathHelper.truncateDoubleToInt((double)var11)) * 1.6F - 0.3F;
      if (var10 < 0.0F) {
         var10 = 0.0F;
      }

      if (var11 < 0.0F) {
         var11 = 0.0F;
      }

      if (var10 > 1.0F) {
         var10 = 1.0F;
      }

      if (var11 > 1.0F) {
         var11 = 1.0F;
      }

      GlStateManager.enableRescaleNormal();
      MODEL_BOOK.render((Entity)null, 0.0F, var10, var11, var9, 0.0F, 0.0625F);
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.matrixMode(5889);
      GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNameParts.getInstance().reseedRandomGenerator((long)this.container.xpSeed);
      int var12 = this.container.getLapisAmount();

      for(int var13 = 0; var13 < 3; ++var13) {
         int var14 = var4 + 60;
         int var15 = var14 + 20;
         byte var16 = 86;
         String var17 = EnchantmentNameParts.getInstance().generateNewRandomName();
         zLevel = 0.0F;
         this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
         int var18 = this.container.enchantLevels[var13];
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         if (var18 == 0) {
            drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
         } else {
            String var19 = "" + var18;
            FontRenderer var20 = this.mc.standardGalacticFontRenderer;
            int var21 = 6839882;
            if ((var12 < var13 + 1 || Minecraft.thePlayer.experienceLevel < var18) && !Minecraft.thePlayer.capabilities.isCreativeMode) {
               drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
               drawTexturedModalRect(var14 + 1, var5 + 15 + 19 * var13, 16 * var13, 239, 16, 16);
               var20.drawSplitString(var17, var15, var5 + 16 + 19 * var13, var16, (var21 & 16711422) >> 1);
               var21 = 4226832;
            } else {
               int var22 = var2 - (var4 + 60);
               int var23 = var3 - (var5 + 14 + 19 * var13);
               if (var22 >= 0 && var23 >= 0 && var22 < 108 && var23 < 19) {
                  drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 204, 108, 19);
                  var21 = 16777088;
               } else {
                  drawTexturedModalRect(var14, var5 + 14 + 19 * var13, 0, 166, 108, 19);
               }

               drawTexturedModalRect(var14 + 1, var5 + 15 + 19 * var13, 16 * var13, 223, 16, 16);
               var20.drawSplitString(var17, var15, var5 + 16 + 19 * var13, var16, var21);
               var21 = 8453920;
            }

            var20 = Minecraft.fontRendererObj;
            var20.drawStringWithShadow(var19, (float)(var15 + 86 - var20.getStringWidth(var19)), (float)(var5 + 16 + 19 * var13 + 7), var21);
         }
      }

   }

   protected void drawGuiContainerForegroundLayer(int var1, int var2) {
      this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 12.0D, 5.0D, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0D, (double)(this.ySize - 96 + 2), 4210752);
   }

   public void updateScreen() {
      super.updateScreen();
      this.func_147068_g();
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      boolean var4 = Minecraft.thePlayer.capabilities.isCreativeMode;
      int var5 = this.container.getLapisAmount();

      for(int var6 = 0; var6 < 3; ++var6) {
         int var7 = this.container.enchantLevels[var6];
         int var8 = this.container.field_178151_h[var6];
         int var9 = var6 + 1;
         if (this.isPointInRegion(60, 14 + 19 * var6, 108, 17, var1, var2) && var7 > 0 && var8 >= 0) {
            ArrayList var10 = Lists.newArrayList();
            String var11;
            if (var8 >= 0 && Enchantment.getEnchantmentById(var8 & 255) != null) {
               var11 = Enchantment.getEnchantmentById(var8 & 255).getTranslatedName((var8 & '\uff00') >> 8);
               var10.add(EnumChatFormatting.WHITE.toString() + EnumChatFormatting.ITALIC.toString() + I18n.format("container.enchant.clue", var11));
            }

            if (!var4) {
               if (var8 >= 0) {
                  var10.add("");
               }

               if (Minecraft.thePlayer.experienceLevel < var7) {
                  var10.add(EnumChatFormatting.RED.toString() + "Level Requirement: " + this.container.enchantLevels[var6]);
               } else {
                  var11 = "";
                  if (var9 == 1) {
                     var11 = I18n.format("container.enchant.lapis.one");
                  } else {
                     var11 = I18n.format("container.enchant.lapis.many", var9);
                  }

                  if (var5 >= var9) {
                     var10.add(EnumChatFormatting.GRAY.toString() + var11);
                  } else {
                     var10.add(EnumChatFormatting.RED.toString() + var11);
                  }

                  if (var9 == 1) {
                     var11 = I18n.format("container.enchant.level.one");
                  } else {
                     var11 = I18n.format("container.enchant.level.many", var9);
                  }

                  var10.add(EnumChatFormatting.GRAY.toString() + var11);
               }
            }

            this.drawHoveringText(var10, var1, var2);
            break;
         }
      }

   }

   public void func_147068_g() {
      ItemStack var1 = this.inventorySlots.getSlot(0).getStack();
      if (!ItemStack.areItemStacksEqual(var1, this.field_147077_B)) {
         this.field_147077_B = var1;

         do {
            this.field_147082_x += (float)(this.random.nextInt(4) - this.random.nextInt(4));
         } while(!(this.field_147071_v > this.field_147082_x + 1.0F) && !(this.field_147071_v < this.field_147082_x - 1.0F));
      }

      ++this.field_147073_u;
      this.field_147069_w = this.field_147071_v;
      this.field_147076_A = this.field_147080_z;
      boolean var2 = false;

      for(int var3 = 0; var3 < 3; ++var3) {
         if (this.container.enchantLevels[var3] != 0) {
            var2 = true;
         }
      }

      if (var2) {
         this.field_147080_z += 0.2F;
      } else {
         this.field_147080_z -= 0.2F;
      }

      this.field_147080_z = MathHelper.clamp_float(this.field_147080_z, 0.0F, 1.0F);
      float var5 = (this.field_147082_x - this.field_147071_v) * 0.4F;
      float var4 = 0.2F;
      var5 = MathHelper.clamp_float(var5, -var4, var4);
      this.field_147081_y += (var5 - this.field_147081_y) * 0.9F;
      this.field_147071_v += this.field_147081_y;
   }
}
