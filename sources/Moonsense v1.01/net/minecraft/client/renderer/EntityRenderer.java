// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.renderer;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Calendar;
import org.lwjgl.util.glu.GLU;
import optifine.RandomMobs;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.resources.I18n;
import optifine.TextureUtils;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import org.lwjgl.opengl.GLContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.client.particle.EffectRenderer;
import moonsense.features.modules.type.world.waypoints.SCRenderWorldPassEvent;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.inventory.IInventory;
import optifine.ReflectorForge;
import net.minecraft.world.WorldSettings;
import moonsense.features.modules.type.hud.BlockOverlayModule;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import moonsense.event.impl.SCGuiScreenPostRenderEvent;
import moonsense.event.impl.SCGuiScreenPreRenderEvent;
import optifine.Lagometer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.Display;
import net.minecraft.client.multiplayer.WorldClient;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import moonsense.features.modules.type.mechanic.OldAnimationsModule;
import shadersmod.client.ShadersRender;
import shadersmod.client.Shaders;
import net.minecraft.potion.Potion;
import org.lwjgl.util.glu.Project;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityAnimal;
import moonsense.features.modules.type.mechanic.FreelookModule;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.init.Blocks;
import org.lwjgl.input.Mouse;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.GameSettings;
import optifine.Config;
import moonsense.features.SCModule;
import moonsense.features.modules.type.mechanic.ZoomModule;
import moonsense.config.ModuleConfig;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.Vec3;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.BlockPos;
import net.minecraft.client.shader.ShaderLinkHelper;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import optifine.Reflector;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import net.minecraft.world.World;
import net.minecraft.client.shader.ShaderGroup;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.MouseFilter;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.MapItemRenderer;
import java.util.Random;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class EntityRenderer implements IResourceManagerReloadListener
{
    private static final Logger logger;
    private static final ResourceLocation locationRainPng;
    private static final ResourceLocation locationSnowPng;
    public static boolean anaglyphEnable;
    public static int anaglyphField;
    private Minecraft mc;
    private final IResourceManager resourceManager;
    private Random random;
    private float farPlaneDistance;
    public ItemRenderer itemRenderer;
    private final MapItemRenderer theMapItemRenderer;
    private int rendererUpdateCount;
    private Entity pointedEntity;
    private MouseFilter mouseFilterXAxis;
    private MouseFilter mouseFilterYAxis;
    private float thirdPersonDistance;
    private float thirdPersonDistanceTemp;
    private float smoothCamYaw;
    private float smoothCamPitch;
    private float smoothCamFilterX;
    private float smoothCamFilterY;
    private float smoothCamPartialTicks;
    private float fovModifierHand;
    private float fovModifierHandPrev;
    private float bossColorModifier;
    private float bossColorModifierPrev;
    private boolean cloudFog;
    private boolean field_175074_C;
    private boolean field_175073_D;
    private long prevFrameTime;
    private long renderEndNanoTime;
    private final DynamicTexture lightmapTexture;
    private final int[] lightmapColors;
    private final ResourceLocation locationLightMap;
    private boolean lightmapUpdateNeeded;
    private float torchFlickerX;
    private float field_175075_L;
    private int rainSoundCounter;
    private float[] field_175076_N;
    private float[] field_175077_O;
    private FloatBuffer fogColorBuffer;
    public float field_175080_Q;
    public float field_175082_R;
    public float field_175081_S;
    private float fogColor2;
    private float fogColor1;
    private int field_175079_V;
    private boolean field_175078_W;
    private double cameraZoom;
    private double cameraYaw;
    private double cameraPitch;
    public ShaderGroup theShaderGroup;
    private static final ResourceLocation[] shaderResourceLocations;
    public static final int shaderCount;
    private int shaderIndex;
    private boolean field_175083_ad;
    public int field_175084_ae;
    private static final String __OBFID = "CL_00000947";
    private boolean initialized;
    private World updatedWorld;
    private boolean showDebugInfo;
    public boolean fogStandard;
    private float clipDistance;
    private long lastServerTime;
    private int lastServerTicks;
    private int serverWaitTime;
    private int serverWaitTimeCurrent;
    private float avgServerTimeDiff;
    private float avgServerTickDiff;
    private long lastErrorCheckTimeMs;
    private ShaderGroup[] fxaaShaders;
    int scrollTotal;
    float zoomCount;
    
    static {
        logger = LogManager.getLogger();
        locationRainPng = new ResourceLocation("textures/environment/rain.png");
        locationSnowPng = new ResourceLocation("textures/environment/snow.png");
        shaderResourceLocations = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
        shaderCount = EntityRenderer.shaderResourceLocations.length;
    }
    
    public EntityRenderer(final Minecraft mcIn, final IResourceManager p_i45076_2_) {
        this.random = new Random();
        this.mouseFilterXAxis = new MouseFilter();
        this.mouseFilterYAxis = new MouseFilter();
        this.thirdPersonDistance = 4.0f;
        this.thirdPersonDistanceTemp = 4.0f;
        this.field_175074_C = true;
        this.field_175073_D = true;
        this.prevFrameTime = Minecraft.getSystemTime();
        this.field_175076_N = new float[1024];
        this.field_175077_O = new float[1024];
        this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.field_175079_V = 0;
        this.field_175078_W = false;
        this.cameraZoom = 1.0;
        this.initialized = false;
        this.updatedWorld = null;
        this.showDebugInfo = false;
        this.fogStandard = false;
        this.clipDistance = 128.0f;
        this.lastServerTime = 0L;
        this.lastServerTicks = 0;
        this.serverWaitTime = 0;
        this.serverWaitTimeCurrent = 0;
        this.avgServerTimeDiff = 0.0f;
        this.avgServerTickDiff = 0.0f;
        this.lastErrorCheckTimeMs = 0L;
        this.fxaaShaders = new ShaderGroup[10];
        this.scrollTotal = 4;
        this.zoomCount = 1.0f;
        this.shaderIndex = EntityRenderer.shaderCount;
        this.field_175083_ad = false;
        this.field_175084_ae = 0;
        this.mc = mcIn;
        this.resourceManager = p_i45076_2_;
        this.itemRenderer = mcIn.getItemRenderer();
        this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
        this.lightmapTexture = new DynamicTexture(16, 16);
        this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;
        for (int var3 = 0; var3 < 32; ++var3) {
            for (int var4 = 0; var4 < 32; ++var4) {
                final float var5 = (float)(var4 - 16);
                final float var6 = (float)(var3 - 16);
                final float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
                this.field_175076_N[var3 << 5 | var4] = -var6 / var7;
                this.field_175077_O[var3 << 5 | var4] = var5 / var7;
            }
        }
    }
    
    public boolean isShaderActive() {
        return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
    }
    
    public void stopUseShader() {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        this.shaderIndex = EntityRenderer.shaderCount;
    }
    
    public void func_175071_c() {
        this.field_175083_ad = !this.field_175083_ad;
    }
    
    public void func_175066_a(final Entity p_175066_1_) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.theShaderGroup = null;
            if (p_175066_1_ instanceof EntityCreeper) {
                this.func_175069_a(new ResourceLocation("shaders/post/creeper.json"));
            }
            else if (p_175066_1_ instanceof EntitySpider) {
                this.func_175069_a(new ResourceLocation("shaders/post/spider.json"));
            }
            else if (p_175066_1_ instanceof EntityEnderman) {
                this.func_175069_a(new ResourceLocation("shaders/post/invert.json"));
            }
            else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
                Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, p_175066_1_, this);
            }
        }
    }
    
    public void activateNextShader() {
        if (OpenGlHelper.shadersSupported && this.mc.func_175606_aa() instanceof EntityPlayer) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.shaderIndex = (this.shaderIndex + 1) % (EntityRenderer.shaderResourceLocations.length + 1);
            if (this.shaderIndex != EntityRenderer.shaderCount) {
                this.func_175069_a(EntityRenderer.shaderResourceLocations[this.shaderIndex]);
            }
            else {
                this.theShaderGroup = null;
            }
        }
    }
    
    public void func_175069_a(final ResourceLocation p_175069_1_) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            try {
                (this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), p_175069_1_)).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                this.field_175083_ad = true;
            }
            catch (IOException var3) {
                EntityRenderer.logger.warn("Failed to load shader: " + p_175069_1_, (Throwable)var3);
                this.shaderIndex = EntityRenderer.shaderCount;
                this.field_175083_ad = false;
            }
            catch (JsonSyntaxException var4) {
                EntityRenderer.logger.warn("Failed to load shader: " + p_175069_1_, (Throwable)var4);
                this.shaderIndex = EntityRenderer.shaderCount;
                this.field_175083_ad = false;
            }
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        if (this.shaderIndex != EntityRenderer.shaderCount) {
            this.func_175069_a(EntityRenderer.shaderResourceLocations[this.shaderIndex]);
        }
        else {
            this.func_175066_a(this.mc.func_175606_aa());
        }
    }
    
    public void updateRenderer() {
        if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
        }
        this.updateFovModifierHand();
        this.updateTorchFlicker();
        this.fogColor2 = this.fogColor1;
        this.thirdPersonDistanceTemp = this.thirdPersonDistance;
        if (this.mc.gameSettings.smoothCamera) {
            final float var1 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float var2 = var1 * var1 * var1 * 8.0f;
            this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05f * var2);
            this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05f * var2);
            this.smoothCamPartialTicks = 0.0f;
            this.smoothCamYaw = 0.0f;
            this.smoothCamPitch = 0.0f;
        }
        else {
            this.smoothCamFilterX = 0.0f;
            this.smoothCamFilterY = 0.0f;
            this.mouseFilterXAxis.func_180179_a();
            this.mouseFilterYAxis.func_180179_a();
        }
        if (this.mc.func_175606_aa() == null) {
            this.mc.func_175607_a(this.mc.thePlayer);
        }
        final Entity viewEntity = this.mc.func_175606_aa();
        final double vx = viewEntity.posX;
        final double vy = viewEntity.posY + viewEntity.getEyeHeight();
        final double vz = viewEntity.posZ;
        final float var1 = this.mc.theWorld.getLightBrightness(new BlockPos(vx, vy, vz));
        float var2 = this.mc.gameSettings.renderDistanceChunks / 16.0f;
        var2 = MathHelper.clamp_float(var2, 0.0f, 1.0f);
        final float var3 = var1 * (1.0f - var2) + var2;
        this.fogColor1 += (var3 - this.fogColor1) * 0.1f;
        ++this.rendererUpdateCount;
        this.itemRenderer.updateEquippedItem();
        this.addRainParticles();
        this.bossColorModifierPrev = this.bossColorModifier;
        if (BossStatus.hasColorModifier) {
            this.bossColorModifier += 0.05f;
            if (this.bossColorModifier > 1.0f) {
                this.bossColorModifier = 1.0f;
            }
            BossStatus.hasColorModifier = false;
        }
        else if (this.bossColorModifier > 0.0f) {
            this.bossColorModifier -= 0.0125f;
        }
    }
    
    public ShaderGroup getShaderGroup() {
        return this.theShaderGroup;
    }
    
    public void updateShaderGroupSize(final int p_147704_1_, final int p_147704_2_) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.createBindFramebuffers(p_147704_1_, p_147704_2_);
            }
            this.mc.renderGlobal.checkOcclusionQueryResult(p_147704_1_, p_147704_2_);
        }
        if (this.mc.theWorld == null) {
            return;
        }
    }
    
    public void getMouseOver(final float p_78473_1_) {
        final Entity var2 = this.mc.func_175606_aa();
        if (var2 != null && this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;
            double var3 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = var2.func_174822_a(var3, p_78473_1_);
            double var4 = var3;
            final Vec3 var5 = var2.func_174824_e(p_78473_1_);
            if (this.mc.playerController.extendedReach()) {
                var3 = 6.0;
                var4 = 6.0;
            }
            else {
                if (var3 > 3.0) {
                    var4 = 3.0;
                }
                var3 = var4;
            }
            if (this.mc.objectMouseOver != null) {
                var4 = this.mc.objectMouseOver.hitVec.distanceTo(var5);
            }
            final Vec3 var6 = var2.getLook(p_78473_1_);
            final Vec3 var7 = var5.addVector(var6.xCoord * var3, var6.yCoord * var3, var6.zCoord * var3);
            this.pointedEntity = null;
            Vec3 var8 = null;
            final float var9 = 1.0f;
            final List var10 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var6.xCoord * var3, var6.yCoord * var3, var6.zCoord * var3).expand(var9, var9, var9));
            double var11 = var4;
            for (int var12 = 0; var12 < var10.size(); ++var12) {
                final Entity var13 = var10.get(var12);
                if (var13.canBeCollidedWith()) {
                    final float var14 = var13.getCollisionBorderSize();
                    final AxisAlignedBB var15 = var13.getEntityBoundingBox().expand(var14, var14, var14);
                    final MovingObjectPosition var16 = var15.calculateIntercept(var5, var7);
                    if (var15.isVecInside(var5)) {
                        if (0.0 < var11 || var11 == 0.0) {
                            this.pointedEntity = var13;
                            var8 = ((var16 == null) ? var5 : var16.hitVec);
                            var11 = 0.0;
                        }
                    }
                    else if (var16 != null) {
                        final double var17 = var5.distanceTo(var16.hitVec);
                        if (var17 < var11 || var11 == 0.0) {
                            boolean canRiderInteract = false;
                            if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                                canRiderInteract = Reflector.callBoolean(var13, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                            }
                            if (var13 == var2.ridingEntity && !canRiderInteract) {
                                if (var11 == 0.0) {
                                    this.pointedEntity = var13;
                                    var8 = var16.hitVec;
                                }
                            }
                            else {
                                this.pointedEntity = var13;
                                var8 = var16.hitVec;
                                var11 = var17;
                            }
                        }
                    }
                }
            }
            if (this.pointedEntity != null && (var11 < var4 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var8);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }
    
    private void updateFovModifierHand() {
        float var1 = 1.0f;
        if (this.mc.func_175606_aa() instanceof AbstractClientPlayer) {
            final AbstractClientPlayer var2 = (AbstractClientPlayer)this.mc.func_175606_aa();
            var1 = var2.func_175156_o();
        }
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (var1 - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }
    
    private float getFOVModifier(final float partialTicks, final boolean p_78481_2_) {
        if (ModuleConfig.INSTANCE.isEnabled(ZoomModule.INSTANCE)) {
            if (this.field_175078_W) {
                return 90.0f;
            }
            final Entity var3 = this.mc.func_175606_aa();
            float var4 = 70.0f;
            if (p_78481_2_) {
                var4 = this.mc.gameSettings.fovSetting;
                if (Config.isDynamicFov()) {
                    var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;
                }
            }
            boolean zoomActive = false;
            if (this.mc.currentScreen == null) {
                final GameSettings var5 = this.mc.gameSettings;
                zoomActive = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
            }
            if (ZoomModule.INSTANCE.zoomAnimation.isDone() && !zoomActive) {
                ZoomModule.INSTANCE.zoomAnimation.reset();
            }
            if (zoomActive) {
                if (!Config.zoomMode) {
                    Config.zoomMode = true;
                    this.mc.gameSettings.smoothCamera = true;
                }
                if (Config.zoomMode) {
                    if (ZoomModule.INSTANCE.scrollZoom.getBoolean()) {
                        var4 /= this.getScrollAmount();
                    }
                    else {
                        var4 /= 4.0f;
                    }
                }
                if (Config.zoomMode && ZoomModule.INSTANCE.smoothZoom.getBoolean() && this.zoomCount < 4.0f) {
                    this.zoomCount += 0.015f;
                }
            }
            else {
                if (this.zoomCount > 1.0f) {
                    this.zoomCount -= 0.015f;
                }
                if (Config.zoomMode) {
                    Config.zoomMode = false;
                    if (ZoomModule.INSTANCE.scrollZoom.getBoolean()) {
                        this.scrollTotal = 4;
                    }
                    this.scrollTotal = 4;
                    this.mc.gameSettings.smoothCamera = false;
                    this.mouseFilterXAxis = new MouseFilter();
                    this.mouseFilterYAxis = new MouseFilter();
                    this.mc.renderGlobal.displayListEntitiesDirty = true;
                }
            }
            var4 /= this.zoomCount;
            if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getHealth() <= 0.0f) {
                final float var6 = ((EntityLivingBase)var3).deathTime + partialTicks;
                var4 /= (1.0f - 500.0f / (var6 + 500.0f)) * 2.0f + 1.0f;
            }
            final Block var7 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, partialTicks);
            if (var7.getMaterial() == Material.water) {
                var4 = var4 * 60.0f / 70.0f;
            }
            return var4;
        }
        else {
            if (this.field_175078_W) {
                return 90.0f;
            }
            final Entity var3 = this.mc.func_175606_aa();
            float var4 = 70.0f;
            if (p_78481_2_) {
                var4 = this.mc.gameSettings.fovSetting;
                if (Config.isDynamicFov()) {
                    var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;
                }
            }
            boolean zoomActive = false;
            if (this.mc.currentScreen == null) {
                final GameSettings var5 = this.mc.gameSettings;
                zoomActive = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
            }
            if (zoomActive) {
                if (!Config.zoomMode) {
                    Config.zoomMode = true;
                    this.mc.gameSettings.smoothCamera = true;
                }
                if (Config.zoomMode) {
                    var4 /= 4.0f;
                }
            }
            else if (Config.zoomMode) {
                Config.zoomMode = false;
                this.mc.gameSettings.smoothCamera = false;
                this.mouseFilterXAxis = new MouseFilter();
                this.mouseFilterYAxis = new MouseFilter();
                this.mc.renderGlobal.displayListEntitiesDirty = true;
            }
            if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getHealth() <= 0.0f) {
                final float var6 = ((EntityLivingBase)var3).deathTime + partialTicks;
                var4 /= (1.0f - 500.0f / (var6 + 500.0f)) * 2.0f + 1.0f;
            }
            final Block var7 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, partialTicks);
            if (var7.getMaterial() == Material.water) {
                var4 = var4 * 60.0f / 70.0f;
            }
            return var4;
        }
    }
    
    private int getScrollAmount() {
        if (ZoomModule.INSTANCE.scrollZoom.getBoolean()) {
            final int i = Mouse.getDWheel();
            if (i != 0) {
                if (i > 1) {
                    ++this.scrollTotal;
                }
                if (i < -1) {
                    --this.scrollTotal;
                }
                if (this.scrollTotal > ZoomModule.INSTANCE.scrollMaximum.getInt()) {
                    this.scrollTotal = ZoomModule.INSTANCE.scrollMaximum.getInt();
                }
                if (this.scrollTotal < 1) {
                    this.scrollTotal = 1;
                }
            }
        }
        return this.scrollTotal;
    }
    
    private void hurtCameraEffect(final float p_78482_1_) {
        if (this.mc.func_175606_aa() instanceof EntityLivingBase) {
            final EntityLivingBase var2 = (EntityLivingBase)this.mc.func_175606_aa();
            float var3 = var2.hurtTime - p_78482_1_;
            if (var2.getHealth() <= 0.0f) {
                final float var4 = var2.deathTime + p_78482_1_;
                GlStateManager.rotate(40.0f - 8000.0f / (var4 + 200.0f), 0.0f, 0.0f, 1.0f);
            }
            if (var3 < 0.0f) {
                return;
            }
            var3 /= var2.maxHurtTime;
            var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927f);
            final float var4 = var2.attackedAtYaw;
            GlStateManager.rotate(-var4, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-var3 * 14.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(var4, 0.0f, 1.0f, 0.0f);
        }
    }
    
    private void setupViewBobbing(final float p_78475_1_) {
        if (this.mc.func_175606_aa() instanceof EntityPlayer) {
            final EntityPlayer var2 = (EntityPlayer)this.mc.func_175606_aa();
            final float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
            final float var4 = -(var2.distanceWalkedModified + var3 * p_78475_1_);
            final float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * p_78475_1_;
            final float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * p_78475_1_;
            GlStateManager.translate(MathHelper.sin(var4 * 3.1415927f) * var5 * 0.5f, -Math.abs(MathHelper.cos(var4 * 3.1415927f) * var5), 0.0f);
            GlStateManager.rotate(MathHelper.sin(var4 * 3.1415927f) * var5 * 3.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(Math.abs(MathHelper.cos(var4 * 3.1415927f - 0.2f) * var5) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(var6, 1.0f, 0.0f, 0.0f);
        }
    }
    
    private void orientCamera(final float p_78467_1_) {
        final Entity var2 = this.mc.func_175606_aa();
        float var3 = var2.getEyeHeight();
        double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * p_78467_1_;
        double var5 = var2.prevPosY + (var2.posY - var2.prevPosY) * p_78467_1_ + var3;
        double var6 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * p_78467_1_;
        if (var2 instanceof EntityLivingBase && ((EntityLivingBase)var2).isPlayerSleeping()) {
            ++var3;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                final BlockPos var7 = new BlockPos(var2);
                final IBlockState partialTicks = this.mc.theWorld.getBlockState(var7);
                final Block var8 = partialTicks.getBlock();
                if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
                    Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, this.mc.theWorld, var7, partialTicks, var2);
                }
                else if (var8 == Blocks.bed) {
                    final int var9 = ((EnumFacing)partialTicks.getValue(BlockBed.AGE)).getHorizontalIndex();
                    GlStateManager.rotate((float)(var9 * 90), 0.0f, 1.0f, 0.0f);
                }
                final FreelookModule instance = FreelookModule.INSTANCE;
                final float cameraYaw = FreelookModule.getCameraYaw();
                final FreelookModule instance2 = FreelookModule.INSTANCE;
                final float cameraYaw2 = FreelookModule.getCameraYaw();
                final FreelookModule instance3 = FreelookModule.INSTANCE;
                GlStateManager.rotate(cameraYaw + (cameraYaw2 - FreelookModule.getCameraYaw()) * p_78467_1_ + 180.0f, 0.0f, -1.0f, 0.0f);
                final FreelookModule instance4 = FreelookModule.INSTANCE;
                final float cameraPitch = FreelookModule.getCameraPitch();
                final FreelookModule instance5 = FreelookModule.INSTANCE;
                final float cameraPitch2 = FreelookModule.getCameraPitch();
                final FreelookModule instance6 = FreelookModule.INSTANCE;
                GlStateManager.rotate(cameraPitch + (cameraPitch2 - FreelookModule.getCameraPitch()) * p_78467_1_, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0) {
            double var10 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * p_78467_1_;
            if (this.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-var10));
            }
            else {
                final FreelookModule instance7 = FreelookModule.INSTANCE;
                final float yaw = FreelookModule.getCameraYaw();
                final FreelookModule instance8 = FreelookModule.INSTANCE;
                float pitch = FreelookModule.getCameraPitch();
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    pitch += 180.0f;
                }
                final double roll = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f) * var10;
                final double event = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f) * var10;
                final double var11 = -MathHelper.sin(pitch / 180.0f * 3.1415927f) * var10;
                for (int var12 = 0; var12 < 8; ++var12) {
                    float var13 = (float)((var12 & 0x1) * 2 - 1);
                    float var14 = (float)((var12 >> 1 & 0x1) * 2 - 1);
                    float var15 = (float)((var12 >> 2 & 0x1) * 2 - 1);
                    var13 *= 0.1f;
                    var14 *= 0.1f;
                    var15 *= 0.1f;
                    final MovingObjectPosition var16 = this.mc.theWorld.rayTraceBlocks(new Vec3(var4 + var13, var5 + var14, var6 + var15), new Vec3(var4 - roll + var13 + var15, var5 - var11 + var14, var6 - event + var15));
                    if (var16 != null) {
                        final double var17 = var16.hitVec.distanceTo(new Vec3(var4, var5, var6));
                        if (var17 < var10) {
                            var10 = var17;
                        }
                    }
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                final FreelookModule instance9 = FreelookModule.INSTANCE;
                GlStateManager.rotate(FreelookModule.getCameraPitch() - pitch, 1.0f, 0.0f, 0.0f);
                final FreelookModule instance10 = FreelookModule.INSTANCE;
                GlStateManager.rotate(FreelookModule.getCameraYaw() - yaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float)(-var10));
                final float n = yaw;
                final FreelookModule instance11 = FreelookModule.INSTANCE;
                GlStateManager.rotate(n - FreelookModule.getCameraYaw(), 0.0f, 1.0f, 0.0f);
                final float n2 = pitch;
                final FreelookModule instance12 = FreelookModule.INSTANCE;
                GlStateManager.rotate(n2 - FreelookModule.getCameraPitch(), 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GlStateManager.translate(0.0f, 0.0f, -0.1f);
        }
        if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
            if (!this.mc.gameSettings.debugCamEnable) {
                final FreelookModule instance13 = FreelookModule.INSTANCE;
                final float cameraYaw3 = FreelookModule.getCameraYaw();
                final FreelookModule instance14 = FreelookModule.INSTANCE;
                final float cameraYaw4 = FreelookModule.getCameraYaw();
                final FreelookModule instance15 = FreelookModule.INSTANCE;
                float yaw = cameraYaw3 + (cameraYaw4 - FreelookModule.getCameraYaw()) * p_78467_1_ + 180.0f;
                final FreelookModule instance16 = FreelookModule.INSTANCE;
                final float cameraPitch3 = FreelookModule.getCameraPitch();
                final FreelookModule instance17 = FreelookModule.INSTANCE;
                final float cameraPitch4 = FreelookModule.getCameraPitch();
                final FreelookModule instance18 = FreelookModule.INSTANCE;
                float pitch = cameraPitch3 + (cameraPitch4 - FreelookModule.getCameraPitch()) * p_78467_1_;
                float var18 = 0.0f;
                if (var2 instanceof EntityAnimal) {
                    final EntityAnimal block = (EntityAnimal)var2;
                    yaw = block.prevRotationYawHead + (block.rotationYawHead - block.prevRotationYawHead) * p_78467_1_ + 180.0f;
                }
                final Block var19 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var2, p_78467_1_);
                final Object var20 = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor, this, var2, var19, p_78467_1_, yaw, pitch, var18);
                Reflector.postForgeBusEvent(var20);
                var18 = Reflector.getFieldValueFloat(var20, Reflector.EntityViewRenderEvent_CameraSetup_roll, var18);
                pitch = Reflector.getFieldValueFloat(var20, Reflector.EntityViewRenderEvent_CameraSetup_pitch, pitch);
                yaw = Reflector.getFieldValueFloat(var20, Reflector.EntityViewRenderEvent_CameraSetup_yaw, yaw);
                GlStateManager.rotate(var18, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(pitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(yaw, 0.0f, 1.0f, 0.0f);
            }
        }
        else if (!this.mc.gameSettings.debugCamEnable) {
            final FreelookModule instance19 = FreelookModule.INSTANCE;
            final float cameraPitch5 = FreelookModule.getCameraPitch();
            final FreelookModule instance20 = FreelookModule.INSTANCE;
            final float cameraPitch6 = FreelookModule.getCameraPitch();
            final FreelookModule instance21 = FreelookModule.INSTANCE;
            GlStateManager.rotate(cameraPitch5 + (cameraPitch6 - FreelookModule.getCameraPitch()) * p_78467_1_, 1.0f, 0.0f, 0.0f);
            if (var2 instanceof EntityAnimal) {
                final EntityAnimal var21 = (EntityAnimal)var2;
                GlStateManager.rotate(var21.prevRotationYawHead + (var21.rotationYawHead - var21.prevRotationYawHead) * p_78467_1_ + 180.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final FreelookModule instance22 = FreelookModule.INSTANCE;
                final float cameraYaw5 = FreelookModule.getCameraYaw();
                final FreelookModule instance23 = FreelookModule.INSTANCE;
                final float cameraYaw6 = FreelookModule.getCameraYaw();
                final FreelookModule instance24 = FreelookModule.INSTANCE;
                GlStateManager.rotate(cameraYaw5 + (cameraYaw6 - FreelookModule.getCameraYaw()) * p_78467_1_ + 180.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        GlStateManager.translate(0.0f, -var3, 0.0f);
        var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * p_78467_1_;
        var5 = var2.prevPosY + (var2.posY - var2.prevPosY) * p_78467_1_ + var3;
        var6 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * p_78467_1_;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(var4, var5, var6, p_78467_1_);
    }
    
    public void setupCameraTransform(final float partialTicks, final int pass) {
        this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);
        if (Config.isFogFancy()) {
            this.farPlaneDistance *= 0.95f;
        }
        if (Config.isFogFast()) {
            this.farPlaneDistance *= 0.83f;
        }
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        final float var3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate(-(pass * 2 - 1) * var3, 0.0f, 0.0f);
        }
        this.clipDistance = this.farPlaneDistance * 2.0f;
        if (this.clipDistance < 173.0f) {
            this.clipDistance = 173.0f;
        }
        if (this.mc.theWorld.provider.getDimensionId() == 1) {
            this.clipDistance = 256.0f;
        }
        if (this.cameraZoom != 1.0) {
            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0);
        }
        Project.gluPerspective(this.getFOVModifier(partialTicks, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((pass * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(partialTicks);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(partialTicks);
        }
        final float var4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
        if (var4 > 0.0f) {
            byte var5 = 20;
            if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
                var5 = 7;
            }
            float var6 = 5.0f / (var4 * var4 + 5.0f) - var4 * 0.04f;
            var6 *= var6;
            GlStateManager.rotate((this.rendererUpdateCount + partialTicks) * var5, 0.0f, 1.0f, 1.0f);
            GlStateManager.scale(1.0f / var6, 1.0f, 1.0f);
            GlStateManager.rotate(-(this.rendererUpdateCount + partialTicks) * var5, 0.0f, 1.0f, 1.0f);
        }
        this.orientCamera(partialTicks);
        if (this.field_175078_W) {
            switch (this.field_175079_V) {
                case 0: {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 1: {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 2: {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
                case 4: {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
            }
        }
    }
    
    public void renderHand(final float p_78476_1_, final int p_78476_2_) {
        if (!this.field_175078_W) {
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            final float var3 = 0.07f;
            if (this.mc.gameSettings.anaglyph) {
                GlStateManager.translate(-(p_78476_2_ * 2 - 1) * var3, 0.0f, 0.0f);
            }
            if (Config.isShaders()) {
                Shaders.applyHandDepth();
            }
            Project.gluPerspective(this.getFOVModifier(p_78476_1_, false), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            if (this.mc.gameSettings.anaglyph) {
                GlStateManager.translate((p_78476_2_ * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            boolean var4 = false;
            if (!Config.isShaders() || !Shaders.isHandRendered) {
                GlStateManager.pushMatrix();
                this.hurtCameraEffect(p_78476_1_);
                if (this.mc.gameSettings.viewBobbing) {
                    this.setupViewBobbing(p_78476_1_);
                }
                var4 = (this.mc.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.func_175606_aa()).isPlayerSleeping());
                if (this.mc.gameSettings.thirdPersonView == 0 && !var4 && !this.mc.gameSettings.hideGUI && !this.mc.playerController.enableEverythingIsScrewedUpMode()) {
                    this.func_180436_i();
                    if (Config.isShaders()) {
                        ShadersRender.renderItemFP(this.itemRenderer, p_78476_1_);
                    }
                    else {
                        this.itemRenderer.renderItemInFirstPerson(p_78476_1_);
                    }
                    this.func_175072_h();
                }
                GlStateManager.popMatrix();
            }
            if (Config.isShaders() && !Shaders.isCompositeRendered) {
                return;
            }
            this.func_175072_h();
            if (this.mc.gameSettings.thirdPersonView == 0 && !var4) {
                this.itemRenderer.renderOverlays(p_78476_1_);
                this.hurtCameraEffect(p_78476_1_);
            }
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(p_78476_1_);
            }
        }
        if (OldAnimationsModule.INSTANCE.build.getBoolean()) {
            if (this.mc.thePlayer.getItemInUseCount() != 0 && this.mc.gameSettings.keyBindAttack.getIsKeyPressed() && this.mc.gameSettings.keyBindUseItem.getIsKeyPressed() && this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK)) {
                this.swingItem(this.mc.thePlayer);
            }
            if (this.mc.gameSettings.keyBindAttack.getIsKeyPressed() && this.mc.gameSettings.keyBindUseItem.getIsKeyPressed() && this.mc.objectMouseOver != null) {
                this.mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK);
            }
        }
    }
    
    private void swingItem(final EntityLivingBase entity) {
        final ItemStack stack = entity.getHeldItem();
        if (stack != null && stack.getItem() != null && (!entity.isSwingInProgress || entity.swingProgressInt >= this.getArmSwingAnimationEnd(entity) / 2 || entity.swingProgressInt < 0)) {
            entity.swingProgressInt = -1;
            entity.isSwingInProgress = true;
        }
    }
    
    private int getArmSwingAnimationEnd(final EntityLivingBase e) {
        return e.isPotionActive(Potion.digSpeed) ? (6 - (1 + e.getActivePotionEffect(Potion.digSpeed).getAmplifier())) : (e.isPotionActive(Potion.digSlowdown) ? (6 + (1 + e.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
    }
    
    public void func_175072_h() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.func_179090_x();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (Config.isShaders()) {
            Shaders.disableLightmap();
        }
    }
    
    public void func_180436_i() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        final float var1 = 0.00390625f;
        GlStateManager.scale(var1, var1, var1);
        GlStateManager.translate(8.0f, 8.0f, 8.0f);
        GlStateManager.matrixMode(5888);
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179098_w();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (Config.isShaders()) {
            Shaders.enableLightmap();
        }
    }
    
    private void updateTorchFlicker() {
        this.field_175075_L += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.field_175075_L *= (float)0.9;
        this.torchFlickerX += (this.field_175075_L - this.torchFlickerX) * 1.0f;
        this.lightmapUpdateNeeded = true;
    }
    
    private void updateLightmap(final float partialTicks) {
        if (this.lightmapUpdateNeeded) {
            this.mc.mcProfiler.startSection("lightTex");
            final WorldClient var2 = this.mc.theWorld;
            if (var2 != null) {
                if (Config.isCustomColors() && CustomColors.updateLightmap(var2, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision))) {
                    this.lightmapTexture.updateDynamicTexture();
                    this.lightmapUpdateNeeded = false;
                    this.mc.mcProfiler.endSection();
                    return;
                }
                for (int var3 = 0; var3 < 256; ++var3) {
                    final float var4 = var2.getSunBrightness(1.0f) * 0.95f + 0.05f;
                    float var5 = var2.provider.getLightBrightnessTable()[var3 / 16] * var4;
                    final float var6 = var2.provider.getLightBrightnessTable()[var3 % 16] * (this.torchFlickerX * 0.1f + 1.5f);
                    if (var2.func_175658_ac() > 0) {
                        var5 = var2.provider.getLightBrightnessTable()[var3 / 16];
                    }
                    final float var7 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                    final float var8 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                    final float var9 = var6 * ((var6 * 0.6f + 0.4f) * 0.6f + 0.4f);
                    final float var10 = var6 * (var6 * var6 * 0.6f + 0.4f);
                    float var11 = var7 + var6;
                    float var12 = var8 + var9;
                    float var13 = var5 + var10;
                    var11 = var11 * 0.96f + 0.03f;
                    var12 = var12 * 0.96f + 0.03f;
                    var13 = var13 * 0.96f + 0.03f;
                    if (this.bossColorModifier > 0.0f) {
                        final float var14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
                        var11 = var11 * (1.0f - var14) + var11 * 0.7f * var14;
                        var12 = var12 * (1.0f - var14) + var12 * 0.6f * var14;
                        var13 = var13 * (1.0f - var14) + var13 * 0.6f * var14;
                    }
                    if (var2.provider.getDimensionId() == 1) {
                        var11 = 0.22f + var6 * 0.75f;
                        var12 = 0.28f + var9 * 0.75f;
                        var13 = 0.25f + var10 * 0.75f;
                    }
                    if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                        final float var14 = this.func_180438_a(this.mc.thePlayer, partialTicks);
                        float var15 = 1.0f / var11;
                        if (var15 > 1.0f / var12) {
                            var15 = 1.0f / var12;
                        }
                        if (var15 > 1.0f / var13) {
                            var15 = 1.0f / var13;
                        }
                        var11 = var11 * (1.0f - var14) + var11 * var15 * var14;
                        var12 = var12 * (1.0f - var14) + var12 * var15 * var14;
                        var13 = var13 * (1.0f - var14) + var13 * var15 * var14;
                    }
                    if (var11 > 1.0f) {
                        var11 = 1.0f;
                    }
                    if (var12 > 1.0f) {
                        var12 = 1.0f;
                    }
                    if (var13 > 1.0f) {
                        var13 = 1.0f;
                    }
                    final float var14 = this.mc.gameSettings.gammaSetting;
                    float var15 = 1.0f - var11;
                    float var16 = 1.0f - var12;
                    float var17 = 1.0f - var13;
                    var15 = 1.0f - var15 * var15 * var15 * var15;
                    var16 = 1.0f - var16 * var16 * var16 * var16;
                    var17 = 1.0f - var17 * var17 * var17 * var17;
                    var11 = var11 * (1.0f - var14) + var15 * var14;
                    var12 = var12 * (1.0f - var14) + var16 * var14;
                    var13 = var13 * (1.0f - var14) + var17 * var14;
                    var11 = var11 * 0.96f + 0.03f;
                    var12 = var12 * 0.96f + 0.03f;
                    var13 = var13 * 0.96f + 0.03f;
                    if (var11 > 1.0f) {
                        var11 = 1.0f;
                    }
                    if (var12 > 1.0f) {
                        var12 = 1.0f;
                    }
                    if (var13 > 1.0f) {
                        var13 = 1.0f;
                    }
                    if (var11 < 0.0f) {
                        var11 = 0.0f;
                    }
                    if (var12 < 0.0f) {
                        var12 = 0.0f;
                    }
                    if (var13 < 0.0f) {
                        var13 = 0.0f;
                    }
                    final short var18 = 255;
                    final int var19 = (int)(var11 * 255.0f);
                    final int var20 = (int)(var12 * 255.0f);
                    final int var21 = (int)(var13 * 255.0f);
                    this.lightmapColors[var3] = (var18 << 24 | var19 << 16 | var20 << 8 | var21);
                }
                this.lightmapTexture.updateDynamicTexture();
                this.lightmapUpdateNeeded = false;
                this.mc.mcProfiler.endSection();
            }
        }
    }
    
    private float func_180438_a(final EntityLivingBase p_180438_1_, final float partialTicks) {
        final int var3 = p_180438_1_.getActivePotionEffect(Potion.nightVision).getDuration();
        return (var3 > 200) ? 1.0f : (0.7f + MathHelper.sin((var3 - partialTicks) * 3.1415927f * 0.2f) * 0.3f);
    }
    
    public void updateCameraAndRender(final float partialTicks) {
        this.frameInit();
        final boolean var2 = Display.isActive();
        if (!var2 && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
            if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
                this.mc.displayInGameMenu();
            }
        }
        else {
            this.prevFrameTime = Minecraft.getSystemTime();
        }
        this.mc.mcProfiler.startSection("mouse");
        if (var2 && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
            Mouse.setGrabbed(false);
            Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
            Mouse.setGrabbed(true);
        }
        if (this.mc.inGameHasFocus && var2) {
            final FreelookModule instance = FreelookModule.INSTANCE;
            if (FreelookModule.overrideMouse()) {
                this.mc.mouseHelper.mouseXYChange();
                final float var3 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                final float var4 = var3 * var3 * var3 * 8.0f;
                float var5 = this.mc.mouseHelper.deltaX * var4;
                float var6 = this.mc.mouseHelper.deltaY * var4;
                byte var7 = 1;
                if (this.mc.gameSettings.invertMouse) {
                    var7 = -1;
                }
                if (this.mc.gameSettings.smoothCamera) {
                    this.smoothCamYaw += var5;
                    this.smoothCamPitch += var6;
                    final float var8 = partialTicks - this.smoothCamPartialTicks;
                    this.smoothCamPartialTicks = partialTicks;
                    var5 = this.smoothCamFilterX * var8;
                    var6 = this.smoothCamFilterY * var8;
                    this.mc.thePlayer.setAngles(var5, var6 * var7);
                }
                else {
                    this.smoothCamYaw = 0.0f;
                    this.smoothCamPitch = 0.0f;
                    this.mc.thePlayer.setAngles(var5, var6 * var7);
                }
            }
        }
        this.mc.mcProfiler.endSection();
        if (!this.mc.skipRenderWorld) {
            EntityRenderer.anaglyphEnable = this.mc.gameSettings.anaglyph;
            final ScaledResolution var9 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            final int var10 = var9.getScaledWidth();
            final int var11 = var9.getScaledHeight();
            final int var12 = Mouse.getX() * var10 / this.mc.displayWidth;
            final int var13 = var11 - Mouse.getY() * var11 / this.mc.displayHeight - 1;
            final int var14 = this.mc.gameSettings.limitFramerate;
            if (this.mc.theWorld != null) {
                this.mc.mcProfiler.startSection("level");
                final int var15 = Math.max(Minecraft.func_175610_ah(), 30);
                this.renderWorld(partialTicks, this.renderEndNanoTime + 1000000000 / var15);
                if (OpenGlHelper.shadersSupported) {
                    this.mc.renderGlobal.func_174975_c();
                    if (this.theShaderGroup != null && this.field_175083_ad) {
                        GlStateManager.matrixMode(5890);
                        GlStateManager.pushMatrix();
                        GlStateManager.loadIdentity();
                        this.theShaderGroup.loadShaderGroup(partialTicks);
                        GlStateManager.popMatrix();
                    }
                    this.mc.getFramebuffer().bindFramebuffer(true);
                }
                this.renderEndNanoTime = System.nanoTime();
                this.mc.mcProfiler.endStartSection("gui");
                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
                    GlStateManager.alphaFunc(516, 0.1f);
                    this.mc.ingameGUI.func_175180_a(partialTicks);
                    if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
                        Config.drawFps();
                    }
                    if (this.mc.gameSettings.showDebugInfo) {
                        Lagometer.showLagometer(var9);
                    }
                }
                this.mc.mcProfiler.endSection();
            }
            else {
                GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
            }
            if (this.mc.currentScreen != null) {
                GlStateManager.clear(256);
                try {
                    if (Reflector.ForgeHooksClient_drawScreen.exists()) {
                        Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, this.mc.currentScreen, var12, var13, partialTicks);
                    }
                    else {
                        final SCGuiScreenPreRenderEvent event1 = new SCGuiScreenPreRenderEvent(var12, var13, partialTicks);
                        if (event1.isCancelled()) {
                            return;
                        }
                        this.mc.currentScreen.drawScreen(var12, var13, partialTicks);
                        final SCGuiScreenPostRenderEvent event2 = new SCGuiScreenPostRenderEvent(var12, var13, partialTicks);
                        if (event2.isCancelled()) {
                            return;
                        }
                    }
                }
                catch (Throwable var17) {
                    final CrashReport var16 = CrashReport.makeCrashReport(var17, "Rendering screen");
                    final CrashReportCategory var18 = var16.makeCategory("Screen render details");
                    var18.addCrashSectionCallable("Screen name", new Callable() {
                        private static final String __OBFID = "CL_00000948";
                        
                        @Override
                        public String call() {
                            return EntityRenderer.this.mc.currentScreen.getClass().getCanonicalName();
                        }
                    });
                    var18.addCrashSectionCallable("Mouse location", new Callable() {
                        private static final String __OBFID = "CL_00000950";
                        
                        @Override
                        public String call() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", var12, var13, Mouse.getX(), Mouse.getY());
                        }
                    });
                    var18.addCrashSectionCallable("Screen size", new Callable() {
                        private static final String __OBFID = "CL_00000951";
                        
                        @Override
                        public String call() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", var9.getScaledWidth(), var9.getScaledHeight(), EntityRenderer.this.mc.displayWidth, EntityRenderer.this.mc.displayHeight, var9.getScaleFactor());
                        }
                    });
                    throw new ReportedException(var16);
                }
            }
        }
        this.frameFinish();
        this.waitForServerThread();
        Lagometer.updateLagometer();
        if (this.mc.gameSettings.ofProfiler) {
            this.mc.gameSettings.showDebugProfilerChart = true;
        }
    }
    
    public void func_152430_c(final float p_152430_1_) {
        this.setupOverlayRendering();
        this.mc.ingameGUI.func_180478_c(new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight));
    }
    
    private boolean func_175070_n() {
        if (!this.field_175073_D) {
            return false;
        }
        final Entity var1 = this.mc.func_175606_aa();
        boolean var2 = var1 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI;
        var2 = (var2 || (ModuleConfig.INSTANCE.isEnabled(BlockOverlayModule.INSTANCE) && BlockOverlayModule.INSTANCE.persistent.getBoolean()));
        if (var2 && var1 instanceof EntityPlayer && !((EntityPlayer)var1).capabilities.allowEdit) {
            final ItemStack var3 = ((EntityPlayer)var1).getCurrentEquippedItem();
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos var4 = this.mc.objectMouseOver.func_178782_a();
                final IBlockState state = this.mc.theWorld.getBlockState(var4);
                final Block var5 = state.getBlock();
                if (this.mc.playerController.func_178889_l() == WorldSettings.GameType.SPECTATOR) {
                    var2 = (ReflectorForge.blockHasTileEntity(state) && this.mc.theWorld.getTileEntity(var4) instanceof IInventory);
                }
                else {
                    var2 = (var3 != null && (var3.canDestroy(var5) || var3.canPlaceOn(var5)));
                }
            }
        }
        return var2;
    }
    
    private void func_175067_i(final float p_175067_1_) {
        if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.func_175140_cp() && !this.mc.gameSettings.field_178879_v) {
            final Entity var2 = this.mc.func_175606_aa();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glLineWidth(1.0f);
            GlStateManager.func_179090_x();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            this.orientCamera(p_175067_1_);
            GlStateManager.translate(0.0f, var2.getEyeHeight(), 0.0f);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), -65536);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), -16776961);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), -16711936);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.func_179098_w();
            GlStateManager.disableBlend();
        }
    }
    
    public void renderWorld(final float partialTicks, final long finishTimeNano) {
        this.updateLightmap(partialTicks);
        if (this.mc.func_175606_aa() == null) {
            this.mc.func_175607_a(this.mc.thePlayer);
        }
        this.getMouseOver(partialTicks);
        if (Config.isShaders()) {
            Shaders.beginRender(this.mc, partialTicks, finishTimeNano);
        }
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        this.mc.mcProfiler.startSection("center");
        if (this.mc.gameSettings.anaglyph) {
            EntityRenderer.anaglyphField = 0;
            GlStateManager.colorMask(false, true, true, false);
            this.func_175068_a(0, partialTicks, finishTimeNano);
            EntityRenderer.anaglyphField = 1;
            GlStateManager.colorMask(true, false, false, false);
            this.func_175068_a(1, partialTicks, finishTimeNano);
            GlStateManager.colorMask(true, true, true, false);
        }
        else {
            this.func_175068_a(2, partialTicks, finishTimeNano);
        }
        this.mc.mcProfiler.endSection();
    }
    
    private void func_175068_a(final int pass, final float partialTicks, final long finishTimeNano) {
        final boolean isShaders = Config.isShaders();
        if (isShaders) {
            Shaders.beginRenderPass(pass, partialTicks, finishTimeNano);
        }
        final RenderGlobal var5 = this.mc.renderGlobal;
        final EffectRenderer var6 = this.mc.effectRenderer;
        final boolean var7 = this.func_175070_n();
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("clear");
        if (isShaders) {
            Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        }
        else {
            GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        }
        this.updateFogColor(partialTicks);
        GlStateManager.clear(16640);
        if (isShaders) {
            Shaders.clearRenderBuffer();
        }
        this.mc.mcProfiler.endStartSection("camera");
        this.setupCameraTransform(partialTicks, pass);
        if (isShaders) {
            Shaders.setCamera(partialTicks);
        }
        ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
        this.mc.mcProfiler.endStartSection("frustum");
        ClippingHelperImpl.getInstance();
        this.mc.mcProfiler.endStartSection("culling");
        final Frustrum var8 = new Frustrum();
        final Entity var9 = this.mc.func_175606_aa();
        final double var10 = var9.lastTickPosX + (var9.posX - var9.lastTickPosX) * partialTicks;
        final double var11 = var9.lastTickPosY + (var9.posY - var9.lastTickPosY) * partialTicks;
        final double var12 = var9.lastTickPosZ + (var9.posZ - var9.lastTickPosZ) * partialTicks;
        if (isShaders) {
            ShadersRender.setFrustrumPosition(var8, var10, var11, var12);
        }
        else {
            var8.setPosition(var10, var11, var12);
        }
        if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
            this.setupFog(-1, partialTicks);
            this.mc.mcProfiler.endStartSection("sky");
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(partialTicks, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(5888);
            if (isShaders) {
                Shaders.beginSky();
            }
            var5.func_174976_a(partialTicks, pass);
            if (isShaders) {
                Shaders.endSky();
            }
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(partialTicks, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(5888);
        }
        else {
            GlStateManager.disableBlend();
        }
        this.setupFog(0, partialTicks);
        GlStateManager.shadeModel(7425);
        if (var9.posY + var9.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f) {
            this.func_180437_a(var5, partialTicks, pass);
        }
        this.mc.mcProfiler.endStartSection("prepareterrain");
        this.setupFog(0, partialTicks);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
        this.mc.mcProfiler.endStartSection("terrain_setup");
        if (isShaders) {
            ShadersRender.setupTerrain(var5, var9, partialTicks, var8, this.field_175084_ae++, this.mc.thePlayer.func_175149_v());
        }
        else {
            var5.func_174970_a(var9, partialTicks, var8, this.field_175084_ae++, this.mc.thePlayer.func_175149_v());
        }
        if (pass == 0 || pass == 2) {
            this.mc.mcProfiler.endStartSection("updatechunks");
            Lagometer.timerChunkUpload.start();
            if (isShaders) {
                ShadersRender.updateChunks(var5, finishTimeNano);
            }
            else {
                this.mc.renderGlobal.func_174967_a(finishTimeNano);
            }
            Lagometer.timerChunkUpload.end();
        }
        this.mc.mcProfiler.endStartSection("terrain");
        Lagometer.timerTerrain.start();
        if (this.mc.gameSettings.ofSmoothFps && pass > 0) {
            this.mc.mcProfiler.endStartSection("finish");
            GL11.glFinish();
            this.mc.mcProfiler.endStartSection("terrain");
        }
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        if (isShaders) {
            ShadersRender.beginTerrainSolid();
        }
        var5.func_174977_a(EnumWorldBlockLayer.SOLID, partialTicks, pass, var9);
        GlStateManager.enableAlpha();
        if (isShaders) {
            ShadersRender.beginTerrainCutoutMipped();
        }
        var5.func_174977_a(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, pass, var9);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
        if (isShaders) {
            ShadersRender.beginTerrainCutout();
        }
        var5.func_174977_a(EnumWorldBlockLayer.CUTOUT, partialTicks, pass, var9);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174935_a();
        if (isShaders) {
            ShadersRender.endTerrain();
        }
        Lagometer.timerTerrain.end();
        GlStateManager.shadeModel(7424);
        GlStateManager.alphaFunc(516, 0.1f);
        if (!this.field_175078_W) {
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 0);
            }
            var5.func_180446_a(var9, var8, partialTicks);
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
            }
            RenderHelper.disableStandardItemLighting();
            this.func_175072_h();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            if (this.mc.objectMouseOver != null && var9.isInsideOfMaterial(Material.water) && var7 && var9 instanceof EntityPlayer) {
                final EntityPlayer var13 = (EntityPlayer)var9;
                GlStateManager.disableAlpha();
                this.mc.mcProfiler.endStartSection("outline");
                if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, var5, var13, this.mc.objectMouseOver, 0, var13.getHeldItem(), partialTicks)) && !this.mc.gameSettings.hideGUI) {
                    var5.drawSelectionBox(var13, this.mc.objectMouseOver, 0, partialTicks);
                }
                else {
                    try {
                        var5.drawSelectionBox(var13, this.mc.objectMouseOver, 0, partialTicks);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                GlStateManager.enableAlpha();
            }
        }
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        if (var7 && this.mc.objectMouseOver != null && !var9.isInsideOfMaterial(Material.water) && var9 instanceof EntityPlayer) {
            final EntityPlayer var13 = (EntityPlayer)var9;
            GlStateManager.disableAlpha();
            this.mc.mcProfiler.endStartSection("outline");
            if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, var5, var13, this.mc.objectMouseOver, 0, var13.getHeldItem(), partialTicks)) && !this.mc.gameSettings.hideGUI) {
                var5.drawSelectionBox(var13, this.mc.objectMouseOver, 0, partialTicks);
            }
            else {
                try {
                    var5.drawSelectionBox(var13, this.mc.objectMouseOver, 0, partialTicks);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            GlStateManager.enableAlpha();
        }
        if (!var5.damagedBlocks.isEmpty()) {
            this.mc.mcProfiler.endStartSection("destroyProgress");
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            var5.func_174981_a(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), var9, partialTicks);
            GlStateManager.disableBlend();
        }
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableBlend();
        if (!this.field_175078_W) {
            this.func_180436_i();
            this.mc.mcProfiler.endStartSection("litParticles");
            if (isShaders) {
                Shaders.beginLitParticles();
            }
            var6.renderLitParticles(var9, partialTicks);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0, partialTicks);
            this.mc.mcProfiler.endStartSection("particles");
            if (isShaders) {
                Shaders.beginParticles();
            }
            var6.renderParticles(var9, partialTicks);
            if (isShaders) {
                Shaders.endParticles();
            }
            this.func_175072_h();
        }
        GlStateManager.depthMask(false);
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("weather");
        if (isShaders) {
            Shaders.beginWeather();
        }
        this.renderRainSnow(partialTicks);
        if (isShaders) {
            Shaders.endWeather();
        }
        GlStateManager.depthMask(true);
        var5.func_180449_a(var9, partialTicks);
        if (isShaders) {
            ShadersRender.renderHand0(this, partialTicks, pass);
            Shaders.preWater();
        }
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.1f);
        this.setupFog(0, partialTicks);
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.shadeModel(7425);
        if (Config.isTranslucentBlocksFancy()) {
            this.mc.mcProfiler.endStartSection("translucent");
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if (isShaders) {
                Shaders.beginWater();
            }
            var5.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, var9);
            if (isShaders) {
                Shaders.endWater();
            }
            GlStateManager.disableBlend();
        }
        else {
            this.mc.mcProfiler.endStartSection("translucent");
            if (isShaders) {
                Shaders.beginWater();
            }
            var5.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, var9);
            if (isShaders) {
                Shaders.endWater();
            }
        }
        if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.field_175078_W) {
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, 1);
            this.mc.renderGlobal.func_180446_a(var9, var8, partialTicks);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, -1);
            RenderHelper.disableStandardItemLighting();
        }
        GlStateManager.shadeModel(7424);
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableFog();
        if (var9.posY + var9.getEyeHeight() >= 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0f) {
            this.mc.mcProfiler.endStartSection("aboveClouds");
            this.func_180437_a(var5, partialTicks, pass);
        }
        if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
            this.mc.mcProfiler.endStartSection("forge_render_last");
            Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, var5, partialTicks);
        }
        new SCRenderWorldPassEvent(partialTicks).call();
        this.mc.mcProfiler.endStartSection("hand");
        final boolean handRendered = Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, this.mc.renderGlobal, partialTicks, pass);
        if (!handRendered && this.field_175074_C && !Shaders.isShadowPass) {
            if (isShaders) {
                ShadersRender.renderHand1(this, partialTicks, pass);
                Shaders.renderCompositeFinal();
            }
            GlStateManager.clear(256);
            if (isShaders) {
                ShadersRender.renderFPOverlay(this, partialTicks, pass);
            }
            else {
                this.renderHand(partialTicks, pass);
            }
            this.func_175067_i(partialTicks);
        }
        if (isShaders) {
            Shaders.endRender();
        }
    }
    
    private void func_180437_a(final RenderGlobal p_180437_1_, final float partialTicks, final int pass) {
        if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
            this.mc.mcProfiler.endStartSection("clouds");
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(partialTicks, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance * 4.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            this.setupFog(0, partialTicks);
            p_180437_1_.func_180447_b(partialTicks, pass);
            GlStateManager.disableFog();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(partialTicks, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.clipDistance);
            GlStateManager.matrixMode(5888);
        }
    }
    
    private void addRainParticles() {
        float var1 = this.mc.theWorld.getRainStrength(1.0f);
        if (!Config.isRainFancy()) {
            var1 /= 2.0f;
        }
        if (var1 != 0.0f && Config.isRainSplash()) {
            this.random.setSeed(this.rendererUpdateCount * 312987231L);
            final Entity var2 = this.mc.func_175606_aa();
            final WorldClient var3 = this.mc.theWorld;
            final BlockPos var4 = new BlockPos(var2);
            final byte var5 = 10;
            double var6 = 0.0;
            double var7 = 0.0;
            double var8 = 0.0;
            int var9 = 0;
            int var10 = (int)(100.0f * var1 * var1);
            if (this.mc.gameSettings.particleSetting == 1) {
                var10 >>= 1;
            }
            else if (this.mc.gameSettings.particleSetting == 2) {
                var10 = 0;
            }
            for (int var11 = 0; var11 < var10; ++var11) {
                final BlockPos var12 = var3.func_175725_q(var4.add(this.random.nextInt(var5) - this.random.nextInt(var5), 0, this.random.nextInt(var5) - this.random.nextInt(var5)));
                final BiomeGenBase var13 = var3.getBiomeGenForCoords(var12);
                final BlockPos var14 = var12.offsetDown();
                final Block var15 = var3.getBlockState(var14).getBlock();
                if (var12.getY() <= var4.getY() + var5 && var12.getY() >= var4.getY() - var5 && var13.canSpawnLightningBolt() && var13.func_180626_a(var12) >= 0.15f) {
                    final float var16 = this.random.nextFloat();
                    final float var17 = this.random.nextFloat();
                    if (var15.getMaterial() == Material.lava) {
                        this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var12.getX() + var16, var12.getY() + 0.1f - var15.getBlockBoundsMinY(), var12.getZ() + var17, 0.0, 0.0, 0.0, new int[0]);
                    }
                    else if (var15.getMaterial() != Material.air) {
                        var15.setBlockBoundsBasedOnState(var3, var14);
                        ++var9;
                        if (this.random.nextInt(var9) == 0) {
                            var6 = var14.getX() + var16;
                            var7 = var14.getY() + 0.1f + var15.getBlockBoundsMaxY() - 1.0;
                            var8 = var14.getZ() + var17;
                        }
                        this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, var14.getX() + var16, var14.getY() + 0.1f + var15.getBlockBoundsMaxY(), var14.getZ() + var17, 0.0, 0.0, 0.0, new int[0]);
                    }
                }
            }
            if (var9 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
                this.rainSoundCounter = 0;
                if (var7 > var4.getY() + 1 && var3.func_175725_q(var4).getY() > MathHelper.floor_float((float)var4.getY())) {
                    this.mc.theWorld.playSound(var6, var7, var8, "ambient.weather.rain", 0.1f, 0.5f, false);
                }
                else {
                    this.mc.theWorld.playSound(var6, var7, var8, "ambient.weather.rain", 0.2f, 1.0f, false);
                }
            }
        }
    }
    
    protected void renderRainSnow(final float partialTicks) {
        if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
            final WorldProvider var2 = this.mc.theWorld.provider;
            final Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
            if (var3 != null) {
                Reflector.callVoid(var3, Reflector.IRenderHandler_render, partialTicks, this.mc.theWorld, this.mc);
                return;
            }
        }
        final float var4 = this.mc.theWorld.getRainStrength(partialTicks);
        if (var4 > 0.0f) {
            if (Config.isRainOff()) {
                return;
            }
            this.func_180436_i();
            final Entity var5 = this.mc.func_175606_aa();
            final WorldClient var6 = this.mc.theWorld;
            final int var7 = MathHelper.floor_double(var5.posX);
            final int var8 = MathHelper.floor_double(var5.posY);
            final int var9 = MathHelper.floor_double(var5.posZ);
            final Tessellator var10 = Tessellator.getInstance();
            final WorldRenderer var11 = var10.getWorldRenderer();
            GlStateManager.disableCull();
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1f);
            final double var12 = var5.lastTickPosX + (var5.posX - var5.lastTickPosX) * partialTicks;
            final double var13 = var5.lastTickPosY + (var5.posY - var5.lastTickPosY) * partialTicks;
            final double var14 = var5.lastTickPosZ + (var5.posZ - var5.lastTickPosZ) * partialTicks;
            final int var15 = MathHelper.floor_double(var13);
            byte var16 = 5;
            if (Config.isRainFancy()) {
                var16 = 10;
            }
            byte var17 = -1;
            final float var18 = this.rendererUpdateCount + partialTicks;
            if (Config.isRainFancy()) {
                var16 = 10;
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            for (int var19 = var9 - var16; var19 <= var9 + var16; ++var19) {
                for (int var20 = var7 - var16; var20 <= var7 + var16; ++var20) {
                    final int var21 = (var19 - var9 + 16) * 32 + var20 - var7 + 16;
                    final float var22 = this.field_175076_N[var21] * 0.5f;
                    final float var23 = this.field_175077_O[var21] * 0.5f;
                    final BlockPos var24 = new BlockPos(var20, 0, var19);
                    final BiomeGenBase var25 = var6.getBiomeGenForCoords(var24);
                    if (var25.canSpawnLightningBolt() || var25.getEnableSnow()) {
                        final int var26 = var6.func_175725_q(var24).getY();
                        int var27 = var8 - var16;
                        int var28 = var8 + var16;
                        if (var27 < var26) {
                            var27 = var26;
                        }
                        if (var28 < var26) {
                            var28 = var26;
                        }
                        final float var29 = 1.0f;
                        int var30;
                        if ((var30 = var26) < var15) {
                            var30 = var15;
                        }
                        if (var27 != var28) {
                            this.random.setSeed(var20 * var20 * 3121 + var20 * 45238971 ^ var19 * var19 * 418711 + var19 * 13761);
                            final float var31 = var25.func_180626_a(new BlockPos(var20, var27, var19));
                            if (var6.getWorldChunkManager().getTemperatureAtHeight(var31, var26) >= 0.15f) {
                                if (var17 != 0) {
                                    if (var17 >= 0) {
                                        var10.draw();
                                    }
                                    var17 = 0;
                                    this.mc.getTextureManager().bindTexture(EntityRenderer.locationRainPng);
                                    var11.startDrawingQuads();
                                }
                                final float var32 = ((this.rendererUpdateCount + var20 * var20 * 3121 + var20 * 45238971 + var19 * var19 * 418711 + var19 * 13761 & 0x1F) + partialTicks) / 32.0f * (3.0f + this.random.nextFloat());
                                final double var33 = var20 + 0.5f - var5.posX;
                                final double var34 = var19 + 0.5f - var5.posZ;
                                final float var35 = MathHelper.sqrt_double(var33 * var33 + var34 * var34) / var16;
                                final float var36 = 1.0f;
                                var11.func_178963_b(var6.getCombinedLight(new BlockPos(var20, var30, var19), 0));
                                var11.func_178960_a(var36, var36, var36, ((1.0f - var35 * var35) * 0.5f + 0.5f) * var4);
                                var11.setTranslation(-var12 * 1.0, -var13 * 1.0, -var14 * 1.0);
                                var11.addVertexWithUV(var20 - var22 + 0.5, var27, var19 - var23 + 0.5, 0.0f * var29, var27 * var29 / 4.0f + var32 * var29);
                                var11.addVertexWithUV(var20 + var22 + 0.5, var27, var19 + var23 + 0.5, 1.0f * var29, var27 * var29 / 4.0f + var32 * var29);
                                var11.addVertexWithUV(var20 + var22 + 0.5, var28, var19 + var23 + 0.5, 1.0f * var29, var28 * var29 / 4.0f + var32 * var29);
                                var11.addVertexWithUV(var20 - var22 + 0.5, var28, var19 - var23 + 0.5, 0.0f * var29, var28 * var29 / 4.0f + var32 * var29);
                                var11.setTranslation(0.0, 0.0, 0.0);
                            }
                            else {
                                if (var17 != 1) {
                                    if (var17 >= 0) {
                                        var10.draw();
                                    }
                                    var17 = 1;
                                    this.mc.getTextureManager().bindTexture(EntityRenderer.locationSnowPng);
                                    var11.startDrawingQuads();
                                }
                                final float var32 = ((this.rendererUpdateCount & 0x1FF) + partialTicks) / 512.0f;
                                final float var37 = this.random.nextFloat() + var18 * 0.01f * (float)this.random.nextGaussian();
                                final float var38 = this.random.nextFloat() + var18 * (float)this.random.nextGaussian() * 0.001f;
                                final double var34 = var20 + 0.5f - var5.posX;
                                final double var39 = var19 + 0.5f - var5.posZ;
                                final float var40 = MathHelper.sqrt_double(var34 * var34 + var39 * var39) / var16;
                                final float var41 = 1.0f;
                                var11.func_178963_b((var6.getCombinedLight(new BlockPos(var20, var30, var19), 0) * 3 + 15728880) / 4);
                                var11.func_178960_a(var41, var41, var41, ((1.0f - var40 * var40) * 0.3f + 0.5f) * var4);
                                var11.setTranslation(-var12 * 1.0, -var13 * 1.0, -var14 * 1.0);
                                var11.addVertexWithUV(var20 - var22 + 0.5, var27, var19 - var23 + 0.5, 0.0f * var29 + var37, var27 * var29 / 4.0f + var32 * var29 + var38);
                                var11.addVertexWithUV(var20 + var22 + 0.5, var27, var19 + var23 + 0.5, 1.0f * var29 + var37, var27 * var29 / 4.0f + var32 * var29 + var38);
                                var11.addVertexWithUV(var20 + var22 + 0.5, var28, var19 + var23 + 0.5, 1.0f * var29 + var37, var28 * var29 / 4.0f + var32 * var29 + var38);
                                var11.addVertexWithUV(var20 - var22 + 0.5, var28, var19 - var23 + 0.5, 0.0f * var29 + var37, var28 * var29 / 4.0f + var32 * var29 + var38);
                                var11.setTranslation(0.0, 0.0, 0.0);
                            }
                        }
                    }
                }
            }
            if (var17 >= 0) {
                var10.draw();
            }
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1f);
            this.func_175072_h();
        }
    }
    
    public void setupOverlayRendering() {
        final ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }
    
    private void updateFogColor(final float partialTicks) {
        final WorldClient var2 = this.mc.theWorld;
        final Entity var3 = this.mc.func_175606_aa();
        float var4 = 0.25f + 0.75f * this.mc.gameSettings.renderDistanceChunks / 32.0f;
        var4 = 1.0f - (float)Math.pow(var4, 0.25);
        Vec3 var5 = var2.getSkyColor(this.mc.func_175606_aa(), partialTicks);
        var5 = CustomColors.getWorldSkyColor(var5, var2, this.mc.func_175606_aa(), partialTicks);
        final float var6 = (float)var5.xCoord;
        final float var7 = (float)var5.yCoord;
        final float var8 = (float)var5.zCoord;
        Vec3 var9 = var2.getFogColor(partialTicks);
        var9 = CustomColors.getWorldFogColor(var9, var2, this.mc.func_175606_aa(), partialTicks);
        this.field_175080_Q = (float)var9.xCoord;
        this.field_175082_R = (float)var9.yCoord;
        this.field_175081_S = (float)var9.zCoord;
        if (this.mc.gameSettings.renderDistanceChunks >= 4) {
            final double var10 = -1.0;
            final Vec3 var11 = (MathHelper.sin(var2.getCelestialAngleRadians(partialTicks)) > 0.0f) ? new Vec3(var10, 0.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
            float var12 = (float)var3.getLook(partialTicks).dotProduct(var11);
            if (var12 < 0.0f) {
                var12 = 0.0f;
            }
            if (var12 > 0.0f) {
                final float[] var13 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(partialTicks), partialTicks);
                if (var13 != null) {
                    var12 *= var13[3];
                    this.field_175080_Q = this.field_175080_Q * (1.0f - var12) + var13[0] * var12;
                    this.field_175082_R = this.field_175082_R * (1.0f - var12) + var13[1] * var12;
                    this.field_175081_S = this.field_175081_S * (1.0f - var12) + var13[2] * var12;
                }
            }
        }
        this.field_175080_Q += (var6 - this.field_175080_Q) * var4;
        this.field_175082_R += (var7 - this.field_175082_R) * var4;
        this.field_175081_S += (var8 - this.field_175081_S) * var4;
        final float var14 = var2.getRainStrength(partialTicks);
        if (var14 > 0.0f) {
            final float var15 = 1.0f - var14 * 0.5f;
            final float var16 = 1.0f - var14 * 0.4f;
            this.field_175080_Q *= var15;
            this.field_175082_R *= var15;
            this.field_175081_S *= var16;
        }
        final float var15 = var2.getWeightedThunderStrength(partialTicks);
        if (var15 > 0.0f) {
            final float var16 = 1.0f - var15 * 0.5f;
            this.field_175080_Q *= var16;
            this.field_175082_R *= var16;
            this.field_175081_S *= var16;
        }
        final Block var17 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, partialTicks);
        if (this.cloudFog) {
            final Vec3 fogYFactor = var2.getCloudColour(partialTicks);
            this.field_175080_Q = (float)fogYFactor.xCoord;
            this.field_175082_R = (float)fogYFactor.yCoord;
            this.field_175081_S = (float)fogYFactor.zCoord;
        }
        else if (var17.getMaterial() == Material.water) {
            float var12 = EnchantmentHelper.func_180319_a(var3) * 0.2f;
            if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)) {
                var12 = var12 * 0.3f + 0.6f;
            }
            this.field_175080_Q = 0.02f + var12;
            this.field_175082_R = 0.02f + var12;
            this.field_175081_S = 0.2f + var12;
            final Vec3 fogYFactor = CustomColors.getUnderwaterColor(this.mc.theWorld, this.mc.func_175606_aa().posX, this.mc.func_175606_aa().posY + 1.0, this.mc.func_175606_aa().posZ);
            if (fogYFactor != null) {
                this.field_175080_Q = (float)fogYFactor.xCoord;
                this.field_175082_R = (float)fogYFactor.yCoord;
                this.field_175081_S = (float)fogYFactor.zCoord;
            }
        }
        else if (var17.getMaterial() == Material.lava) {
            this.field_175080_Q = 0.6f;
            this.field_175082_R = 0.1f;
            this.field_175081_S = 0.0f;
        }
        float var12 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
        this.field_175080_Q *= var12;
        this.field_175082_R *= var12;
        this.field_175081_S *= var12;
        final double fogYFactor2 = var2.provider.getVoidFogYFactor();
        double var18 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * partialTicks) * fogYFactor2;
        if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.blindness)) {
            final int var19 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
            if (var19 < 20) {
                var18 *= 1.0f - var19 / 20.0f;
            }
            else {
                var18 = 0.0;
            }
        }
        if (var18 < 1.0) {
            if (var18 < 0.0) {
                var18 = 0.0;
            }
            var18 *= var18;
            this.field_175080_Q *= (float)var18;
            this.field_175082_R *= (float)var18;
            this.field_175081_S *= (float)var18;
        }
        if (this.bossColorModifier > 0.0f) {
            final float var20 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
            this.field_175080_Q = this.field_175080_Q * (1.0f - var20) + this.field_175080_Q * 0.7f * var20;
            this.field_175082_R = this.field_175082_R * (1.0f - var20) + this.field_175082_R * 0.6f * var20;
            this.field_175081_S = this.field_175081_S * (1.0f - var20) + this.field_175081_S * 0.6f * var20;
        }
        if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.nightVision)) {
            final float var20 = this.func_180438_a((EntityLivingBase)var3, partialTicks);
            float var21 = 1.0f / this.field_175080_Q;
            if (var21 > 1.0f / this.field_175082_R) {
                var21 = 1.0f / this.field_175082_R;
            }
            if (var21 > 1.0f / this.field_175081_S) {
                var21 = 1.0f / this.field_175081_S;
            }
            this.field_175080_Q = this.field_175080_Q * (1.0f - var20) + this.field_175080_Q * var21 * var20;
            this.field_175082_R = this.field_175082_R * (1.0f - var20) + this.field_175082_R * var21 * var20;
            this.field_175081_S = this.field_175081_S * (1.0f - var20) + this.field_175081_S * var21 * var20;
        }
        if (this.mc.gameSettings.anaglyph) {
            final float var20 = (this.field_175080_Q * 30.0f + this.field_175082_R * 59.0f + this.field_175081_S * 11.0f) / 100.0f;
            final float var21 = (this.field_175080_Q * 30.0f + this.field_175082_R * 70.0f) / 100.0f;
            final float event = (this.field_175080_Q * 30.0f + this.field_175081_S * 70.0f) / 100.0f;
            this.field_175080_Q = var20;
            this.field_175082_R = var21;
            this.field_175081_S = event;
        }
        if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
            final Object event2 = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, this, var3, var17, partialTicks, this.field_175080_Q, this.field_175082_R, this.field_175081_S);
            Reflector.postForgeBusEvent(event2);
            this.field_175080_Q = Reflector.getFieldValueFloat(event2, Reflector.EntityViewRenderEvent_FogColors_red, this.field_175080_Q);
            this.field_175082_R = Reflector.getFieldValueFloat(event2, Reflector.EntityViewRenderEvent_FogColors_green, this.field_175082_R);
            this.field_175081_S = Reflector.getFieldValueFloat(event2, Reflector.EntityViewRenderEvent_FogColors_blue, this.field_175081_S);
        }
        Shaders.setClearColor(this.field_175080_Q, this.field_175082_R, this.field_175081_S, 0.0f);
    }
    
    private void setupFog(final int p_78468_1_, final float partialTicks) {
        final Entity var3 = this.mc.func_175606_aa();
        boolean var4 = false;
        this.fogStandard = false;
        if (var3 instanceof EntityPlayer) {
            var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
        }
        GL11.glFog(2918, this.setFogColorBuffer(this.field_175080_Q, this.field_175082_R, this.field_175081_S, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final Block var5 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, partialTicks);
        float forgeFogDensity = -1.0f;
        if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
            forgeFogDensity = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, this, var3, var5, partialTicks, 0.1f);
        }
        if (forgeFogDensity >= 0.0f) {
            GlStateManager.setFogDensity(forgeFogDensity);
        }
        else if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.blindness)) {
            float var6 = 5.0f;
            final int var7 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
            if (var7 < 20) {
                var6 = 5.0f + (this.farPlaneDistance - 5.0f) * (1.0f - var7 / 20.0f);
            }
            if (Config.isShaders()) {
                Shaders.setFog(9729);
            }
            else {
                GlStateManager.setFog(9729);
            }
            if (p_78468_1_ == -1) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(var6 * 0.8f);
            }
            else {
                GlStateManager.setFogStart(var6 * 0.25f);
                GlStateManager.setFogEnd(var6);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
                GL11.glFogi(34138, 34139);
            }
        }
        else if (this.cloudFog) {
            if (Config.isShaders()) {
                Shaders.setFog(2048);
            }
            else {
                GlStateManager.setFog(2048);
            }
            GlStateManager.setFogDensity(0.1f);
        }
        else if (var5.getMaterial() == Material.water) {
            if (Config.isShaders()) {
                Shaders.setFog(2048);
            }
            else {
                GlStateManager.setFog(2048);
            }
            if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)) {
                GlStateManager.setFogDensity(0.01f);
            }
            else {
                GlStateManager.setFogDensity(0.1f - EnchantmentHelper.func_180319_a(var3) * 0.03f);
            }
            if (Config.isClearWater()) {
                GlStateManager.setFogDensity(0.02f);
            }
        }
        else if (var5.getMaterial() == Material.lava) {
            if (Config.isShaders()) {
                Shaders.setFog(2048);
            }
            else {
                GlStateManager.setFog(2048);
            }
            GlStateManager.setFogDensity(2.0f);
        }
        else {
            final float var6 = this.farPlaneDistance;
            this.fogStandard = true;
            if (Config.isShaders()) {
                Shaders.setFog(9729);
            }
            else {
                GlStateManager.setFog(9729);
            }
            if (p_78468_1_ == -1) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(var6);
            }
            else {
                GlStateManager.setFogStart(var6 * Config.getFogStart());
                GlStateManager.setFogEnd(var6);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                if (Config.isFogFancy()) {
                    GL11.glFogi(34138, 34139);
                }
                if (Config.isFogFast()) {
                    GL11.glFogi(34138, 34140);
                }
            }
            if (this.mc.theWorld.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ)) {
                GlStateManager.setFogStart(var6 * 0.05f);
                GlStateManager.setFogEnd(var6);
            }
            if (Reflector.ForgeHooksClient_onFogRender.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, this, var3, var5, partialTicks, p_78468_1_, var6);
            }
        }
        GlStateManager.enableColorMaterial();
        GlStateManager.enableFog();
        GlStateManager.colorMaterial(1028, 4608);
    }
    
    private FloatBuffer setFogColorBuffer(final float p_78469_1_, final float p_78469_2_, final float p_78469_3_, final float p_78469_4_) {
        if (Config.isShaders()) {
            Shaders.setFogColor(p_78469_1_, p_78469_2_, p_78469_3_);
        }
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(p_78469_1_).put(p_78469_2_).put(p_78469_3_).put(p_78469_4_);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }
    
    public MapItemRenderer getMapItemRenderer() {
        return this.theMapItemRenderer;
    }
    
    private void waitForServerThread() {
        this.serverWaitTimeCurrent = 0;
        if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
            if (this.mc.isIntegratedServerRunning()) {
                final IntegratedServer srv = this.mc.getIntegratedServer();
                if (srv != null) {
                    final boolean paused = this.mc.isGamePaused();
                    if (!paused && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
                        if (this.serverWaitTime > 0) {
                            Lagometer.timerServer.start();
                            Config.sleep(this.serverWaitTime);
                            Lagometer.timerServer.end();
                            this.serverWaitTimeCurrent = this.serverWaitTime;
                        }
                        final long timeNow = System.nanoTime() / 1000000L;
                        if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                            long timeDiff = timeNow - this.lastServerTime;
                            if (timeDiff < 0L) {
                                this.lastServerTime = timeNow;
                                timeDiff = 0L;
                            }
                            if (timeDiff >= 50L) {
                                this.lastServerTime = timeNow;
                                final int ticks = srv.getTickCounter();
                                int tickDiff = ticks - this.lastServerTicks;
                                if (tickDiff < 0) {
                                    this.lastServerTicks = ticks;
                                    tickDiff = 0;
                                }
                                if (tickDiff < 1 && this.serverWaitTime < 100) {
                                    this.serverWaitTime += 2;
                                }
                                if (tickDiff > 1 && this.serverWaitTime > 0) {
                                    --this.serverWaitTime;
                                }
                                this.lastServerTicks = ticks;
                            }
                        }
                        else {
                            this.lastServerTime = timeNow;
                            this.lastServerTicks = srv.getTickCounter();
                            this.avgServerTickDiff = 1.0f;
                            this.avgServerTimeDiff = 50.0f;
                        }
                    }
                    else {
                        if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
                            Config.sleep(20L);
                        }
                        this.lastServerTime = 0L;
                        this.lastServerTicks = 0;
                    }
                }
            }
        }
        else {
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
        }
    }
    
    private void frameInit() {
        if (!this.initialized) {
            TextureUtils.registerResourceListener();
            if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
                Config.setNotify64BitJava(true);
            }
            this.initialized = true;
        }
        Config.checkDisplayMode();
        final WorldClient world = this.mc.theWorld;
        if (world != null) {
            if (Config.getNewRelease() != null) {
                final String msg = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
                final String fullNewVer = String.valueOf(msg) + " " + Config.getNewRelease();
                final ChatComponentText msg2 = new ChatComponentText(I18n.format("of.message.newVersion", fullNewVer));
                this.mc.ingameGUI.getChatGUI().printChatMessage(msg2);
                Config.setNewRelease(null);
            }
            if (Config.isNotify64BitJava()) {
                Config.setNotify64BitJava(false);
                final ChatComponentText msg3 = new ChatComponentText(I18n.format("of.message.java64Bit", new Object[0]));
                this.mc.ingameGUI.getChatGUI().printChatMessage(msg3);
            }
        }
        if (this.mc.currentScreen instanceof GuiMainMenu) {
            this.updateMainMenu((GuiMainMenu)this.mc.currentScreen);
        }
        if (this.updatedWorld != world) {
            RandomMobs.worldChanged(this.updatedWorld, world);
            Config.updateThreadPriorities();
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
            this.updatedWorld = world;
        }
        if (!this.setFxaaShader(Shaders.configAntialiasingLevel)) {
            Shaders.configAntialiasingLevel = 0;
        }
    }
    
    private void frameFinish() {
        if (this.mc.theWorld != null) {
            final long now = System.currentTimeMillis();
            if (now > this.lastErrorCheckTimeMs + 10000L) {
                this.lastErrorCheckTimeMs = now;
                final int err = GL11.glGetError();
                if (err != 0) {
                    final String text = GLU.gluErrorString(err);
                    final ChatComponentText msg = new ChatComponentText(I18n.format("of.message.openglError", err, text));
                    this.mc.ingameGUI.getChatGUI().printChatMessage(msg);
                }
            }
        }
    }
    
    private void updateMainMenu(final GuiMainMenu mainGui) {
        try {
            String e = null;
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            final int day = calendar.get(5);
            final int month = calendar.get(2) + 1;
            if (day == 8 && month == 4) {
                e = "Happy birthday, OptiFine!";
            }
            if (day == 14 && month == 8) {
                e = "Happy birthday, sp614x!";
            }
            if (e == null) {
                return;
            }
            final Field[] fs = GuiMainMenu.class.getDeclaredFields();
            for (int i = 0; i < fs.length; ++i) {
                if (fs[i].getType() == String.class) {
                    fs[i].setAccessible(true);
                    fs[i].set(mainGui, e);
                    break;
                }
            }
        }
        catch (Throwable t) {}
    }
    
    public boolean setFxaaShader(final int fxaaLevel) {
        if (!OpenGlHelper.isFramebufferEnabled()) {
            return false;
        }
        if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && this.theShaderGroup != this.fxaaShaders[4]) {
            return true;
        }
        if (fxaaLevel != 2 && fxaaLevel != 4) {
            if (this.theShaderGroup == null) {
                return true;
            }
            this.theShaderGroup.deleteShaderGroup();
            this.theShaderGroup = null;
            return true;
        }
        else {
            if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[fxaaLevel]) {
                return true;
            }
            if (this.mc.theWorld == null) {
                return true;
            }
            this.func_175069_a(new ResourceLocation("shaders/post/fxaa_of_" + fxaaLevel + "x.json"));
            this.fxaaShaders[fxaaLevel] = this.theShaderGroup;
            return this.field_175083_ad;
        }
    }
}
