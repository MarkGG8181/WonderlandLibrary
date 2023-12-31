package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandHandler implements ICommandManager {
   private static final Logger logger = LogManager.getLogger();
   private final Map commandMap = Maps.newHashMap();
   private final Set commandSet = Sets.newHashSet();

   public List getPossibleCommands(ICommandSender sender) {
      List<ICommand> list = Lists.newArrayList();

      for(ICommand icommand : this.commandSet) {
         if(icommand.canCommandSenderUseCommand(sender)) {
            list.add(icommand);
         }
      }

      return list;
   }

   public List getTabCompletionOptions(ICommandSender sender, String input, BlockPos pos) {
      String[] astring = input.split(" ", -1);
      String s = astring[0];
      if(astring.length == 1) {
         List<String> list = Lists.newArrayList();

         for(Entry<String, ICommand> entry : this.commandMap.entrySet()) {
            if(CommandBase.doesStringStartWith(s, (String)entry.getKey()) && ((ICommand)entry.getValue()).canCommandSenderUseCommand(sender)) {
               list.add(entry.getKey());
            }
         }

         return list;
      } else {
         if(astring.length > 1) {
            ICommand icommand = (ICommand)this.commandMap.get(s);
            if(icommand != null && icommand.canCommandSenderUseCommand(sender)) {
               return icommand.addTabCompletionOptions(sender, dropFirstString(astring), pos);
            }
         }

         return null;
      }
   }

   protected boolean tryExecute(ICommandSender sender, String[] args, ICommand command, String input) {
      try {
         command.processCommand(sender, args);
         return true;
      } catch (WrongUsageException var7) {
         ChatComponentTranslation chatcomponenttranslation2 = new ChatComponentTranslation("commands.generic.usage", new Object[]{new ChatComponentTranslation(var7.getMessage(), var7.getErrorObjects())});
         chatcomponenttranslation2.getChatStyle().setColor(EnumChatFormatting.RED);
         sender.addChatMessage(chatcomponenttranslation2);
      } catch (CommandException var8) {
         ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation(var8.getMessage(), var8.getErrorObjects());
         chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.RED);
         sender.addChatMessage(chatcomponenttranslation1);
      } catch (Throwable var9) {
         ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
         chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
         sender.addChatMessage(chatcomponenttranslation);
         logger.warn("Couldn\'t process command: \'" + input + "\'");
      }

      return false;
   }

   private int getUsernameIndex(ICommand command, String[] args) {
      if(command == null) {
         return -1;
      } else {
         for(int i = 0; i < args.length; ++i) {
            if(command.isUsernameIndex(args, i) && PlayerSelector.matchesMultiplePlayers(args[i])) {
               return i;
            }
         }

         return -1;
      }
   }

   private static String[] dropFirstString(String[] input) {
      String[] astring = new String[input.length - 1];
      System.arraycopy(input, 1, astring, 0, input.length - 1);
      return astring;
   }

   public ICommand registerCommand(ICommand command) {
      this.commandMap.put(command.getCommandName(), command);
      this.commandSet.add(command);

      for(String s : command.getCommandAliases()) {
         ICommand icommand = (ICommand)this.commandMap.get(s);
         if(icommand == null || !icommand.getCommandName().equals(s)) {
            this.commandMap.put(s, command);
         }
      }

      return command;
   }

   public Map getCommands() {
      return this.commandMap;
   }

   public int executeCommand(ICommandSender sender, String rawCommand) {
      rawCommand = rawCommand.trim();
      if(rawCommand.startsWith("/")) {
         rawCommand = rawCommand.substring(1);
      }

      String[] astring = rawCommand.split(" ");
      String s = astring[0];
      astring = dropFirstString(astring);
      ICommand icommand = (ICommand)this.commandMap.get(s);
      int i = this.getUsernameIndex(icommand, astring);
      int j = 0;
      if(icommand == null) {
         ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
         chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
         sender.addChatMessage(chatcomponenttranslation);
      } else if(icommand.canCommandSenderUseCommand(sender)) {
         if(i > -1) {
            List<Entity> list = PlayerSelector.matchEntities(sender, astring[i], Entity.class);
            String s1 = astring[i];
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());

            for(Entity entity : list) {
               astring[i] = entity.getUniqueID().toString();
               if(this.tryExecute(sender, astring, icommand, rawCommand)) {
                  ++j;
               }
            }

            astring[i] = s1;
         } else {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
            if(this.tryExecute(sender, astring, icommand, rawCommand)) {
               ++j;
            }
         }
      } else {
         ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
         chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.RED);
         sender.addChatMessage(chatcomponenttranslation1);
      }

      sender.setCommandStat(CommandResultStats.Type.SUCCESS_COUNT, j);
      return j;
   }
}
