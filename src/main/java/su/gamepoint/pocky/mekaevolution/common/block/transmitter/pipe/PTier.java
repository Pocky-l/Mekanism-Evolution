package su.gamepoint.pocky.mekaevolution.common.block.transmitter.pipe;

import mekanism.common.tier.PipeTier;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;

/**
 * @author Dudko Roman
 */
public class PTier {
    public static int getPipePullAmount(PipeTier tier) {
        return switch (tier) {
            case BASIC -> MekanismEvolutionMod.getConfig().absoluteMechanicalPipePullAmount.get().intValue();
            case ADVANCED -> MekanismEvolutionMod.getConfig().supremeMechanicalPipePullAmount.get().intValue();
            case ELITE -> MekanismEvolutionMod.getConfig().cosmicMechanicalPipePullAmount.get().intValue();
            case ULTIMATE -> MekanismEvolutionMod.getConfig().infiniteMechanicalPipePullAmount.get().intValue();
        };
    }

    public static long getPipeCapacity(PipeTier tier) {
        return switch (tier) {
            case BASIC -> MekanismEvolutionMod.getConfig().absoluteMechanicalPipeCapacity.get().longValue();
            case ADVANCED -> MekanismEvolutionMod.getConfig().supremeMechanicalPipeCapacity.get().longValue();
            case ELITE -> MekanismEvolutionMod.getConfig().cosmicMechanicalPipeCapacity.get().longValue();
            case ULTIMATE -> MekanismEvolutionMod.getConfig().infiniteMechanicalPipeCapacity.get().longValue();
        };
    }
}
