package su.gamepoint.pocky.mekaevolution.common.block.transmitter.pipe;

import mekanism.common.block.attribute.AttributeTier;
import mekanism.common.block.interfaces.IHasTileEntity;
import mekanism.common.block.interfaces.ITypeBlock;
import mekanism.common.block.transmitter.BlockLargeTransmitter;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tier.PipeTier;
import su.gamepoint.pocky.mekaevolution.registers.TileEntityTypes;

/**
 * @author Dudko Roman
 */
public class EvoBlockMechanicalPipe extends BlockLargeTransmitter implements ITypeBlock, IHasTileEntity<EvoTileEntityMechanicalPipe> {

    private final PipeTier tier;

    public EvoBlockMechanicalPipe(PipeTier tier) {
        this.tier = tier;
    }

    public BlockType getType() {
        return AttributeTier.getPassthroughType(this.tier);
    }

    public TileEntityTypeRegistryObject<EvoTileEntityMechanicalPipe> getTileType() {

        return switch (this.tier) {
            case BASIC -> TileEntityTypes.ABSOLUTE_MECHANICAL_PIPE;
            case ADVANCED -> TileEntityTypes.SUPREME_MECHANICAL_PIPE;
            case ELITE -> TileEntityTypes.COSMIC_MECHANICAL_PIPE;
            case ULTIMATE -> TileEntityTypes.INFINITE_MECHANICAL_PIPE;
        };
    }
}
