package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBook extends GuiScreen {
   private boolean bookGettingSigned;
   private GuiButton buttonFinalize;
   private boolean bookIsModified;
   private final boolean bookIsUnsigned;
   private int field_175387_B = -1;
   private int currPage;
   private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
   private int bookImageHeight = 192;
   private List field_175386_A;
   private GuiButton buttonDone;
   private GuiButton buttonSign;
   private int bookTotalPages = 1;
   private final EntityPlayer editingPlayer;
   private final ItemStack bookObj;
   private int bookImageWidth = 192;
   private NBTTagList bookPages;
   private GuiScreenBook.NextPageButton buttonNextPage;
   private GuiScreenBook.NextPageButton buttonPreviousPage;
   private GuiButton buttonCancel;
   private int updateCount;
   private static final Logger logger = LogManager.getLogger();
   private String bookTitle = "";

   protected void keyTyped(char var1, int var2) throws IOException {
      super.keyTyped(var1, var2);
      if (this.bookIsUnsigned) {
         if (this.bookGettingSigned) {
            this.keyTypedInTitle(var1, var2);
         } else {
            this.keyTypedInBook(var1, var2);
         }
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.enabled) {
         if (var1.id == 0) {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.sendBookToServer(false);
         } else if (var1.id == 3 && this.bookIsUnsigned) {
            this.bookGettingSigned = true;
         } else if (var1.id == 1) {
            if (this.currPage < this.bookTotalPages - 1) {
               ++this.currPage;
            } else if (this.bookIsUnsigned) {
               this.addNewPage();
               if (this.currPage < this.bookTotalPages - 1) {
                  ++this.currPage;
               }
            }
         } else if (var1.id == 2) {
            if (this.currPage > 0) {
               --this.currPage;
            }
         } else if (var1.id == 5 && this.bookGettingSigned) {
            this.sendBookToServer(true);
            this.mc.displayGuiScreen((GuiScreen)null);
         } else if (var1.id == 4 && this.bookGettingSigned) {
            this.bookGettingSigned = false;
         }

         this.updateButtons();
      }

   }

   private void keyTypedInBook(char var1, int var2) {
      if (GuiScreen.isKeyComboCtrlV(var2)) {
         this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
      } else {
         switch(var2) {
         case 14:
            String var3 = this.pageGetCurrent();
            if (var3.length() > 0) {
               this.pageSetCurrent(var3.substring(0, var3.length() - 1));
            }

            return;
         case 28:
         case 156:
            this.pageInsertIntoCurrent("\n");
            return;
         default:
            if (ChatAllowedCharacters.isAllowedCharacter(var1)) {
               this.pageInsertIntoCurrent(Character.toString(var1));
            }
         }
      }

   }

   public GuiScreenBook(EntityPlayer var1, ItemStack var2, boolean var3) {
      this.editingPlayer = var1;
      this.bookObj = var2;
      this.bookIsUnsigned = var3;
      if (var2.hasTagCompound()) {
         NBTTagCompound var4 = var2.getTagCompound();
         this.bookPages = var4.getTagList("pages", 8);
         if (this.bookPages != null) {
            this.bookPages = (NBTTagList)this.bookPages.copy();
            this.bookTotalPages = this.bookPages.tagCount();
            if (this.bookTotalPages < 1) {
               this.bookTotalPages = 1;
            }
         }
      }

      if (this.bookPages == null && var3) {
         this.bookPages = new NBTTagList();
         this.bookPages.appendTag(new NBTTagString(""));
         this.bookTotalPages = 1;
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(bookGuiTextures);
      int var4 = (width - this.bookImageWidth) / 2;
      byte var5 = 2;
      drawTexturedModalRect(var4, var5, 0, 0, this.bookImageWidth, this.bookImageHeight);
      String var6;
      String var7;
      int var8;
      int var9;
      if (this.bookGettingSigned) {
         var6 = this.bookTitle;
         if (this.bookIsUnsigned) {
            if (this.updateCount / 6 % 2 == 0) {
               var6 = var6 + EnumChatFormatting.BLACK + "_";
            } else {
               var6 = var6 + EnumChatFormatting.GRAY + "_";
            }
         }

         var7 = I18n.format("book.editTitle");
         var8 = this.fontRendererObj.getStringWidth(var7);
         this.fontRendererObj.drawString(var7, (double)(var4 + 36 + (116 - var8) / 2), (double)(var5 + 16 + 16), 0);
         var9 = this.fontRendererObj.getStringWidth(var6);
         this.fontRendererObj.drawString(var6, (double)(var4 + 36 + (116 - var9) / 2), (double)(var5 + 48), 0);
         String var10 = I18n.format("book.byAuthor", this.editingPlayer.getName());
         int var11 = this.fontRendererObj.getStringWidth(var10);
         this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + var10, (double)(var4 + 36 + (116 - var11) / 2), (double)(var5 + 48 + 10), 0);
         String var12 = I18n.format("book.finalizeWarning");
         this.fontRendererObj.drawSplitString(var12, var4 + 36, var5 + 80, 116, 0);
      } else {
         var6 = I18n.format("book.pageIndicator", this.currPage + 1, this.bookTotalPages);
         var7 = "";
         if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            var7 = this.bookPages.getStringTagAt(this.currPage);
         }

         if (this.bookIsUnsigned) {
            if (this.fontRendererObj.getBidiFlag()) {
               var7 = var7 + "_";
            } else if (this.updateCount / 6 % 2 == 0) {
               var7 = var7 + EnumChatFormatting.BLACK + "_";
            } else {
               var7 = var7 + EnumChatFormatting.GRAY + "_";
            }
         } else if (this.field_175387_B != this.currPage) {
            if (ItemEditableBook.validBookTagContents(this.bookObj.getTagCompound())) {
               try {
                  IChatComponent var14 = IChatComponent.Serializer.jsonToComponent(var7);
                  this.field_175386_A = var14 != null ? GuiUtilRenderComponents.func_178908_a(var14, 116, this.fontRendererObj, true, true) : null;
               } catch (JsonParseException var13) {
                  this.field_175386_A = null;
               }
            } else {
               ChatComponentText var15 = new ChatComponentText(EnumChatFormatting.DARK_RED.toString() + "* Invalid book tag *");
               this.field_175386_A = Lists.newArrayList((Iterable)var15);
            }

            this.field_175387_B = this.currPage;
         }

         var8 = this.fontRendererObj.getStringWidth(var6);
         this.fontRendererObj.drawString(var6, (double)(var4 - var8 + this.bookImageWidth - 44), (double)(var5 + 16), 0);
         if (this.field_175386_A == null) {
            this.fontRendererObj.drawSplitString(var7, var4 + 36, var5 + 16 + 16, 116, 0);
         } else {
            var9 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());

            for(int var16 = 0; var16 < var9; ++var16) {
               IChatComponent var18 = (IChatComponent)this.field_175386_A.get(var16);
               this.fontRendererObj.drawString(var18.getUnformattedText(), (double)(var4 + 36), (double)(var5 + 16 + 16 + var16 * this.fontRendererObj.FONT_HEIGHT), 0);
            }

            IChatComponent var17 = this.func_175385_b(var1, var2);
            if (var17 != null) {
               this.handleComponentHover(var17, var1, var2);
            }
         }
      }

      super.drawScreen(var1, var2, var3);
   }

   private void pageSetCurrent(String var1) {
      if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
         this.bookPages.set(this.currPage, new NBTTagString(var1));
         this.bookIsModified = true;
      }

   }

   public void initGui() {
      this.buttonList.clear();
      Keyboard.enableRepeatEvents(true);
      if (this.bookIsUnsigned) {
         this.buttonList.add(this.buttonSign = new GuiButton(3, width / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.format("book.signButton")));
         this.buttonList.add(this.buttonDone = new GuiButton(0, width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n.format("gui.done")));
         this.buttonList.add(this.buttonFinalize = new GuiButton(5, width / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.format("book.finalizeButton")));
         this.buttonList.add(this.buttonCancel = new GuiButton(4, width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n.format("gui.cancel")));
      } else {
         this.buttonList.add(this.buttonDone = new GuiButton(0, width / 2 - 100, 4 + this.bookImageHeight, 200, 20, I18n.format("gui.done")));
      }

      int var1 = (width - this.bookImageWidth) / 2;
      byte var2 = 2;
      this.buttonList.add(this.buttonNextPage = new GuiScreenBook.NextPageButton(1, var1 + 120, var2 + 154, true));
      this.buttonList.add(this.buttonPreviousPage = new GuiScreenBook.NextPageButton(2, var1 + 38, var2 + 154, false));
      this.updateButtons();
   }

   private String pageGetCurrent() {
      return this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount() ? this.bookPages.getStringTagAt(this.currPage) : "";
   }

   private void keyTypedInTitle(char var1, int var2) throws IOException {
      switch(var2) {
      case 14:
         if (!this.bookTitle.isEmpty()) {
            this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
            this.updateButtons();
         }

         return;
      case 28:
      case 156:
         if (!this.bookTitle.isEmpty()) {
            this.sendBookToServer(true);
            this.mc.displayGuiScreen((GuiScreen)null);
         }

         return;
      default:
         if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(var1)) {
            this.bookTitle = this.bookTitle + Character.toString(var1);
            this.updateButtons();
            this.bookIsModified = true;
         }

      }
   }

   private void sendBookToServer(boolean var1) throws IOException {
      if (this.bookIsUnsigned && this.bookIsModified && this.bookPages != null) {
         String var2;
         while(this.bookPages.tagCount() > 1) {
            var2 = this.bookPages.getStringTagAt(this.bookPages.tagCount() - 1);
            if (var2.length() != 0) {
               break;
            }

            this.bookPages.removeTag(this.bookPages.tagCount() - 1);
         }

         if (this.bookObj.hasTagCompound()) {
            NBTTagCompound var6 = this.bookObj.getTagCompound();
            var6.setTag("pages", this.bookPages);
         } else {
            this.bookObj.setTagInfo("pages", this.bookPages);
         }

         var2 = "MC|BEdit";
         if (var1) {
            var2 = "MC|BSign";
            this.bookObj.setTagInfo("author", new NBTTagString(this.editingPlayer.getName()));
            this.bookObj.setTagInfo("title", new NBTTagString(this.bookTitle.trim()));

            for(int var3 = 0; var3 < this.bookPages.tagCount(); ++var3) {
               String var4 = this.bookPages.getStringTagAt(var3);
               ChatComponentText var5 = new ChatComponentText(var4);
               var4 = IChatComponent.Serializer.componentToJson(var5);
               this.bookPages.set(var3, new NBTTagString(var4));
            }

            this.bookObj.setItem(Items.written_book);
         }

         PacketBuffer var7 = new PacketBuffer(Unpooled.buffer());
         var7.writeItemStackToBuffer(this.bookObj);
         this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var7));
      }

   }

   private void pageInsertIntoCurrent(String var1) {
      String var2 = this.pageGetCurrent();
      String var3 = var2 + var1;
      int var4 = this.fontRendererObj.splitStringWidth(var3 + EnumChatFormatting.BLACK + "_", 118);
      if (var4 <= 128 && var3.length() < 256) {
         this.pageSetCurrent(var3);
      }

   }

   static ResourceLocation access$0() {
      return bookGuiTextures;
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public IChatComponent func_175385_b(int var1, int var2) {
      if (this.field_175386_A == null) {
         return null;
      } else {
         int var3 = var1 - (width - this.bookImageWidth) / 2 - 36;
         int var4 = var2 - 2 - 16 - 16;
         if (var3 >= 0 && var4 >= 0) {
            int var5 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
            if (var3 <= 116 && var4 < Minecraft.fontRendererObj.FONT_HEIGHT * var5 + var5) {
               int var6 = var4 / Minecraft.fontRendererObj.FONT_HEIGHT;
               if (var6 >= 0 && var6 < this.field_175386_A.size()) {
                  IChatComponent var7 = (IChatComponent)this.field_175386_A.get(var6);
                  int var8 = 0;
                  Iterator var10 = var7.iterator();

                  while(var10.hasNext()) {
                     IChatComponent var9 = (IChatComponent)var10.next();
                     if (var9 instanceof ChatComponentText) {
                        var8 += Minecraft.fontRendererObj.getStringWidth(((ChatComponentText)var9).getChatComponentText_TextValue());
                        if (var8 > var3) {
                           return var9;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         IChatComponent var4 = this.func_175385_b(var1, var2);
         if (var4 == null) {
            return;
         }
      }

      super.mouseClicked(var1, var2, var3);
   }

   public void updateScreen() {
      super.updateScreen();
      ++this.updateCount;
   }

   private void updateButtons() {
      this.buttonNextPage.visible = !this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned);
      this.buttonPreviousPage.visible = !this.bookGettingSigned && this.currPage > 0;
      this.buttonDone.visible = !this.bookIsUnsigned || !this.bookGettingSigned;
      if (this.bookIsUnsigned) {
         this.buttonSign.visible = !this.bookGettingSigned;
         this.buttonCancel.visible = this.bookGettingSigned;
         this.buttonFinalize.visible = this.bookGettingSigned;
         this.buttonFinalize.enabled = this.bookTitle.trim().length() > 0;
      }

   }

   private void addNewPage() {
      if (this.bookPages != null && this.bookPages.tagCount() < 50) {
         this.bookPages.appendTag(new NBTTagString(""));
         ++this.bookTotalPages;
         this.bookIsModified = true;
      }

   }

   static class NextPageButton extends GuiButton {
      private final boolean field_146151_o;

      public NextPageButton(int var1, int var2, int var3, boolean var4) {
         super(var1, var2, var3, 23, 13, "");
         this.field_146151_o = var4;
      }

      public void drawButton(Minecraft var1, int var2, int var3) {
         if (this.visible) {
            boolean var4 = var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            var1.getTextureManager().bindTexture(GuiScreenBook.access$0());
            int var5 = 0;
            int var6 = 192;
            if (var4) {
               var5 += 23;
            }

            if (!this.field_146151_o) {
               var6 += 13;
            }

            drawTexturedModalRect(this.xPosition, this.yPosition, var5, var6, 23, 13);
         }

      }
   }
}
