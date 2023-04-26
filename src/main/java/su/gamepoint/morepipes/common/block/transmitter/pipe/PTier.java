package su.gamepoint.morepipes.common.block.transmitter.pipe;

import mekanism.common.tier.PipeTier;

/**
 * @author Dudko Roman
 */
public class PTier {
    public static int getPipePullAmount(PipeTier tier) {
        return switch (tier) {
            case BASIC -> 128000;
            case ADVANCED -> 512000;
            case ELITE -> 2048000;
            case ULTIMATE -> 8192000;
        };
    }

    public static long getPipeCapacity(PipeTier tier) {
        return switch (tier) {
            case BASIC -> 512000L;
            case ADVANCED -> 2048000L;
            case ELITE -> 8192000L;
            case ULTIMATE -> 32768000L;
        };
    }
}
