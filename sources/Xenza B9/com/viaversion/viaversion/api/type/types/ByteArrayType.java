// 
// Decompiled by Procyon v0.6.0
// 

package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import com.viaversion.viaversion.api.type.Type;

public class ByteArrayType extends Type<byte[]>
{
    public ByteArrayType() {
        super(byte[].class);
    }
    
    @Override
    public void write(final ByteBuf buffer, final byte[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        buffer.writeBytes(object);
    }
    
    @Override
    public byte[] read(final ByteBuf buffer) throws Exception {
        final int length = Type.VAR_INT.readPrimitive(buffer);
        Preconditions.checkArgument(buffer.isReadable(length), (Object)"Length is fewer than readable bytes");
        final byte[] array = new byte[length];
        buffer.readBytes(array);
        return array;
    }
}
