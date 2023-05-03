package su.gamepoint.pocky.mekaevolution.common.item;

import mekanism.api.tier.BaseTier;

/**
 * @author Dudko Roman
 */
public enum EvoQIODriveTier {

    ABSOLUTE(BaseTier.BASIC, 128_000_000_000L, 32768),
    SUPREME(BaseTier.ADVANCED, 1_048_000_000_000L, 131072),
    COSMIC(BaseTier.ELITE, 8_000_000_000_000L, 524288),
    INFINITE(BaseTier.ULTIMATE, 16_000_000_000_000L, 2097152);

    private final BaseTier baseTier;
    private final long count;
    private final int types;

    EvoQIODriveTier(BaseTier tier, long count, int types) {
        this.baseTier = tier;
        this.count = count;
        this.types = types;
    }

    public BaseTier getBaseTier() {
        return this.baseTier;
    }

    public long getMaxCount() {
        return this.count;
    }

    public int getMaxTypes() {
        return this.types;
    }
}
