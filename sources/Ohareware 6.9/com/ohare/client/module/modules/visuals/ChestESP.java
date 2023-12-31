package com.ohare.client.module.modules.visuals;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.ohare.client.event.events.render.Render3DEvent;
import com.ohare.client.module.Module;
import com.ohare.client.utils.RenderUtil;

import dorkbox.messageBus.annotations.Subscribe;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;

/**
 * made by oHare for oHareWare
 *
 * @since 5/29/2019
 **/
public class ChestESP extends Module {
    public ChestESP() {
        super("ChestESP", Category.VISUALS, new Color(255, 188, 255, 255).getRGB());
        setDescription("Chest ESP");
    }

    @Subscribe
    public void render3d(Render3DEvent event) {
        for (TileEntity tile : mc.theWorld.loadedTileEntityList) {
            double posX = tile.getPos().getX() - RenderManager.renderPosX;
            double posY = tile.getPos().getY() - RenderManager.renderPosY;
            double posZ = tile.getPos().getZ() - RenderManager.renderPosZ;
            if (tile instanceof TileEntityChest) {

                AxisAlignedBB bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ);
                TileEntityChest adjacent = null;
                if (((TileEntityChest) tile).adjacentChestXNeg != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestXNeg;
                if (((TileEntityChest) tile).adjacentChestXPos != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestXPos;
                if (((TileEntityChest) tile).adjacentChestZNeg != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestZNeg;
                if (((TileEntityChest) tile).adjacentChestZPos != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestZPos;
                if (adjacent != null)
                    bb = bb.union(new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(adjacent.getPos().getX() - RenderManager.renderPosX, adjacent.getPos().getY() - RenderManager.renderPosY, adjacent.getPos().getZ() - RenderManager.renderPosZ));

                if (((TileEntityChest) tile).getChestType() == 1) {
                   drawBlockESP(bb, 255f, 91f, 86f, 255f,1f);
                } else {
                   drawBlockESP(bb, 255f, 227f, 0f, 255f,1f);
                }

            }
            if (tile instanceof TileEntityEnderChest) {
               drawBlockESP(new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ),78f, 197f, 255f, 255f,1f);
            }
        }
    }
    private void drawBlockESP(AxisAlignedBB bb, float red, float green, float blue, float alpha,float width) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, 0.2f);
        RenderUtil.drawBoundingBox(bb);
        GL11.glLineWidth(width);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 255f);
        RenderUtil.drawOutlinedBoundingBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}