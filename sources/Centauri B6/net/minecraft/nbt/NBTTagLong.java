package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagLong extends NBTBase.NBTPrimitive {
   private long data;

   NBTTagLong() {
   }

   public NBTTagLong(long data) {
      this.data = data;
   }

   public boolean equals(Object p_equals_1_) {
      if(super.equals(p_equals_1_)) {
         NBTTagLong nbttaglong = (NBTTagLong)p_equals_1_;
         return this.data == nbttaglong.data;
      } else {
         return false;
      }
   }

   public String toString() {
      return "" + this.data + "L";
   }

   public int hashCode() {
      return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
   }

   public byte getByte() {
      return (byte)((int)(this.data & 255L));
   }

   public short getShort() {
      return (short)((int)(this.data & 65535L));
   }

   public int getInt() {
      return (int)(this.data & -1L);
   }

   public long getLong() {
      return this.data;
   }

   public float getFloat() {
      return (float)this.data;
   }

   public double getDouble() {
      return (double)this.data;
   }

   void write(DataOutput output) throws IOException {
      output.writeLong(this.data);
   }

   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
      sizeTracker.read(128L);
      this.data = input.readLong();
   }

   public byte getId() {
      return (byte)4;
   }

   public NBTBase copy() {
      return new NBTTagLong(this.data);
   }
}
