// 
// Decompiled by Procyon v0.6.0
// 

package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import java.io.IOException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.Packet;

public class S14PacketEntity implements Packet<INetHandlerPlayClient>
{
    public int entityId;
    public byte posX;
    public byte posY;
    public byte posZ;
    public byte yaw;
    public byte pitch;
    public boolean onGround;
    protected boolean field_149069_g;
    
    public S14PacketEntity() {
    }
    
    public S14PacketEntity(final int entityIdIn) {
        this.entityId = entityIdIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.entityId = buf.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.entityId);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleEntityMovement(this);
    }
    
    @Override
    public String toString() {
        return "Entity_" + super.toString();
    }
    
    public Entity getEntity(final World worldIn) {
        return worldIn.getEntityByID(this.entityId);
    }
    
    public byte func_149062_c() {
        return this.posX;
    }
    
    public byte func_149061_d() {
        return this.posY;
    }
    
    public byte func_149064_e() {
        return this.posZ;
    }
    
    public byte func_149066_f() {
        return this.yaw;
    }
    
    public byte func_149063_g() {
        return this.pitch;
    }
    
    public boolean func_149060_h() {
        return this.field_149069_g;
    }
    
    public boolean getOnGround() {
        return this.onGround;
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public void setPosX(final int posX) {
        this.posX = (byte)posX;
    }
    
    public void setPosY(final int posY) {
        this.posY = (byte)posY;
    }
    
    public void setPosZ(final int posZ) {
        this.posZ = (byte)posZ;
    }
    
    public void setEntityId(final int entityId) {
        this.entityId = entityId;
    }
    
    public byte getYaw() {
        return this.yaw;
    }
    
    public byte getPitch() {
        return this.pitch;
    }
    
    public static class S15PacketEntityRelMove extends S14PacketEntity
    {
        public S15PacketEntityRelMove() {
        }
        
        public S15PacketEntityRelMove(final int entityIdIn, final byte x, final byte y, final byte z, final boolean onGroundIn) {
            super(entityIdIn);
            this.posX = x;
            this.posY = y;
            this.posZ = z;
            this.onGround = onGroundIn;
        }
        
        @Override
        public void readPacketData(final PacketBuffer buf) throws IOException {
            super.readPacketData(buf);
            this.posX = buf.readByte();
            this.posY = buf.readByte();
            this.posZ = buf.readByte();
            this.onGround = buf.readBoolean();
        }
        
        @Override
        public void writePacketData(final PacketBuffer buf) throws IOException {
            super.writePacketData(buf);
            buf.writeByte(this.posX);
            buf.writeByte(this.posY);
            buf.writeByte(this.posZ);
            buf.writeBoolean(this.onGround);
        }
    }
    
    public static class S16PacketEntityLook extends S14PacketEntity
    {
        public S16PacketEntityLook() {
            this.field_149069_g = true;
        }
        
        public S16PacketEntityLook(final int entityIdIn, final byte yawIn, final byte pitchIn, final boolean onGroundIn) {
            super(entityIdIn);
            this.yaw = yawIn;
            this.pitch = pitchIn;
            this.field_149069_g = true;
            this.onGround = onGroundIn;
        }
        
        @Override
        public void readPacketData(final PacketBuffer buf) throws IOException {
            super.readPacketData(buf);
            this.yaw = buf.readByte();
            this.pitch = buf.readByte();
            this.onGround = buf.readBoolean();
        }
        
        @Override
        public void writePacketData(final PacketBuffer buf) throws IOException {
            super.writePacketData(buf);
            buf.writeByte(this.yaw);
            buf.writeByte(this.pitch);
            buf.writeBoolean(this.onGround);
        }
    }
    
    public static class S17PacketEntityLookMove extends S14PacketEntity
    {
        public S17PacketEntityLookMove() {
            this.field_149069_g = true;
        }
        
        public S17PacketEntityLookMove(final int p_i45973_1_, final byte p_i45973_2_, final byte p_i45973_3_, final byte p_i45973_4_, final byte p_i45973_5_, final byte p_i45973_6_, final boolean p_i45973_7_) {
            super(p_i45973_1_);
            this.posX = p_i45973_2_;
            this.posY = p_i45973_3_;
            this.posZ = p_i45973_4_;
            this.yaw = p_i45973_5_;
            this.pitch = p_i45973_6_;
            this.onGround = p_i45973_7_;
            this.field_149069_g = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer buf) throws IOException {
            super.readPacketData(buf);
            this.posX = buf.readByte();
            this.posY = buf.readByte();
            this.posZ = buf.readByte();
            this.yaw = buf.readByte();
            this.pitch = buf.readByte();
            this.onGround = buf.readBoolean();
        }
        
        @Override
        public void writePacketData(final PacketBuffer buf) throws IOException {
            super.writePacketData(buf);
            buf.writeByte(this.posX);
            buf.writeByte(this.posY);
            buf.writeByte(this.posZ);
            buf.writeByte(this.yaw);
            buf.writeByte(this.pitch);
            buf.writeBoolean(this.onGround);
        }
    }
}
