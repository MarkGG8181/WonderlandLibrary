package net.minecraft.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import net.minecraft.util.Cartesian.1;

public class Cartesian {
   private static Object[] toArray(Class clazz, Iterable it) {
      List<T> list = Lists.newArrayList();

      for(T t : it) {
         list.add(t);
      }

      return (Object[])((Object[])list.toArray(createArray(clazz, list.size())));
   }

   public static Iterable cartesianProduct(Iterable sets) {
      return arraysAsLists(cartesianProduct(Object.class, sets));
   }

   public static Iterable cartesianProduct(Class clazz, Iterable sets) {
      return new Cartesian.Product(clazz, (Iterable[])((Iterable[])toArray(Iterable.class, sets)), (1)null);
   }

   private static Object[] createArray(Class p_179319_0_, int p_179319_1_) {
      return (Object[])((Object[])((Object[])((Object[])Array.newInstance(p_179319_0_, p_179319_1_))));
   }

   private static Iterable arraysAsLists(Iterable arrays) {
      return Iterables.transform(arrays, new Cartesian.GetList((1)null));
   }

   static class GetList implements Function {
      // $FF: synthetic method
      GetList(1 x0) {
         this();
      }

      private GetList() {
      }

      public List apply(Object[] p_apply_1_) {
         return Arrays.asList((Object[])p_apply_1_);
      }
   }

   static class Product implements Iterable {
      private final Class clazz;
      private final Iterable[] iterables;

      private Product(Class clazz, Iterable[] iterables) {
         this.clazz = clazz;
         this.iterables = iterables;
      }

      // $FF: synthetic method
      Product(Class x0, Iterable[] x1, 1 x2) {
         this(x0, x1);
      }

      public Iterator iterator() {
         return (Iterator)(this.iterables.length <= 0?Collections.singletonList((Object[])Cartesian.createArray(this.clazz, 0)).iterator():new Cartesian.Product.ProductIterator(this.clazz, this.iterables, (1)null));
      }

      static class ProductIterator extends UnmodifiableIterator {
         private int index;
         private final Iterable[] iterables;
         private final Iterator[] iterators;
         private final Object[] results;

         // $FF: synthetic method
         ProductIterator(Class x0, Iterable[] x1, 1 x2) {
            this(x0, x1);
         }

         private ProductIterator(Class clazz, Iterable[] iterables) {
            this.index = -2;
            this.iterables = iterables;
            this.iterators = (Iterator[])((Iterator[])Cartesian.createArray(Iterator.class, this.iterables.length));

            for(int i = 0; i < this.iterables.length; ++i) {
               this.iterators[i] = iterables[i].iterator();
            }

            this.results = Cartesian.createArray(clazz, this.iterators.length);
         }

         public boolean hasNext() {
            if(this.index == -2) {
               this.index = 0;

               for(Iterator<? extends T> iterator1 : this.iterators) {
                  if(!iterator1.hasNext()) {
                     this.endOfData();
                     break;
                  }
               }

               return true;
            } else {
               if(this.index >= this.iterators.length) {
                  for(this.index = this.iterators.length - 1; this.index >= 0; --this.index) {
                     Iterator<? extends T> iterator = this.iterators[this.index];
                     if(iterator.hasNext()) {
                        break;
                     }

                     if(this.index == 0) {
                        this.endOfData();
                        break;
                     }

                     iterator = this.iterables[this.index].iterator();
                     this.iterators[this.index] = iterator;
                     if(!iterator.hasNext()) {
                        this.endOfData();
                        break;
                     }
                  }
               }

               return this.index >= 0;
            }
         }

         public Object[] next() {
            if(!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               while(this.index < this.iterators.length) {
                  this.results[this.index] = this.iterators[this.index].next();
                  ++this.index;
               }

               return (Object[])((Object[])((Object[])this.results.clone()));
            }
         }

         private void endOfData() {
            this.index = -1;
            Arrays.fill(this.iterators, (Object)null);
            Arrays.fill(this.results, (Object)null);
         }
      }
   }
}
