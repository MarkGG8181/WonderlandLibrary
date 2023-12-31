package net.minecraft.src;

import java.lang.reflect.Field;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;

public class ReflectorField {
   private ReflectorClass reflectorClass = null;
   private String targetFieldName = null;
   private boolean checked = false;
   private Field targetField = null;

   public ReflectorField(ReflectorClass p_i78_1_, String p_i78_2_) {
      this.reflectorClass = p_i78_1_;
      this.targetFieldName = p_i78_2_;
      Field field = this.getTargetField();
   }

   public Object getValue() {
      return Reflector.getFieldValue((Object)null, this);
   }

   public void setValue(Object p_setValue_1_) {
      Reflector.setFieldValue((Object)null, this, p_setValue_1_);
   }

   public boolean exists() {
      return this.checked?this.targetField != null:this.getTargetField() != null;
   }

   public Field getTargetField() {
      if(this.checked) {
         return this.targetField;
      } else {
         this.checked = true;
         Class oclass = this.reflectorClass.getTargetClass();
         if(oclass == null) {
            return null;
         } else {
            try {
               this.targetField = oclass.getDeclaredField(this.targetFieldName);
               this.targetField.setAccessible(true);
            } catch (NoSuchFieldException var3) {
               Config.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
            } catch (SecurityException var4) {
               var4.printStackTrace();
            } catch (Throwable var5) {
               var5.printStackTrace();
            }

            return this.targetField;
         }
      }
   }
}
