package su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMaps;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import mekanism.common.block.BlockMekanism;
import mekanism.common.block.states.IStateFluidLoggable;
import mekanism.common.content.network.transmitter.Transmitter;
import mekanism.common.lib.transmitter.ConnectionType;
import mekanism.common.registries.MekanismItems;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import mekanism.common.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dudko Roman
 */
public abstract class EvoBlockTransmitter extends BlockMekanism implements IStateFluidLoggable {

    private static final Short2ObjectMap<VoxelShape> cachedShapes = Short2ObjectMaps.synchronize(new Short2ObjectOpenHashMap());

    protected EvoBlockTransmitter() {
        super(Properties.of(Material.PISTON).strength(1.0F, 6.0F));
    }

    protected EvoBlockTransmitter(Properties properties) {
        super(properties);
    }

    /** @deprecated */
    @Nonnull
    @Deprecated
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (MekanismUtils.canUseAsWrench(stack) && player.isShiftKeyDown()) {
            if (!world.isClientSide) {
                WorldUtils.dismantleBlock(state, world, pos);
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    public void setPlacedBy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        TileEntityTransmitter tile = (TileEntityTransmitter)WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile != null) {
            tile.onAdded();
        }

    }

    /** @deprecated */
    @Deprecated
    public void neighborChanged(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos, boolean isMoving) {
        TileEntityTransmitter tile = (TileEntityTransmitter)WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile != null) {
            Direction side = Direction.getNearest((float)(neighborPos.getX() - pos.getX()), (float)(neighborPos.getY() - pos.getY()), (float)(neighborPos.getZ() - pos.getZ()));
            tile.onNeighborBlockChange(side);
        }

    }

    public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {
        TileEntityTransmitter tile = (TileEntityTransmitter)WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile != null) {
            Direction side = Direction.getNearest((float)(neighbor.getX() - pos.getX()), (float)(neighbor.getY() - pos.getY()), (float)(neighbor.getZ() - pos.getZ()));
            tile.onNeighborTileChange(side);
        }

    }

    /** @deprecated */
    @Deprecated
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
        return false;
    }

    /** @deprecated */
    @Nonnull
    @Deprecated
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, CollisionContext context) {
        if (!context.isHoldingItem(MekanismItems.CONFIGURATOR.asItem())) {
            return this.getRealShape(world, pos);
        } else {
            if (context instanceof EntityCollisionContext) {
                EntityCollisionContext entityContext = (EntityCollisionContext)context;
                if (entityContext.getEntity() != null) {
                    TileEntityTransmitter tile = (TileEntityTransmitter)WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
                    if (tile == null) {
                        return this.getCenter();
                    }

                    MultipartUtils.AdvancedRayTraceResult result = MultipartUtils.collisionRayTrace(entityContext.getEntity(), pos, tile.getCollisionBoxes());
                    if (result != null && result.valid()) {
                        return result.bounds;
                    }

                    return this.getCenter();
                }
            }

            return this.getRealShape(world, pos);
        }
    }

    /** @deprecated */
    @Nonnull
    @Deprecated
    public VoxelShape getOcclusionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos) {
        return this.getRealShape(world, pos);
    }

    /** @deprecated */
    @Nonnull
    @Deprecated
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return this.getRealShape(world, pos);
    }

    protected abstract VoxelShape getCenter();

    protected abstract VoxelShape getSide(ConnectionType var1, Direction var2);

    private VoxelShape getRealShape(BlockGetter world, BlockPos pos) {
        TileEntityTransmitter tile = (TileEntityTransmitter)WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile == null) {
            return this.getCenter();
        } else {
            Transmitter<?, ?, ?> transmitter = tile.getTransmitter();
            int packedKey = tile.getTransmitterType().getSize().ordinal() << 12;
            Direction[] var6 = EnumUtils.DIRECTIONS;
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction side = var6[var8];
                ConnectionType connectionType = transmitter.getConnectionType(side);
                packedKey |= connectionType.ordinal() << side.ordinal() * 2;
            }

            return (VoxelShape)cachedShapes.computeIfAbsent((short)packedKey, (packed) -> {
                List<VoxelShape> shapes = new ArrayList(EnumUtils.DIRECTIONS.length);
                Direction[] var3 = EnumUtils.DIRECTIONS;
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Direction side = var3[var5];
                    int index = packed >> side.ordinal() * 2 & 3;
                    ConnectionType connectionType = ConnectionType.byIndexStatic(index);
                    if (connectionType != ConnectionType.NONE) {
                        shapes.add(this.getSide(connectionType, side));
                    }
                }

                VoxelShape center = this.getCenter();
                if (shapes.isEmpty()) {
                    return center;
                } else {
                    return VoxelShapeUtils.batchCombine(center, BooleanOp.OR, true, shapes);
                }
            });
        }
    }
}
