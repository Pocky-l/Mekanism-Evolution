package su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter;

import mekanism.common.tier.TransporterTier;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;

/**
 * @author Dudko Roman
 */
public class LTTier {
    public static int getSpeed(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> MekanismEvolutionMod.getConfig().absoluteLogisticalTransporterSpeed.get().intValue();
            case ADVANCED -> MekanismEvolutionMod.getConfig().supremeLogisticalTransporterSpeed.get().intValue();
            case ELITE -> MekanismEvolutionMod.getConfig().cosmicLogisticalTransporterSpeed.get().intValue();
            case ULTIMATE -> MekanismEvolutionMod.getConfig().infiniteLogisticalTransporterSpeed.get().intValue();
        };
    }

    public static int getPullAmount(TransporterTier tier) {
        return switch (tier) {
            case BASIC -> MekanismEvolutionMod.getConfig().absoluteLogisticalTransporterPullAmount.get().intValue();
            case ADVANCED -> MekanismEvolutionMod.getConfig().supremeLogisticalTransporterPullAmount.get().intValue();
            case ELITE -> MekanismEvolutionMod.getConfig().cosmicLogisticalTransporterPullAmount.get().intValue();
            case ULTIMATE -> MekanismEvolutionMod.getConfig().infiniteLogisticalTransporterPullAmount.get().intValue();
        };
    }
}
