package su.gamepoint.pocky.mekaevolution.utils;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeHooks;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Dudko Roman
 */
public class EvolutionUtils {
    public static void excavateArea(IEnergyContainer energyContainer, FloatingLong energyRequired, Level world, BlockPos centerPos, ServerPlayer player, ItemStack stack, Item usedTool,
                                    MekanismUtils.BlastEnergyFunction blastEnergy, MekanismUtils.VeinEnergyFunction veinEnergy, int width, int height, int depth) {
        final Set<BlockPos> positions = findPositions(centerPos, player.getDirection(), width, height, depth);

        FloatingLong energyUsed = FloatingLong.ZERO;
        FloatingLong energyAvailable = energyContainer.getEnergy();
        //Subtract from our available energy the amount that we will require to break the target block
        energyAvailable = energyAvailable.subtract(energyRequired);

        assert positions != null;
        for (BlockPos foundPos : positions) {

            if (centerPos.equals(foundPos)) {
                continue;
            }

            BlockState targetState = world.getBlockState(foundPos);
            if (targetState.isAir()) {
                continue;
            }

            float hardness = targetState.getDestroySpeed(world, foundPos);
            if (hardness == -1) {
                continue;
            }

            int distance = player.getOnPos().distManhattan(new Vec3i(foundPos.getX(), foundPos.getY(), foundPos.getZ()));
            FloatingLong destroyEnergy = distance == 0 ? blastEnergy.calc(hardness) : veinEnergy.calc(hardness, distance, targetState);
            if (energyUsed.add(destroyEnergy).greaterThan(energyAvailable)) {
                //If we don't have energy to break the block continue
                //Note: We do not break as given the energy scales with hardness, so it is possible we still have energy to break another block
                // Given we validate the blocks are the same but their block states may be different thus making them have different
                // block hardness values in a modded context
                continue;
            }
            int exp = ForgeHooks.onBlockBreakEvent(world, player.gameMode.getGameModeForPlayer(), player, foundPos);
            if (exp == -1) {
                //If we can't actually break the block continue (this allows mods to stop us from vein mining into protected land)
                continue;
            }
            //Otherwise, break the block
            Block block = targetState.getBlock();
            //Get the tile now so that we have it for when we try to harvest the block
            BlockEntity tileEntity = WorldUtils.getTileEntity(world, foundPos);
            //Remove the block
            if (targetState.onDestroyedByPlayer(world, foundPos, player, true, targetState.getFluidState())) {
                block.destroy(world, foundPos, targetState);
                //Harvest the block allowing it to handle block drops, incrementing block mined count, and adding exhaustion
                block.playerDestroy(world, player, foundPos, targetState, tileEntity, stack);
                player.awardStat(Stats.ITEM_USED.get(usedTool));
                if (exp > 0) {
                    //If we have xp drop it
                    block.popExperience((ServerLevel) world, foundPos, exp);
                }
                //Mark that we used that portion of the energy
                energyUsed = energyUsed.plusEqual(destroyEnergy);
            }
        }
        energyContainer.extract(energyUsed, Action.EXECUTE, AutomationType.MANUAL);
    }

    public static Set<BlockPos> findPositions(BlockPos center, Direction direction, int width, int height, int depth) {
        if (width % 2 == 0 || height % 2 == 0 || depth % 2 == 0) return null;

        int w = (width - 1) / 2;
        int h = (height - 1) / 2;

        AABB aabb = switch (direction) {
            case NORTH -> new AABB(center.getX() - w, center.getY() - h, center.getZ(), center.getX() + w, center.getY() + h, center.getZ() + (depth - 1));
            case EAST -> new AABB(center.getX(), center.getY() - h, center.getZ() - w, center.getX() + (depth - 1), center.getY() + h, center.getZ() + w);
            case WEST -> new AABB(center.getX(), center.getY() - h, center.getZ() + w, center.getX() - (depth - 1), center.getY() + h, center.getZ() - w);
            case SOUTH -> new AABB(center.getX() + w, center.getY() - h, center.getZ(), center.getX() - w, center.getY() + h, center.getZ() + (depth - 1));
            default -> null;
        };
        assert aabb != null;
        return BlockPos.betweenClosedStream(aabb).map(BlockPos::new).collect(Collectors.toUnmodifiableSet());
    }
}
