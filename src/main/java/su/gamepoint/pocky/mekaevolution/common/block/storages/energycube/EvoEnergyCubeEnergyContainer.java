package su.gamepoint.pocky.mekaevolution.common.block.storages.energycube;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.tier.EnergyCubeTier;
import net.minecraft.MethodsReturnNonnullByDefault;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * @author Dudko Roman
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EvoEnergyCubeEnergyContainer extends BasicEnergyContainer {

    private final FloatingLong rate;

    public static EvoEnergyCubeEnergyContainer create(EnergyCubeTier tier, @Nullable IContentsListener listener) {
        Objects.requireNonNull(tier, "Energy cube tier cannot be null");
        return new EvoEnergyCubeEnergyContainer(tier, listener);
    }

    private EvoEnergyCubeEnergyContainer(EnergyCubeTier tier, @Nullable IContentsListener listener) {
        super(ECTier.getMaxEnergy(tier), alwaysTrue, alwaysTrue, listener);
        Objects.requireNonNull(tier);
        this.rate = ECTier.getOutput(tier);
    }

    protected @NotNull FloatingLong getRate(@Nullable AutomationType automationType) {
        return automationType == AutomationType.INTERNAL ? this.rate : super.getRate(automationType);
    }

    public @NotNull FloatingLong insert(@NotNull FloatingLong amount, Action action, @NotNull AutomationType automationType) {
        return super.insert(amount, action.combine(true), automationType);
    }

    public @NotNull FloatingLong extract(@NotNull FloatingLong amount, Action action, @NotNull AutomationType automationType) {
        return super.extract(amount, action.combine(true), automationType);
    }
}
