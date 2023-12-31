package net.minecraft.entity.item.minecart;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;

public class SpawnerMinecartEntity extends AbstractMinecartEntity
{
    private final AbstractSpawner mobSpawnerLogic = new AbstractSpawner()
    {
        public void broadcastEvent(int id)
        {
            SpawnerMinecartEntity.this.world.setEntityState(SpawnerMinecartEntity.this, (byte)id);
        }
        public World getWorld()
        {
            return SpawnerMinecartEntity.this.world;
        }
        public BlockPos getSpawnerPosition()
        {
            return SpawnerMinecartEntity.this.getPosition();
        }
    };

    public SpawnerMinecartEntity(EntityType <? extends SpawnerMinecartEntity > type, World world)
    {
        super(type, world);
    }

    public SpawnerMinecartEntity(World worldIn, double x, double y, double z)
    {
        super(EntityType.SPAWNER_MINECART, worldIn, x, y, z);
    }

    public Type getMinecartType()
    {
        return Type.SPAWNER;
    }

    public BlockState getDefaultDisplayTile()
    {
        return Blocks.SPAWNER.getDefaultState();
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        this.mobSpawnerLogic.read(compound);
    }

    protected void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        this.mobSpawnerLogic.write(compound);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    public void handleStatusUpdate(byte id)
    {
        this.mobSpawnerLogic.setDelayToMin(id);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick()
    {
        super.tick();
        this.mobSpawnerLogic.tick();
    }

    /**
     * Checks if players can use this entity to access operator (permission level 2) commands either directly or
     * indirectly, such as give or setblock. A similar method exists for entities at {@link
     * net.minecraft.tileentity.TileEntity#onlyOpsCanSetNbt()}.<p>For example, {@link
     * net.minecraft.entity.item.EntityMinecartCommandBlock#ignoreItemEntityData() command block minecarts} and {@link
     * net.minecraft.entity.item.EntityMinecartMobSpawner#ignoreItemEntityData() mob spawner minecarts} (spawning
     * command block minecarts or drops) are considered accessible.</p>@return true if this entity offers ways for
     * unauthorized players to use restricted commands
     */
    public boolean ignoreItemEntityData()
    {
        return true;
    }
}
