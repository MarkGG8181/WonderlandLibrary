// 
// Decompiled by Procyon v0.6.0
// 

package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType;

@Deprecated
public class Metadata1_18Type extends ModernMetaType
{
    @Override
    protected MetaType getType(final int index) {
        return Types1_18.META_TYPES.byId(index);
    }
}
