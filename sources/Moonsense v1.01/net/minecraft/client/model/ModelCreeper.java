// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.model;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;

public class ModelCreeper extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer creeperArmor;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    private static final String __OBFID = "CL_00000837";
    
    public ModelCreeper() {
        this(0.0f);
    }
    
    public ModelCreeper(final float p_i46366_1_) {
        final byte var2 = 6;
        (this.head = new ModelRenderer(this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i46366_1_);
        this.head.setRotationPoint(0.0f, var2, 0.0f);
        (this.creeperArmor = new ModelRenderer(this, 32, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, p_i46366_1_ + 0.5f);
        this.creeperArmor.setRotationPoint(0.0f, var2, 0.0f);
        (this.body = new ModelRenderer(this, 16, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, p_i46366_1_);
        this.body.setRotationPoint(0.0f, var2, 0.0f);
        (this.leg1 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46366_1_);
        this.leg1.setRotationPoint(-2.0f, (float)(12 + var2), 4.0f);
        (this.leg2 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46366_1_);
        this.leg2.setRotationPoint(2.0f, (float)(12 + var2), 4.0f);
        (this.leg3 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46366_1_);
        this.leg3.setRotationPoint(-2.0f, (float)(12 + var2), -4.0f);
        (this.leg4 = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 0.0f, -2.0f, 4, 6, 4, p_i46366_1_);
        this.leg4.setRotationPoint(2.0f, (float)(12 + var2), -4.0f);
    }
    
    @Override
    public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.head.render(p_78088_7_);
        this.body.render(p_78088_7_);
        this.leg1.render(p_78088_7_);
        this.leg2.render(p_78088_7_);
        this.leg3.render(p_78088_7_);
        this.leg4.render(p_78088_7_);
    }
    
    @Override
    public void setRotationAngles(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        this.head.rotateAngleY = p_78087_4_ / 57.295776f;
        this.head.rotateAngleX = p_78087_5_ / 57.295776f;
        this.leg1.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662f) * 1.4f * p_78087_2_;
        this.leg2.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662f + 3.1415927f) * 1.4f * p_78087_2_;
        this.leg3.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662f + 3.1415927f) * 1.4f * p_78087_2_;
        this.leg4.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662f) * 1.4f * p_78087_2_;
    }
}
