package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;

public class NBTTagShort extends NBTBase.NBTPrimitive {
   private short data;

   public NBTTagShort() {
   }

   public NBTTagShort(short data) {
      this.data = data;
   }

   public boolean equals(Object p_equals_1_) {
      if(super.equals(p_equals_1_)) {
         NBTTagShort nbttagshort = (NBTTagShort)p_equals_1_;
         return this.data == nbttagshort.data;
      } else {
         return false;
      }
   }

   public String toString() {
      return "" + this.data + "s";
   }

   public int hashCode() {
      return super.hashCode() ^ this.data;
   }

   public byte getByte() {
      return (byte)(this.data & 255);
   }

   public short getShort() {
      return this.data;
   }

   public int getInt() {
      return this.data;
   }

   public long getLong() {
      return (long)this.data;
   }

   public float getFloat() {
      return (float)this.data;
   }

   public double getDouble() {
      return (double)this.data;
   }

   void write(DataOutput output) throws IOException {
      output.writeShort(this.data);
   }

   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
      sizeTracker.read(80L);
      this.data = input.readShort();
   }

   public byte getId() {
      return (byte)2;
   }

   public NBTBase copy() {
      return new NBTTagShort(this.data);
   }
}
