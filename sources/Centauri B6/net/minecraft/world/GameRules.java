package net.minecraft.world;

import java.util.Set;
import java.util.TreeMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.GameRules.Value;
import net.minecraft.world.GameRules.ValueType;

public class GameRules {
   private TreeMap theGameRules = new TreeMap();
   private static final String __OBFID = "CL_00000136";

   public GameRules() {
      this.addGameRule("doFireTick", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("mobGriefing", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("keepInventory", "false", ValueType.BOOLEAN_VALUE);
      this.addGameRule("doMobSpawning", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("doMobLoot", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("doTileDrops", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("doEntityDrops", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("commandBlockOutput", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("naturalRegeneration", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("doDaylightCycle", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("logAdminCommands", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("showDeathMessages", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("randomTickSpeed", "3", ValueType.NUMERICAL_VALUE);
      this.addGameRule("sendCommandFeedback", "true", ValueType.BOOLEAN_VALUE);
      this.addGameRule("reducedDebugInfo", "false", ValueType.BOOLEAN_VALUE);
   }

   public boolean getBoolean(String name) {
      Value gamerules$value = (Value)this.theGameRules.get(name);
      return gamerules$value != null?gamerules$value.getBoolean():false;
   }

   public int getInt(String name) {
      Value gamerules$value = (Value)this.theGameRules.get(name);
      return gamerules$value != null?gamerules$value.getInt():0;
   }

   public String getString(String name) {
      Value gamerules$value = (Value)this.theGameRules.get(name);
      return gamerules$value != null?gamerules$value.getString():"";
   }

   public NBTTagCompound writeToNBT() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();

      for(String s : this.theGameRules.keySet()) {
         Value gamerules$value = (Value)this.theGameRules.get(s);
         nbttagcompound.setString(s, gamerules$value.getString());
      }

      return nbttagcompound;
   }

   public void readFromNBT(NBTTagCompound nbt) {
      for(String s : nbt.getKeySet()) {
         String s1 = nbt.getString(s);
         this.setOrCreateGameRule(s, s1);
      }

   }

   public void setOrCreateGameRule(String key, String ruleValue) {
      Value gamerules$value = (Value)this.theGameRules.get(key);
      if(gamerules$value != null) {
         gamerules$value.setValue(ruleValue);
      } else {
         this.addGameRule(key, ruleValue, ValueType.ANY_VALUE);
      }

   }

   public String[] getRules() {
      Set set = this.theGameRules.keySet();
      return (String[])((String[])((String[])set.toArray(new String[set.size()])));
   }

   public boolean areSameType(String key, ValueType otherValue) {
      Value gamerules$value = (Value)this.theGameRules.get(key);
      return gamerules$value != null && (gamerules$value.getType() == otherValue || otherValue == ValueType.ANY_VALUE);
   }

   public void addGameRule(String key, String value, ValueType type) {
      this.theGameRules.put(key, new Value(value, type));
   }

   public boolean hasRule(String name) {
      return this.theGameRules.containsKey(name);
   }
}
