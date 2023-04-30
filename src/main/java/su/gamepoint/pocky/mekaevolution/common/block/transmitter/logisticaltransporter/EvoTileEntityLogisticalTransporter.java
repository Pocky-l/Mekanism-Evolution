package su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter;

import mekanism.api.providers.IBlockProvider;
import mekanism.api.tier.BaseTier;
import mekanism.client.model.data.TransmitterModelData;
import mekanism.common.block.states.TransmitterType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

/**
 * @author Dudko Roman
 */
public class EvoTileEntityLogisticalTransporter extends EvoTileEntityLogisticalTransporterBase {

    public EvoTileEntityLogisticalTransporter(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public void onUpdateServer() {
        super.onUpdateServer();
    }

    protected EvoLogisticalTransporter createTransmitter(IBlockProvider blockProvider) {
        return new EvoLogisticalTransporter(blockProvider, this);
    }

    public EvoLogisticalTransporter getTransmitter() {
        return (EvoLogisticalTransporter)super.getTransmitter();
    }

    public TransmitterType getTransmitterType() {
        return TransmitterType.LOGISTICAL_TRANSPORTER;
    }

    protected void updateModelData(TransmitterModelData modelData) {
        super.updateModelData(modelData);
        modelData.setHasColor(this.getTransmitter().getColor() != null);
    }

    @Nonnull
    protected BlockState upgradeResult(@Nonnull BlockState current, @Nonnull BaseTier tier) {
        return current;
    }
}
