package su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter;

import mekanism.api.providers.IBlockProvider;
import mekanism.common.content.network.transmitter.LogisticalTransporterBase;
import mekanism.common.content.transporter.TransporterStack;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import mekanism.common.util.TransporterUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;

/**
 * @author Dudko Roman
 */
public abstract class EvoTileEntityLogisticalTransporterBase extends TileEntityTransmitter {

    protected EvoTileEntityLogisticalTransporterBase(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    protected abstract LogisticalTransporterBase createTransmitter(IBlockProvider var1);

    public EvoLogisticalTransporterBase getTransmitter() {
        return (EvoLogisticalTransporterBase)super.getTransmitter();
    }

    public static void tickClient(Level level, BlockPos pos, BlockState state, EvoTileEntityLogisticalTransporterBase transmitter) {
        transmitter.getTransmitter().onUpdateClient();
    }

    public void onUpdateServer() {
        super.onUpdateServer();
        this.getTransmitter().onUpdateServer();
    }

    public void blockRemoved() {
        super.blockRemoved();
        if (!this.isRemote()) {
            LogisticalTransporterBase transporter = this.getTransmitter();
            if (!transporter.isUpgrading()) {
                Iterator var2 = transporter.getTransit().iterator();

                while(var2.hasNext()) {
                    TransporterStack stack = (TransporterStack)var2.next();
                    TransporterUtils.drop(transporter, stack);
                }
            }
        }

    }
}
