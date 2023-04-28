package su.gamepoint.morepipes.common.block.transmitter.pipe;

import mekanism.common.tier.PipeTier;
import su.gamepoint.morepipes.MorePipesMod;

/**
 * @author Dudko Roman
 */
public class PTier {
    public static int getPipePullAmount(PipeTier tier) {
        return switch (tier) {
            case BASIC -> MorePipesMod.getConfig().absoluteMechanicalPipePullAmount.get().intValue();
            case ADVANCED -> MorePipesMod.getConfig().supremeMechanicalPipePullAmount.get().intValue();
            case ELITE -> MorePipesMod.getConfig().cosmicMechanicalPipePullAmount.get().intValue();
            case ULTIMATE -> MorePipesMod.getConfig().infiniteMechanicalPipePullAmount.get().intValue();
        };
    }

    public static long getPipeCapacity(PipeTier tier) {
        return switch (tier) {
            case BASIC -> MorePipesMod.getConfig().absoluteMechanicalPipeCapacity.get().longValue();
            case ADVANCED -> MorePipesMod.getConfig().supremeMechanicalPipeCapacity.get().longValue();
            case ELITE -> MorePipesMod.getConfig().cosmicMechanicalPipeCapacity.get().longValue();
            case ULTIMATE -> MorePipesMod.getConfig().infiniteMechanicalPipeCapacity.get().longValue();
        };
    }
}
