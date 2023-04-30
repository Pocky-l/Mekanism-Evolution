package su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter;

import mekanism.api.providers.IBlockProvider;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Dudko Roman
 */
public abstract class EvoTileEntityTransmitter extends TileEntityTransmitter {
    public EvoTileEntityTransmitter(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    public static void moreTickServer(Level level, BlockPos blockPos, BlockState blockState, EvoTileEntityLogisticalTransporter evoTileEntityLogisticalTransporter) {
        evoTileEntityLogisticalTransporter.onUpdateServer();
    }
}
