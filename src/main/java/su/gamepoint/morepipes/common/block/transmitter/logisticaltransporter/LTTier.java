package su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter;

import mekanism.common.tier.TransporterTier;
import su.gamepoint.morepipes.MorePipesMod;

/**
 * @author Dudko Roman
 */
public class LTTier {
    public static int getSpeed(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> MorePipesMod.getConfig().absoluteLogisticalTransporterSpeed.get().intValue();
            case ADVANCED -> MorePipesMod.getConfig().supremeLogisticalTransporterSpeed.get().intValue();
            case ELITE -> MorePipesMod.getConfig().cosmicLogisticalTransporterSpeed.get().intValue();
            case ULTIMATE -> MorePipesMod.getConfig().infiniteLogisticalTransporterSpeed.get().intValue();
        };
    }

    public static int getPullAmount(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> MorePipesMod.getConfig().absoluteLogisticalTransporterPullAmount.get().intValue();
            case ADVANCED -> MorePipesMod.getConfig().supremeLogisticalTransporterPullAmount.get().intValue();
            case ELITE -> MorePipesMod.getConfig().cosmicLogisticalTransporterPullAmount.get().intValue();
            case ULTIMATE -> MorePipesMod.getConfig().infiniteLogisticalTransporterPullAmount.get().intValue();
        };
    }
}
