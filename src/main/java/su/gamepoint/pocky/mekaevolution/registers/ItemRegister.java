package su.gamepoint.pocky.mekaevolution.registers;

import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.common.item.EvoItemQIODrive;
import su.gamepoint.pocky.mekaevolution.common.item.EvoQIODriveTier;

import java.util.Locale;

/**
 * @author Dudko Roman
 */
public class ItemRegister {

    public static final ItemDeferredRegister REGISTRY_ITEM = new ItemDeferredRegister(MekanismEvolutionMod.MODID);

    public static final ItemRegistryObject<EvoItemQIODrive> ABSOLUTE_QIO_DRIVE = registerQIODrive(EvoQIODriveTier.ABSOLUTE);
    public static final ItemRegistryObject<EvoItemQIODrive> SUPREME_QIO_DRIVE = registerQIODrive(EvoQIODriveTier.SUPREME);
    public static final ItemRegistryObject<EvoItemQIODrive> COSMIC_QIO_DRIVE = registerQIODrive(EvoQIODriveTier.COSMIC);
    public static final ItemRegistryObject<EvoItemQIODrive> INFINITE_QIO_DRIVE = registerQIODrive(EvoQIODriveTier.INFINITE);

    private static ItemRegistryObject<EvoItemQIODrive> registerQIODrive(EvoQIODriveTier tier) {
        return REGISTRY_ITEM.register("qio_drive_" + tier.name().toLowerCase(Locale.ROOT), properties -> new EvoItemQIODrive(tier, properties));
    }
}
