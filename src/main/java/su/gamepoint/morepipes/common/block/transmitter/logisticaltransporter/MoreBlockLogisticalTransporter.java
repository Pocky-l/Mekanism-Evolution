package su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter;

import mekanism.common.block.attribute.AttributeTier;
import mekanism.common.block.interfaces.IHasTileEntity;
import mekanism.common.block.interfaces.ITypeBlock;
import mekanism.common.block.transmitter.BlockLargeTransmitter;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tier.TransporterTier;
import su.gamepoint.morepipes.registers.TileEntityTypes;

/**
 * @author Dudko Roman
 */
public class MoreBlockLogisticalTransporter extends BlockLargeTransmitter implements IHasTileEntity<MoreTileEntityLogisticalTransporterBase>, ITypeBlock {

    private final TransporterTier tier;

    public MoreBlockLogisticalTransporter(TransporterTier tier) {
        this.tier = tier;
    }

    public BlockType getType() {
        return AttributeTier.getPassthroughType(this.tier);
    }

    public TileEntityTypeRegistryObject<MoreTileEntityLogisticalTransporter> getTileType() {
        return switch (this.tier) {
            case BASIC -> TileEntityTypes.ABSOLUTE_LOGISTICAL_TRANSPORTER;
            case ADVANCED -> TileEntityTypes.SUPREME_LOGISTICAL_TRANSPORTER;
            case ELITE -> TileEntityTypes.COSMIC_LOGISTICAL_TRANSPORTER;
            case ULTIMATE -> TileEntityTypes.INFINITE_LOGISTICAL_TRANSPORTER;
        };
    }
}
