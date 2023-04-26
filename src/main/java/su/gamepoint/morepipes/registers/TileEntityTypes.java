package su.gamepoint.morepipes.registers;

import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import su.gamepoint.morepipes.MorePipesMod;
import su.gamepoint.morepipes.common.block.transmitter.cable.MoreTileEntityUniversalCable;
import su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter.MoreTileEntityLogisticalTransporter;
import su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter.MoreTileEntityLogisticalTransporterBase;
import su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter.MoreTileEntityTransmitter;
import su.gamepoint.morepipes.common.block.transmitter.pipe.MoreTileEntityMechanicalPipe;

/**
 * @author Dudko Roman
 */
public class TileEntityTypes {

    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MorePipesMod.MODID);

    private static <BE extends TileEntityTransmitter> TileEntityTypeRegistryObject<BE> registerTransmitter(BlockRegistryObject<?, ?> block,
                                                                                                           BlockEntityType.BlockEntitySupplier<? extends BE> factory) {
        //Note: There is no data fixer type as forge does not currently have a way exposing data fixers to mods yet
        return TILE_ENTITY_TYPES.<BE>builder(block, factory).serverTicker(TileEntityTransmitter::tickServer).build();
    }

    public static final TileEntityTypeRegistryObject<MoreTileEntityUniversalCable> ABSOLUTE_UNIVERSAL_CABLE = registerTransmitter(BlockRegister.ABSOLUTE_UNIVERSAL_CABLE, (pos, state) -> new MoreTileEntityUniversalCable(BlockRegister.ABSOLUTE_UNIVERSAL_CABLE, pos, state));
    public static final TileEntityTypeRegistryObject<MoreTileEntityUniversalCable> SUPREME_UNIVERSAL_CABLE = registerTransmitter(BlockRegister.SUPREME_UNIVERSAL_CABLE, (pos, state) -> new MoreTileEntityUniversalCable(BlockRegister.SUPREME_UNIVERSAL_CABLE, pos, state));
    public static final TileEntityTypeRegistryObject<MoreTileEntityUniversalCable> COSMIC_UNIVERSAL_CABLE = registerTransmitter(BlockRegister.COSMIC_UNIVERSAL_CABLE, (pos, state) -> new MoreTileEntityUniversalCable(BlockRegister.COSMIC_UNIVERSAL_CABLE, pos, state));
    public static final TileEntityTypeRegistryObject<MoreTileEntityUniversalCable> INFINITE_UNIVERSAL_CABLE = registerTransmitter(BlockRegister.INFINITE_UNIVERSAL_CABLE, (pos, state) -> new MoreTileEntityUniversalCable(BlockRegister.INFINITE_UNIVERSAL_CABLE, pos, state));

    public static final TileEntityTypeRegistryObject<MoreTileEntityLogisticalTransporter> ABSOLUTE_LOGISTICAL_TRANSPORTER = TILE_ENTITY_TYPES.builder(BlockRegister.ABSOLUTE_LOGISTICAL_TRANSPORTER, (pos, state) -> new MoreTileEntityLogisticalTransporter(BlockRegister.ABSOLUTE_LOGISTICAL_TRANSPORTER, pos, state)).clientTicker(MoreTileEntityLogisticalTransporterBase::tickClient).serverTicker(MoreTileEntityTransmitter::moreTickServer).build();
    public static final TileEntityTypeRegistryObject<MoreTileEntityLogisticalTransporter> SUPREME_LOGISTICAL_TRANSPORTER = TILE_ENTITY_TYPES.builder(BlockRegister.SUPREME_LOGISTICAL_TRANSPORTER, (pos, state) -> new MoreTileEntityLogisticalTransporter(BlockRegister.SUPREME_LOGISTICAL_TRANSPORTER, pos, state)).clientTicker(MoreTileEntityLogisticalTransporterBase::tickClient).serverTicker(MoreTileEntityTransmitter::moreTickServer).build();
    public static final TileEntityTypeRegistryObject<MoreTileEntityLogisticalTransporter> COSMIC_LOGISTICAL_TRANSPORTER = TILE_ENTITY_TYPES.builder(BlockRegister.COSMIC_LOGISTICAL_TRANSPORTER, (pos, state) -> new MoreTileEntityLogisticalTransporter(BlockRegister.COSMIC_LOGISTICAL_TRANSPORTER, pos, state)).clientTicker(MoreTileEntityLogisticalTransporterBase::tickClient).serverTicker(MoreTileEntityTransmitter::moreTickServer).build();
    public static final TileEntityTypeRegistryObject<MoreTileEntityLogisticalTransporter> INFINITE_LOGISTICAL_TRANSPORTER = TILE_ENTITY_TYPES.builder(BlockRegister.INFINITE_LOGISTICAL_TRANSPORTER, (pos, state) -> new MoreTileEntityLogisticalTransporter(BlockRegister.INFINITE_LOGISTICAL_TRANSPORTER, pos, state)).clientTicker(MoreTileEntityLogisticalTransporterBase::tickClient).serverTicker(MoreTileEntityTransmitter::moreTickServer).build();

    public static final TileEntityTypeRegistryObject<MoreTileEntityMechanicalPipe> ABSOLUTE_MECHANICAL_PIPE = registerTransmitter(BlockRegister.ABSOLUTE_MECHANICAL_PIPE, (pos, state) -> new MoreTileEntityMechanicalPipe(BlockRegister.ABSOLUTE_MECHANICAL_PIPE, pos, state));
    public static final TileEntityTypeRegistryObject<MoreTileEntityMechanicalPipe> SUPREME_MECHANICAL_PIPE = registerTransmitter(BlockRegister.SUPREME_MECHANICAL_PIPE, (pos, state) -> new MoreTileEntityMechanicalPipe(BlockRegister.SUPREME_MECHANICAL_PIPE, pos, state));
    public static final TileEntityTypeRegistryObject<MoreTileEntityMechanicalPipe> COSMIC_MECHANICAL_PIPE = registerTransmitter(BlockRegister.COSMIC_MECHANICAL_PIPE, (pos, state) -> new MoreTileEntityMechanicalPipe(BlockRegister.COSMIC_MECHANICAL_PIPE, pos, state));
    public static final TileEntityTypeRegistryObject<MoreTileEntityMechanicalPipe> INFINITE_MECHANICAL_PIPE = registerTransmitter(BlockRegister.INFINITE_MECHANICAL_PIPE, (pos, state) -> new MoreTileEntityMechanicalPipe(BlockRegister.INFINITE_MECHANICAL_PIPE, pos, state));

}
