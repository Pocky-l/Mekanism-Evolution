package su.gamepoint.morepipes.common.block.transmitter.cable;

import mekanism.api.math.FloatingLong;
import mekanism.common.tier.CableTier;
import su.gamepoint.morepipes.MorePipesMod;

/**
 * @author Dudko Roman
 */
public class UCTier {
    public static FloatingLong getCapacityAsFloatingLong(CableTier tier) {
        if (tier == null) return FloatingLong.create(8000L);
        return switch (tier) {
            case BASIC -> MorePipesMod.getConfig().absoluteUniversalCableCapacity.get();
            case ADVANCED -> MorePipesMod.getConfig().supremeUniversalCableCapacity.get();
            case ELITE -> MorePipesMod.getConfig().cosmicUniversalCableCapacity.get();
            case ULTIMATE -> MorePipesMod.getConfig().infiniteUniversalCableCapacity.get();
        };
    }
}
