// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.expr;

public interface IExpressionFloatArray extends IExpression
{
    float[] eval();
    
    default ExpressionType getExpressionType() {
        return ExpressionType.FLOAT_ARRAY;
    }
}
