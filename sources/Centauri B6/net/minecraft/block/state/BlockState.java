package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

public class BlockState {
   private static final Joiner COMMA_JOINER = Joiner.on(", ");
   private static final Function GET_NAME_FUNC = new Function() {
      public String apply(IProperty p_apply_1_) {
         return p_apply_1_ == null?"<NULL>":p_apply_1_.getName();
      }
   };
   private final Block block;
   private final ImmutableList properties;
   private final ImmutableList validStates;

   public BlockState(Block blockIn, IProperty... properties) {
      this.block = blockIn;
      Arrays.sort(properties, new Comparator() {
         public int compare(IProperty p_compare_1_, IProperty p_compare_2_) {
            return p_compare_1_.getName().compareTo(p_compare_2_.getName());
         }
      });
      this.properties = ImmutableList.copyOf(properties);
      Map<Map<IProperty, Comparable>, BlockState.StateImplementation> map = Maps.newLinkedHashMap();
      List<BlockState.StateImplementation> list = Lists.newArrayList();

      for(List<Comparable> list1 : Cartesian.cartesianProduct(this.getAllowedValues())) {
         Map<IProperty, Comparable> map1 = MapPopulator.createMap(this.properties, list1);
         BlockState.StateImplementation blockstate$stateimplementation = new BlockState.StateImplementation(blockIn, ImmutableMap.copyOf(map1));
         map.put(map1, blockstate$stateimplementation);
         list.add(blockstate$stateimplementation);
      }

      for(BlockState.StateImplementation blockstate$stateimplementation1 : list) {
         blockstate$stateimplementation1.buildPropertyValueTable(map);
      }

      this.validStates = ImmutableList.copyOf(list);
   }

   public String toString() {
      return Objects.toStringHelper(this).add("block", Block.blockRegistry.getNameForObject(this.block)).add("properties", Iterables.transform(this.properties, GET_NAME_FUNC)).toString();
   }

   public Collection getProperties() {
      return this.properties;
   }

   public Block getBlock() {
      return this.block;
   }

   private List getAllowedValues() {
      List<Iterable<Comparable>> list = Lists.newArrayList();

      for(int i = 0; i < this.properties.size(); ++i) {
         list.add(((IProperty)this.properties.get(i)).getAllowedValues());
      }

      return list;
   }

   public ImmutableList getValidStates() {
      return this.validStates;
   }

   public IBlockState getBaseState() {
      return (IBlockState)this.validStates.get(0);
   }

   static class StateImplementation extends BlockStateBase {
      private final Block block;
      private final ImmutableMap properties;
      private ImmutableTable propertyValueTable;

      private StateImplementation(Block blockIn, ImmutableMap propertiesIn) {
         this.block = blockIn;
         this.properties = propertiesIn;
      }

      public boolean equals(Object p_equals_1_) {
         return this == p_equals_1_;
      }

      public int hashCode() {
         return this.properties.hashCode();
      }

      public Comparable getValue(IProperty property) {
         if(!this.properties.containsKey(property)) {
            throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
         } else {
            return (Comparable)property.getValueClass().cast(this.properties.get(property));
         }
      }

      public ImmutableMap getProperties() {
         return this.properties;
      }

      public Collection getPropertyNames() {
         return Collections.unmodifiableCollection(this.properties.keySet());
      }

      public IBlockState withProperty(IProperty property, Comparable value) {
         return (IBlockState)(!this.properties.containsKey(property)?this:(!property.getAllowedValues().contains(value)?this:(this.properties.get(property) == value?this:(IBlockState)this.propertyValueTable.get(property, value))));
      }

      public Block getBlock() {
         return this.block;
      }

      public void buildPropertyValueTable(Map map) {
         if(this.propertyValueTable != null) {
            throw new IllegalStateException();
         } else {
            Table<IProperty, Comparable, IBlockState> table = HashBasedTable.create();
            UnmodifiableIterator var3 = this.properties.keySet().iterator();

            while(var3.hasNext()) {
               IProperty<? extends Comparable> iproperty = (IProperty)var3.next();

               for(Comparable comparable : iproperty.getAllowedValues()) {
                  if(comparable != this.properties.get(iproperty)) {
                     table.put(iproperty, comparable, map.get(this.getPropertiesWithValue(iproperty, comparable)));
                  }
               }
            }

            this.propertyValueTable = ImmutableTable.copyOf(table);
         }
      }

      private Map getPropertiesWithValue(IProperty property, Comparable value) {
         Map<IProperty, Comparable> map = Maps.newHashMap(this.properties);
         map.put(property, value);
         return map;
      }
   }
}
