package su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter;

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
public class MoreTileEntityLogisticalTransporter extends MoreTileEntityLogisticalTransporterBase {

    public MoreTileEntityLogisticalTransporter(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public void onUpdateServer() {
        super.onUpdateServer();
    }

    protected MoreLogisticalTransporter createTransmitter(IBlockProvider blockProvider) {
        return new MoreLogisticalTransporter(blockProvider, this);
    }

    public MoreLogisticalTransporter getTransmitter() {
        return (MoreLogisticalTransporter)super.getTransmitter();
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
