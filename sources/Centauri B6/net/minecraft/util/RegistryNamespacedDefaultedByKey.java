package net.minecraft.util;

import net.minecraft.util.RegistryNamespaced;
import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced {
   private final Object defaultValueKey;
   private Object defaultValue;

   public RegistryNamespacedDefaultedByKey(Object p_i46017_1_) {
      this.defaultValueKey = p_i46017_1_;
   }

   public void register(int id, Object p_177775_2_, Object p_177775_3_) {
      if(this.defaultValueKey.equals(p_177775_2_)) {
         this.defaultValue = p_177775_3_;
      }

      super.register(id, p_177775_2_, p_177775_3_);
   }

   public Object getObject(Object name) {
      V v = super.getObject(name);
      return v == null?this.defaultValue:v;
   }

   public Object getObjectById(int id) {
      V v = super.getObjectById(id);
      return v == null?this.defaultValue:v;
   }

   public void validateKey() {
      Validate.notNull(this.defaultValueKey);
   }
}
