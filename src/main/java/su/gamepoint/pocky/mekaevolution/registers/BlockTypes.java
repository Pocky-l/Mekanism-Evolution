package su.gamepoint.pocky.mekaevolution.registers;

import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.*;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tier.EnergyCubeTier;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.ECTier;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoTileEntityEnergyCube;
import su.gamepoint.pocky.mekaevolution.utils.EvoFloatingLong;

import java.util.function.Supplier;

/**
 * @author Dudko Roman
 */
public class BlockTypes {

    public static final Machine<EvoTileEntityEnergyCube> ABSOLUTE_ENERGY_CUBE = createEnergyCube(EnergyCubeTier.BASIC, () -> TileEntityTypes.ABSOLUTE_ENERGY_CUBE);
    public static final Machine<EvoTileEntityEnergyCube> SUPREME_ENERGY_CUBE = createEnergyCube(EnergyCubeTier.ADVANCED, () -> TileEntityTypes.SUPREME_ENERGY_CUBE);
    public static final Machine<EvoTileEntityEnergyCube> COSMIC_ENERGY_CUBE = createEnergyCube(EnergyCubeTier.ELITE, () -> TileEntityTypes.COSMIC_ENERGY_CUBE);
    public static final Machine<EvoTileEntityEnergyCube> INFINITE_ENERGY_CUBE = createEnergyCube(EnergyCubeTier.ULTIMATE, () -> TileEntityTypes.INFINITE_ENERGY_CUBE);

    private static <TILE extends EvoTileEntityEnergyCube> Machine<TILE> createEnergyCube(EnergyCubeTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile) {
        return Machine.MachineBuilder.createMachine(tile, MekanismLang.DESCRIPTION_ENERGY_CUBE)
                .withGui(() -> EvoContainerTypes.ENERGY_CUBE)
                .withEnergyConfig(new EvoFloatingLong(ECTier.getMaxEnergy(tier)))
                .with(new AttributeTier<>(tier), new AttributeStateFacing(BlockStateProperties.FACING))
                .without(AttributeParticleFX.class, AttributeStateActive.class, AttributeUpgradeSupport.class)
                .withComputerSupport(tier, "EnergyCube")
                .build();
    }
}
