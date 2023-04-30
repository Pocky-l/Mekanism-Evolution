package su.gamepoint.pocky.mekaevolution.common.block.transmitter.pipe;

import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.tier.BaseTier;
import mekanism.common.block.states.TransmitterType;
import mekanism.common.capabilities.fluid.DynamicFluidHandler;
import mekanism.common.capabilities.resolver.manager.FluidHandlerManager;
import mekanism.common.content.network.FluidNetwork;
import mekanism.common.integration.computer.ComputerCapabilityHelper;
import mekanism.common.integration.computer.IComputerTile;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.lib.transmitter.ConnectionType;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter.EvoTileEntityTransmitter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Dudko Roman
 */
public class EvoTileEntityMechanicalPipe extends EvoTileEntityTransmitter implements IComputerTile {

    private final FluidHandlerManager fluidHandlerManager;

    public EvoTileEntityMechanicalPipe(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
        this.addCapabilityResolver(this.fluidHandlerManager = new FluidHandlerManager((direction) -> {
            EvoMechanicalPipe pipe = this.getTransmitter();
            return direction != null && pipe.getConnectionTypeRaw(direction) == ConnectionType.NONE ? Collections.emptyList() : pipe.getFluidTanks(direction);
        }, new DynamicFluidHandler(this::getFluidTanks, this.getExtractPredicate(), this.getInsertPredicate(), null)));
        ComputerCapabilityHelper.addComputerCapabilities(this, this::addCapabilityResolver);
    }

    protected EvoMechanicalPipe createTransmitter(IBlockProvider blockProvider) {
        return new EvoMechanicalPipe(blockProvider, this);
    }

    public EvoMechanicalPipe getTransmitter() {
        return (EvoMechanicalPipe)super.getTransmitter();
    }

    protected void onUpdateServer() {
        this.getTransmitter().pullFromAcceptors();
        super.onUpdateServer();
    }

    public TransmitterType getTransmitterType() {
        return TransmitterType.MECHANICAL_PIPE;
    }

    @Nonnull
    protected BlockState upgradeResult(@Nonnull BlockState current, @Nonnull BaseTier tier) {
        return current;
    }

    @Nonnull
    public CompoundTag getUpdateTag() {
        CompoundTag updateTag = super.getUpdateTag();
        if (this.getTransmitter().hasTransmitterNetwork()) {
            FluidNetwork network = this.getTransmitter().getTransmitterNetwork();
            updateTag.put("fluid", network.lastFluid.writeToNBT(new CompoundTag()));
            updateTag.putFloat("scale", network.currentScale);
        }

        return updateTag;
    }

    private List<IExtendedFluidTank> getFluidTanks(@Nullable Direction side) {
        return this.fluidHandlerManager.getContainers(side);
    }

    public void sideChanged(@Nonnull Direction side, @Nonnull ConnectionType old, @Nonnull ConnectionType type) {
        super.sideChanged(side, old, type);
        if (type == ConnectionType.NONE) {
            this.invalidateCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
            WorldUtils.notifyNeighborOfChange(this.level, side, this.worldPosition);
        } else if (old == ConnectionType.NONE) {
            WorldUtils.notifyNeighborOfChange(this.level, side, this.worldPosition);
        }

    }

    public String getComputerName() {
        return this.getTransmitter().getTier().getBaseTier().getLowerName() + "MechanicalPipe";
    }

    @ComputerMethod
    private FluidStack getBuffer() {
        return this.getTransmitter().getBufferWithFallback();
    }

    @ComputerMethod
    private long getCapacity() {
        EvoMechanicalPipe pipe = this.getTransmitter();
        return pipe.hasTransmitterNetwork() ? pipe.getTransmitterNetwork().getCapacity() : pipe.getCapacity();
    }

    @ComputerMethod
    private long getNeeded() {
        return this.getCapacity() - (long)this.getBuffer().getAmount();
    }

    @ComputerMethod
    private double getFilledPercentage() {
        return (double)this.getBuffer().getAmount() / (double)this.getCapacity();
    }
}
