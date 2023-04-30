package su.gamepoint.pocky.mekaevolution.common.block.storages.energycube;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mekanism.api.IContentsListener;
import mekanism.api.NBTConstants;
import mekanism.api.RelativeSide;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tier.EnergyCubeTier;
import mekanism.common.tile.base.SubstanceType;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.upgrade.EnergyCubeUpgradeData;
import mekanism.common.upgrade.IUpgradeData;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Dudko Roman
 */
public class EvoTileEntityEnergyCube extends TileEntityConfigurableMachine {

    /**
     * This Energy Cube's tier.
     */
    private EnergyCubeTier tier;
    private float prevScale;

    private EvoEnergyCubeEnergyContainer energyContainer;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getChargeItem")
    private EnergyInventorySlot chargeSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getDischargeItem")
    private EnergyInventorySlot dischargeSlot;

    /**
     * A block used to store and transfer electricity.
     */
    public EvoTileEntityEnergyCube(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
        configComponent = new TileComponentConfig(this, TransmissionType.ENERGY, TransmissionType.ITEM);
        configComponent.setupIOConfig(TransmissionType.ITEM, chargeSlot, dischargeSlot, RelativeSide.FRONT, true).setCanEject(false);
        configComponent.setupIOConfig(TransmissionType.ENERGY, energyContainer, RelativeSide.FRONT).setEjecting(true);
        ejectorComponent = new TileComponentEjector(this, () -> ECTier.getOutput(tier));
        ejectorComponent.setOutputData(configComponent, TransmissionType.ENERGY).setCanEject(type -> MekanismUtils.canFunction(this));
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        tier = Attribute.getTier(getBlockType(), EnergyCubeTier.class);
    }

    @Nonnull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addContainer(energyContainer = EvoEnergyCubeEnergyContainer.create(tier, listener));
        return builder.build();
    }

    @Nonnull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addSlot(dischargeSlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 17, 35));
        builder.addSlot(chargeSlot = EnergyInventorySlot.drain(energyContainer, listener, 143, 35));
        dischargeSlot.setSlotOverlay(SlotOverlay.MINUS);
        chargeSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    public EnergyCubeTier getTier() {
        return tier;
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        chargeSlot.drainContainer();
        dischargeSlot.fillContainerOrConvert();
        float newScale = MekanismUtils.getScale(prevScale, energyContainer);
        if (newScale != prevScale) {
            prevScale = newScale;
            sendUpdatePacket();
        }
    }

    @Override
    public int getRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(energyContainer.getEnergy(), energyContainer.getMaxEnergy());
    }

    @Override
    protected boolean makesComparatorDirty(@Nullable SubstanceType type) {
        return type == SubstanceType.ENERGY;
    }

    @Override
    public void parseUpgradeData(@Nonnull IUpgradeData upgradeData) {
        if (upgradeData instanceof EnergyCubeUpgradeData data) {
            redstone = data.redstone;
            setControlType(data.controlType);
            getEnergyContainer().setEnergy(data.energyContainer.getEnergy());
            chargeSlot.setStack(data.chargeSlot.getStack());
            //Copy the contents using NBT so that if it is not actually valid due to a reload we don't crash
            dischargeSlot.deserializeNBT(data.dischargeSlot.serializeNBT());
            for (ITileComponent component : getComponents()) {
                component.read(data.components);
            }
        } else {
            super.parseUpgradeData(upgradeData);
        }
    }

    public EvoEnergyCubeEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    @Nonnull
    @Override
    public EnergyCubeUpgradeData getUpgradeData() {
        return new EnergyCubeUpgradeData(redstone, getControlType(), getEnergyContainer(), chargeSlot, dischargeSlot, getComponents());
    }

    public float getEnergyScale() {
        return prevScale;
    }

    @Nonnull
    @Override
    public CompoundTag getReducedUpdateTag() {
        CompoundTag updateTag = super.getReducedUpdateTag();
        updateTag.putFloat(NBTConstants.SCALE, prevScale);
        return updateTag;
    }

    @Override
    public void handleUpdateTag(@Nonnull CompoundTag tag) {
        super.handleUpdateTag(tag);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE, scale -> prevScale = scale);
    }
}
