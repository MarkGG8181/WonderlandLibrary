// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelEnderman extends ModelBiped
{
    public boolean isCarrying;
    public boolean isAttacking;
    
    public ModelEnderman(final float scale) {
        super(0.0f, -14.0f, 64, 32);
        final float f = -14.0f;
        (this.bipedHeadwear = new ModelRenderer(this, 0, 16)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, scale - 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, -14.0f, 0.0f);
        (this.bipedBody = new ModelRenderer(this, 32, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, scale);
        this.bipedBody.setRotationPoint(0.0f, -14.0f, 0.0f);
        (this.bipedRightArm = new ModelRenderer(this, 56, 0)).addBox(-1.0f, -2.0f, -1.0f, 2, 30, 2, scale);
        this.bipedRightArm.setRotationPoint(-3.0f, -12.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer(this, 56, 0);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 30, 2, scale);
        this.bipedLeftArm.setRotationPoint(5.0f, -12.0f, 0.0f);
        (this.bipedRightLeg = new ModelRenderer(this, 56, 0)).addBox(-1.0f, 0.0f, -1.0f, 2, 30, 2, scale);
        this.bipedRightLeg.setRotationPoint(-2.0f, -2.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 30, 2, scale);
        this.bipedLeftLeg.setRotationPoint(2.0f, -2.0f, 0.0f);
    }
    
    @Override
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.bipedHead.showModel = true;
        final float f = -14.0f;
        this.bipedBody.rotateAngleX = 0.0f;
        this.bipedBody.rotationPointY = -14.0f;
        this.bipedBody.rotationPointZ = -0.0f;
        final ModelRenderer bipedRightLeg = this.bipedRightLeg;
        bipedRightLeg.rotateAngleX -= 0.0f;
        final ModelRenderer bipedLeftLeg = this.bipedLeftLeg;
        bipedLeftLeg.rotateAngleX -= 0.0f;
        this.bipedRightArm.rotateAngleX *= 0.5;
        this.bipedLeftArm.rotateAngleX *= 0.5;
        this.bipedRightLeg.rotateAngleX *= 0.5;
        this.bipedLeftLeg.rotateAngleX *= 0.5;
        final float f2 = 0.4f;
        if (this.bipedRightArm.rotateAngleX > 0.4f) {
            this.bipedRightArm.rotateAngleX = 0.4f;
        }
        if (this.bipedLeftArm.rotateAngleX > 0.4f) {
            this.bipedLeftArm.rotateAngleX = 0.4f;
        }
        if (this.bipedRightArm.rotateAngleX < -0.4f) {
            this.bipedRightArm.rotateAngleX = -0.4f;
        }
        if (this.bipedLeftArm.rotateAngleX < -0.4f) {
            this.bipedLeftArm.rotateAngleX = -0.4f;
        }
        if (this.bipedRightLeg.rotateAngleX > 0.4f) {
            this.bipedRightLeg.rotateAngleX = 0.4f;
        }
        if (this.bipedLeftLeg.rotateAngleX > 0.4f) {
            this.bipedLeftLeg.rotateAngleX = 0.4f;
        }
        if (this.bipedRightLeg.rotateAngleX < -0.4f) {
            this.bipedRightLeg.rotateAngleX = -0.4f;
        }
        if (this.bipedLeftLeg.rotateAngleX < -0.4f) {
            this.bipedLeftLeg.rotateAngleX = -0.4f;
        }
        if (this.isCarrying) {
            this.bipedRightArm.rotateAngleX = -0.5f;
            this.bipedLeftArm.rotateAngleX = -0.5f;
            this.bipedRightArm.rotateAngleZ = 0.05f;
            this.bipedLeftArm.rotateAngleZ = -0.05f;
        }
        this.bipedRightArm.rotationPointZ = 0.0f;
        this.bipedLeftArm.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointZ = 0.0f;
        this.bipedLeftLeg.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointY = -5.0f;
        this.bipedLeftLeg.rotationPointY = -5.0f;
        this.bipedHead.rotationPointZ = -0.0f;
        this.bipedHead.rotationPointY = -13.0f;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
        if (this.isAttacking) {
            final float f3 = 1.0f;
            final ModelRenderer bipedHead = this.bipedHead;
            bipedHead.rotationPointY -= 5.0f;
        }
    }
}
