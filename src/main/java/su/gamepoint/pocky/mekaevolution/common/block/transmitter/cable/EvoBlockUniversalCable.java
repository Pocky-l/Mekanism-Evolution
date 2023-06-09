package su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable;

import mekanism.common.block.attribute.AttributeTier;
import mekanism.common.block.interfaces.IHasTileEntity;
import mekanism.common.block.interfaces.ITypeBlock;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tier.CableTier;
import su.gamepoint.pocky.mekaevolution.registers.TileEntityTypes;

/**
 * @author Dudko Roman
 */
public class EvoBlockUniversalCable extends EvoBlockSmallTransmitter implements ITypeBlock, IHasTileEntity<EvoTileEntityUniversalCable> {

    private final CableTier tier;

    public EvoBlockUniversalCable(CableTier tier) {
        //super(Properties.of(Material.PISTON).strength(1.0F, 6.0F));
        this.tier = tier;
    }

    public EvoBlockUniversalCable(CableTier tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public BlockType getType() {
        return AttributeTier.getPassthroughType(tier);
    }

    @Override
    public TileEntityTypeRegistryObject<EvoTileEntityUniversalCable> getTileType() {

        return switch (tier) {
            case BASIC -> TileEntityTypes.ABSOLUTE_UNIVERSAL_CABLE;
            case ADVANCED -> TileEntityTypes.SUPREME_UNIVERSAL_CABLE;
            case ELITE -> TileEntityTypes.COSMIC_UNIVERSAL_CABLE;
            case ULTIMATE -> TileEntityTypes.INFINITE_UNIVERSAL_CABLE;
        };
    }
}
