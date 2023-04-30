package su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter;

import mekanism.common.block.attribute.AttributeTier;
import mekanism.common.block.interfaces.IHasTileEntity;
import mekanism.common.block.interfaces.ITypeBlock;
import mekanism.common.block.transmitter.BlockLargeTransmitter;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tier.TransporterTier;
import su.gamepoint.pocky.mekaevolution.registers.TileEntityTypes;

/**
 * @author Dudko Roman
 */
public class EvoBlockLogisticalTransporter extends BlockLargeTransmitter implements IHasTileEntity<EvoTileEntityLogisticalTransporterBase>, ITypeBlock {

    private final TransporterTier tier;

    public EvoBlockLogisticalTransporter(TransporterTier tier) {
        this.tier = tier;
    }

    public BlockType getType() {
        return AttributeTier.getPassthroughType(this.tier);
    }

    public TileEntityTypeRegistryObject<EvoTileEntityLogisticalTransporter> getTileType() {
        return switch (this.tier) {
            case BASIC -> TileEntityTypes.ABSOLUTE_LOGISTICAL_TRANSPORTER;
            case ADVANCED -> TileEntityTypes.SUPREME_LOGISTICAL_TRANSPORTER;
            case ELITE -> TileEntityTypes.COSMIC_LOGISTICAL_TRANSPORTER;
            case ULTIMATE -> TileEntityTypes.INFINITE_LOGISTICAL_TRANSPORTER;
        };
    }
}
