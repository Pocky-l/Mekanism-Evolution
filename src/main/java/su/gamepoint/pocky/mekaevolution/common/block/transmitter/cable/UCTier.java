package su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable;

import mekanism.api.math.FloatingLong;
import mekanism.common.tier.CableTier;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;

/**
 * @author Dudko Roman
 */
public class UCTier {
    public static FloatingLong getCapacityAsFloatingLong(CableTier tier) {
        if (tier == null) return FloatingLong.create(8000L);
        return switch (tier) {
            case BASIC -> MekanismEvolutionMod.getConfig().absoluteUniversalCableCapacity.get();
            case ADVANCED -> MekanismEvolutionMod.getConfig().supremeUniversalCableCapacity.get();
            case ELITE -> MekanismEvolutionMod.getConfig().cosmicUniversalCableCapacity.get();
            case ULTIMATE -> MekanismEvolutionMod.getConfig().infiniteUniversalCableCapacity.get();
        };
    }
}
