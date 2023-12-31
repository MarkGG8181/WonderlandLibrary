package net.minecraft.block.properties;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

public class PropertyDirection extends PropertyEnum {
   protected PropertyDirection(String name, Collection values) {
      super(name, EnumFacing.class, values);
   }

   public static PropertyDirection create(String name, Collection values) {
      return new PropertyDirection(name, values);
   }

   public static PropertyDirection create(String name, Predicate filter) {
      return create(name, Collections2.filter(Lists.newArrayList(EnumFacing.values()), filter));
   }

   public static PropertyDirection create(String name) {
      return create(name, Predicates.alwaysTrue());
   }
}
