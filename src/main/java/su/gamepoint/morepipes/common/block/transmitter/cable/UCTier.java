package su.gamepoint.morepipes.common.block.transmitter.cable;

import mekanism.api.math.FloatingLong;
import mekanism.common.tier.CableTier;

/**
 * @author Dudko Roman
 */
public class UCTier {
    public static FloatingLong getCapacityAsFloatingLong(CableTier tier) {
        if (tier == null) return FloatingLong.create(8000L);
        return switch (tier) {
            case BASIC -> FloatingLong.create(65536000L);
            case ADVANCED -> FloatingLong.create(524288000L);
            case ELITE -> FloatingLong.create(4194304000L);
            case ULTIMATE -> FloatingLong.create(33554432000L);
        };
    }
}
