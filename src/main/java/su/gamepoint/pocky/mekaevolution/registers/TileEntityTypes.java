package su.gamepoint.pocky.mekaevolution.registers;

import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoTileEntityEnergyCube;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable.EvoTileEntityUniversalCable;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter.EvoTileEntityLogisticalTransporter;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter.EvoTileEntityLogisticalTransporterBase;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter.EvoTileEntityTransmitter;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.pipe.EvoTileEntityMechanicalPipe;

/**
 * @author Dudko Roman
 */
public class TileEntityTypes {

    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MekanismEvolutionMod.MODID);

    private static <BE extends TileEntityTransmitter> TileEntityTypeRegistryObject<BE> registerTransmitter(BlockRegistryObject<?, ?> block,
                                                                                                           BlockEntityType.BlockEntitySupplier<? extends BE> factory) {
        //Note: There is no data fixer type as forge does not currently have a way exposing data fixers to mods yet
        return TILE_ENTITY_TYPES.<BE>builder(block, factory).serverTicker(TileEntityTransmitter::tickServer).build();
    }

    public static final TileEntityTypeRegistryObject<EvoTileEntityUniversalCable> ABSOLUTE_UNIVERSAL_CABLE = registerTransmitter(BlockRegister.ABSOLUTE_UNIVERSAL_CABLE, (pos, state) -> new EvoTileEntityUniversalCable(BlockRegister.ABSOLUTE_UNIVERSAL_CABLE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityUniversalCable> SUPREME_UNIVERSAL_CABLE = registerTransmitter(BlockRegister.SUPREME_UNIVERSAL_CABLE, (pos, state) -> new EvoTileEntityUniversalCable(BlockRegister.SUPREME_UNIVERSAL_CABLE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityUniversalCable> COSMIC_UNIVERSAL_CABLE = registerTransmitter(BlockRegister.COSMIC_UNIVERSAL_CABLE, (pos, state) -> new EvoTileEntityUniversalCable(BlockRegister.COSMIC_UNIVERSAL_CABLE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityUniversalCable> INFINITE_UNIVERSAL_CABLE = registerTransmitter(BlockRegister.INFINITE_UNIVERSAL_CABLE, (pos, state) -> new EvoTileEntityUniversalCable(BlockRegister.INFINITE_UNIVERSAL_CABLE, pos, state));

    public static final TileEntityTypeRegistryObject<EvoTileEntityLogisticalTransporter> ABSOLUTE_LOGISTICAL_TRANSPORTER = TILE_ENTITY_TYPES.builder(BlockRegister.ABSOLUTE_LOGISTICAL_TRANSPORTER, (pos, state) -> new EvoTileEntityLogisticalTransporter(BlockRegister.ABSOLUTE_LOGISTICAL_TRANSPORTER, pos, state)).clientTicker(EvoTileEntityLogisticalTransporterBase::tickClient).serverTicker(EvoTileEntityTransmitter::evoTickServer).build();
    public static final TileEntityTypeRegistryObject<EvoTileEntityLogisticalTransporter> SUPREME_LOGISTICAL_TRANSPORTER = TILE_ENTITY_TYPES.builder(BlockRegister.SUPREME_LOGISTICAL_TRANSPORTER, (pos, state) -> new EvoTileEntityLogisticalTransporter(BlockRegister.SUPREME_LOGISTICAL_TRANSPORTER, pos, state)).clientTicker(EvoTileEntityLogisticalTransporterBase::tickClient).serverTicker(EvoTileEntityTransmitter::evoTickServer).build();
    public static final TileEntityTypeRegistryObject<EvoTileEntityLogisticalTransporter> COSMIC_LOGISTICAL_TRANSPORTER = TILE_ENTITY_TYPES.builder(BlockRegister.COSMIC_LOGISTICAL_TRANSPORTER, (pos, state) -> new EvoTileEntityLogisticalTransporter(BlockRegister.COSMIC_LOGISTICAL_TRANSPORTER, pos, state)).clientTicker(EvoTileEntityLogisticalTransporterBase::tickClient).serverTicker(EvoTileEntityTransmitter::evoTickServer).build();
    public static final TileEntityTypeRegistryObject<EvoTileEntityLogisticalTransporter> INFINITE_LOGISTICAL_TRANSPORTER = TILE_ENTITY_TYPES.builder(BlockRegister.INFINITE_LOGISTICAL_TRANSPORTER, (pos, state) -> new EvoTileEntityLogisticalTransporter(BlockRegister.INFINITE_LOGISTICAL_TRANSPORTER, pos, state)).clientTicker(EvoTileEntityLogisticalTransporterBase::tickClient).serverTicker(EvoTileEntityTransmitter::evoTickServer).build();

    public static final TileEntityTypeRegistryObject<EvoTileEntityMechanicalPipe> ABSOLUTE_MECHANICAL_PIPE = registerTransmitter(BlockRegister.ABSOLUTE_MECHANICAL_PIPE, (pos, state) -> new EvoTileEntityMechanicalPipe(BlockRegister.ABSOLUTE_MECHANICAL_PIPE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityMechanicalPipe> SUPREME_MECHANICAL_PIPE = registerTransmitter(BlockRegister.SUPREME_MECHANICAL_PIPE, (pos, state) -> new EvoTileEntityMechanicalPipe(BlockRegister.SUPREME_MECHANICAL_PIPE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityMechanicalPipe> COSMIC_MECHANICAL_PIPE = registerTransmitter(BlockRegister.COSMIC_MECHANICAL_PIPE, (pos, state) -> new EvoTileEntityMechanicalPipe(BlockRegister.COSMIC_MECHANICAL_PIPE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityMechanicalPipe> INFINITE_MECHANICAL_PIPE = registerTransmitter(BlockRegister.INFINITE_MECHANICAL_PIPE, (pos, state) -> new EvoTileEntityMechanicalPipe(BlockRegister.INFINITE_MECHANICAL_PIPE, pos, state));

    public static final TileEntityTypeRegistryObject<EvoTileEntityEnergyCube> ABSOLUTE_ENERGY_CUBE = TILE_ENTITY_TYPES.register(BlockRegister.ABSOLUTE_ENERGY_CUBE, (pos, state) -> new EvoTileEntityEnergyCube(BlockRegister.ABSOLUTE_ENERGY_CUBE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityEnergyCube> SUPREME_ENERGY_CUBE = TILE_ENTITY_TYPES.register(BlockRegister.SUPREME_ENERGY_CUBE, (pos, state) -> new EvoTileEntityEnergyCube(BlockRegister.SUPREME_ENERGY_CUBE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityEnergyCube> COSMIC_ENERGY_CUBE = TILE_ENTITY_TYPES.register(BlockRegister.COSMIC_ENERGY_CUBE, (pos, state) -> new EvoTileEntityEnergyCube(BlockRegister.COSMIC_ENERGY_CUBE, pos, state));
    public static final TileEntityTypeRegistryObject<EvoTileEntityEnergyCube> INFINITE_ENERGY_CUBE = TILE_ENTITY_TYPES.register(BlockRegister.INFINITE_ENERGY_CUBE, (pos, state) -> new EvoTileEntityEnergyCube(BlockRegister.INFINITE_ENERGY_CUBE, pos, state));
}
