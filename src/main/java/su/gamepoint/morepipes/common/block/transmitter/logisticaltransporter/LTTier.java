package su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter;

import mekanism.common.tier.TransporterTier;

/**
 * @author Dudko Roman
 */
public class LTTier {
    public static int getSpeed(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> 100;
            case ADVANCED -> 200;
            case ELITE -> 400;
            case ULTIMATE -> 800;
        };
    }

    public static int getPullAmount(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> 128;
            case ADVANCED -> 256;
            case ELITE -> 512;
            case ULTIMATE -> 1024;
        };
    }
}
