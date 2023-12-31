package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentStyle.1;
import net.minecraft.util.ChatComponentStyle.2;

public abstract class ChatComponentStyle implements IChatComponent {
   protected List siblings = Lists.newArrayList();
   private ChatStyle style;

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(!(p_equals_1_ instanceof ChatComponentStyle)) {
         return false;
      } else {
         ChatComponentStyle chatcomponentstyle = (ChatComponentStyle)p_equals_1_;
         return this.siblings.equals(chatcomponentstyle.siblings) && this.getChatStyle().equals(chatcomponentstyle.getChatStyle());
      }
   }

   public String toString() {
      return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
   }

   public int hashCode() {
      return 31 * this.style.hashCode() + this.siblings.hashCode();
   }

   public Iterator iterator() {
      return Iterators.concat(Iterators.forArray(new ChatComponentStyle[]{this}), createDeepCopyIterator(this.siblings));
   }

   public List getSiblings() {
      return this.siblings;
   }

   public IChatComponent appendText(String text) {
      return this.appendSibling(new ChatComponentText(text));
   }

   public IChatComponent setChatStyle(ChatStyle style) {
      this.style = style;

      for(IChatComponent ichatcomponent : this.siblings) {
         ichatcomponent.getChatStyle().setParentStyle(this.getChatStyle());
      }

      return this;
   }

   public IChatComponent appendSibling(IChatComponent component) {
      component.getChatStyle().setParentStyle(this.getChatStyle());
      this.siblings.add(component);
      return this;
   }

   public final String getUnformattedText() {
      StringBuilder stringbuilder = new StringBuilder();

      for(IChatComponent ichatcomponent : this) {
         stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
      }

      return stringbuilder.toString();
   }

   public ChatStyle getChatStyle() {
      if(this.style == null) {
         this.style = new ChatStyle();

         for(IChatComponent ichatcomponent : this.siblings) {
            ichatcomponent.getChatStyle().setParentStyle(this.style);
         }
      }

      return this.style;
   }

   public final String getFormattedText() {
      StringBuilder stringbuilder = new StringBuilder();

      for(IChatComponent ichatcomponent : this) {
         stringbuilder.append(ichatcomponent.getChatStyle().getFormattingCode());
         stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
         stringbuilder.append(EnumChatFormatting.RESET);
      }

      return stringbuilder.toString();
   }

   public static Iterator createDeepCopyIterator(Iterable components) {
      Iterator<IChatComponent> iterator = Iterators.concat(Iterators.transform(components.iterator(), new 1()));
      iterator = Iterators.transform(iterator, new 2());
      return iterator;
   }
}
