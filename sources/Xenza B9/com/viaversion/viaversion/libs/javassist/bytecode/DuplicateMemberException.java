// 
// Decompiled by Procyon v0.6.0
// 

package com.viaversion.viaversion.libs.javassist.bytecode;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;

public class DuplicateMemberException extends CannotCompileException
{
    private static final long serialVersionUID = 1L;
    
    public DuplicateMemberException(final String msg) {
        super(msg);
    }
}
