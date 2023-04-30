package su.gamepoint.pocky.mekaevolution.utils;

import mekanism.api.math.FloatingLong;
import mekanism.api.math.FloatingLongSupplier;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dudko Roman
 */
public class EvoFloatingLong implements FloatingLongSupplier {

    private final FloatingLong number;

    public EvoFloatingLong(FloatingLong number) {
        this.number = number;
    }

    @NotNull
    @Override
    public FloatingLong get() {
        return number;
    }
}
