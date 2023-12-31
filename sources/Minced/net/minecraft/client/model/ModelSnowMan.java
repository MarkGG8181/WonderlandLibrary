// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.model;

import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;

public class ModelSnowMan extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer bottomBody;
    public ModelRenderer head;
    public ModelRenderer rightHand;
    public ModelRenderer leftHand;
    
    public ModelSnowMan() {
        final float f = 4.0f;
        final float f2 = 0.0f;
        (this.head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, -0.5f);
        this.head.setRotationPoint(0.0f, 4.0f, 0.0f);
        (this.rightHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64)).addBox(-1.0f, 0.0f, -1.0f, 12, 2, 2, -0.5f);
        this.rightHand.setRotationPoint(0.0f, 6.0f, 0.0f);
        (this.leftHand = new ModelRenderer(this, 32, 0).setTextureSize(64, 64)).addBox(-1.0f, 0.0f, -1.0f, 12, 2, 2, -0.5f);
        this.leftHand.setRotationPoint(0.0f, 6.0f, 0.0f);
        (this.body = new ModelRenderer(this, 0, 16).setTextureSize(64, 64)).addBox(-5.0f, -10.0f, -5.0f, 10, 10, 10, -0.5f);
        this.body.setRotationPoint(0.0f, 13.0f, 0.0f);
        (this.bottomBody = new ModelRenderer(this, 0, 36).setTextureSize(64, 64)).addBox(-6.0f, -12.0f, -6.0f, 12, 12, 12, -0.5f);
        this.bottomBody.setRotationPoint(0.0f, 24.0f, 0.0f);
    }
    
    @Override
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleY = netHeadYaw * 0.017453292f;
        this.head.rotateAngleX = headPitch * 0.017453292f;
        this.body.rotateAngleY = netHeadYaw * 0.017453292f * 0.25f;
        final float f = MathHelper.sin(this.body.rotateAngleY);
        final float f2 = MathHelper.cos(this.body.rotateAngleY);
        this.rightHand.rotateAngleZ = 1.0f;
        this.leftHand.rotateAngleZ = -1.0f;
        this.rightHand.rotateAngleY = 0.0f + this.body.rotateAngleY;
        this.leftHand.rotateAngleY = 3.1415927f + this.body.rotateAngleY;
        this.rightHand.rotationPointX = f2 * 5.0f;
        this.rightHand.rotationPointZ = -f * 5.0f;
        this.leftHand.rotationPointX = -f2 * 5.0f;
        this.leftHand.rotationPointZ = f * 5.0f;
    }
    
    @Override
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.body.render(scale);
        this.bottomBody.render(scale);
        this.head.render(scale);
        this.rightHand.render(scale);
        this.leftHand.render(scale);
    }
}
