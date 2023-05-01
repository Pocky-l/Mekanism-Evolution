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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter.EvoTileEntityTransmitter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dudko Roman
 */
public abstract class EvoBlockTransmitter extends BlockMekanism implements IStateFluidLoggable {

    //Max retained size if we used a HashMap with a key of record(Size, ConnectionType[6]) ~= 1,343,576B
    //Max retained size packing it like this 163,987B
    private static final Short2ObjectMap<VoxelShape> cachedShapes = Short2ObjectMaps.synchronize(new Short2ObjectOpenHashMap<>());

    protected EvoBlockTransmitter() {
        super(BlockBehaviour.Properties.of(Material.PISTON).strength(1, 6));
    }

    public EvoBlockTransmitter(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    @Deprecated
    public InteractionResult use(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand,
                                 @NotNull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (MekanismUtils.canUseAsWrench(stack) && player.isShiftKeyDown()) {
            if (!world.isClientSide) {
                WorldUtils.dismantleBlock(state, world, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void setPlacedBy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        TileEntityTransmitter tile = WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile != null) {
            tile.onAdded();
        }
    }

    @Override
    @Deprecated
    public void neighborChanged(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos,
                                boolean isMoving) {
        TileEntityTransmitter tile = WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile != null) {
            Direction side = Direction.getNearest(neighborPos.getX() - pos.getX(), neighborPos.getY() - pos.getY(), neighborPos.getZ() - pos.getZ());
            tile.onNeighborBlockChange(side);
        }
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {
        TileEntityTransmitter tile = WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile != null) {
            Direction side = Direction.getNearest(neighbor.getX() - pos.getX(), neighbor.getY() - pos.getY(), neighbor.getZ() - pos.getZ());
            tile.onNeighborTileChange(side);
        }
    }

    @Override
    @Deprecated
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return false;
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, CollisionContext context) {
        if (!context.isHoldingItem(MekanismItems.CONFIGURATOR.asItem())) {
            return getRealShape(world, pos);
        }
        //Get the partial selection box if we are holding a configurator
        if (!(context instanceof EntityCollisionContext entityContext) || entityContext.getEntity() == null) {
            //If we don't have an entity get the full VoxelShape
            return getRealShape(world, pos);
        }
        TileEntityTransmitter tile = WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile == null) {
            //If we failed to get the tile, just give the center shape
            return getCenter();
        }
        //TODO: Try to cache some of this? At the very least the collision boxes
        MultipartUtils.AdvancedRayTraceResult result = MultipartUtils.collisionRayTrace(entityContext.getEntity(), pos, tile.getCollisionBoxes());
        if (result != null && result.valid()) {
            return result.bounds;
        }
        //If we failed to figure it out somehow, just fall back to the center. This should never happen
        return getCenter();
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        //Override this so that we ALWAYS have the full collision box, even if a configurator is being held
        return getRealShape(world, pos);
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        //Override this so that we ALWAYS have the full collision box, even if a configurator is being held
        return getRealShape(world, pos);
    }

    protected abstract VoxelShape getCenter();

    protected abstract VoxelShape getSide(ConnectionType type, Direction side);

    private VoxelShape getRealShape(BlockGetter world, BlockPos pos) {
        TileEntityTransmitter tile = WorldUtils.getTileEntity(TileEntityTransmitter.class, world, pos);
        if (tile == null) {
            //If we failed to get the tile, just give the center shape
            return getCenter();
        }
        Transmitter<?, ?, ?> transmitter = tile.getTransmitter();
        //Created a pack key as follows:
        // first four bits of a short are used to represent size (realistically first three are ignored and fourth represents small or large)
        // last 12 bits are separated into 6 sides each of 2 bits that represent the connection type
        int packedKey = tile.getTransmitterType().getSize().ordinal() << 12;
        for (Direction side : EnumUtils.DIRECTIONS) {
            //Get the actual connection types
            ConnectionType connectionType = transmitter.getConnectionType(side);
            //Bit shift in increments of two based on which side we are on
            packedKey |= connectionType.ordinal() << (side.ordinal() * 2);
        }
        //We can cast this to a short as we don't use more bits than are in a short, we just use an int to simplify bit shifting
        return cachedShapes.computeIfAbsent((short) packedKey, packed -> {
            //If we don't have a cached version of our shape, then we need to calculate it
            //size = Size.byIndexStatic(packed >> 12);
            List<VoxelShape> shapes = new ArrayList<>(EnumUtils.DIRECTIONS.length);
            for (Direction side : EnumUtils.DIRECTIONS) {
                //Unpack the ordinal of the connection type (shift so that significant bits are the two rightmost
                // and then read those two bits
                int index = (packed >> (side.ordinal() * 2)) & 0b11;
                ConnectionType connectionType = ConnectionType.byIndexStatic(index);
                if (connectionType != ConnectionType.NONE) {
                    shapes.add(getSide(connectionType, side));
                }
            }
            VoxelShape center = getCenter();
            if (shapes.isEmpty()) {
                return center;
            }
            //Call batchCombine directly rather than just combine so that we can skip a few checks
            return VoxelShapeUtils.batchCombine(center, BooleanOp.OR, true, shapes);
        });
    }
}
