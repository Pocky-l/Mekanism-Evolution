package su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter;

import mekanism.api.NBTConstants;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.content.transporter.PathfinderCache;
import mekanism.common.tier.TransporterTier;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import mekanism.common.upgrade.transmitter.LogisticalTransporterUpgradeData;
import mekanism.common.upgrade.transmitter.TransmitterUpgradeData;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.NBTUtils;
import mekanism.common.util.TransporterUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Dudko Roman
 */
public class EvoLogisticalTransporter extends EvoLogisticalTransporterBase {

    private EnumColor color;

    public EvoLogisticalTransporter(IBlockProvider blockProvider, TileEntityTransmitter tile) {
        super(tile, Attribute.getTier(blockProvider, TransporterTier.class));
    }

    public TransporterTier getTier() {
        return this.tier;
    }

    public EnumColor getColor() {
        return this.color;
    }

    public void setColor(EnumColor c) {
        this.color = c;
    }

    public InteractionResult onConfigure(Player player, Direction side) {
        EvoTransporterUtils.incrementColor(this);

        PathfinderCache.onChanged(this.getTransmitterNetwork());
        this.getTransmitterTile().sendUpdatePacket();
        EnumColor color = this.getColor();
        player.sendSystemMessage(MekanismUtils.logFormat(MekanismLang.TOGGLE_COLOR.translate(color == null ? MekanismLang.NONE : color.getColoredName())));
        return InteractionResult.SUCCESS;
    }

    public InteractionResult onRightClick(Player player, Direction side) {
        EnumColor color = this.getColor();
        player.sendSystemMessage(MekanismUtils.logFormat(MekanismLang.CURRENT_COLOR.translate(color == null ? MekanismLang.NONE : color.getColoredName())));
        return super.onRightClick(player, side);
    }

    @Nullable
    public LogisticalTransporterUpgradeData getUpgradeData() {
        return new LogisticalTransporterUpgradeData(this.redstoneReactive, this.getConnectionTypesRaw(), this.getColor(), this.transit, this.needsSync, this.nextId, this.delay, this.delayCount);
    }

    public boolean dataTypeMatches(@Nonnull TransmitterUpgradeData data) {
        return data instanceof LogisticalTransporterUpgradeData;
    }

    public void parseUpgradeData(@Nonnull LogisticalTransporterUpgradeData data) {
        this.redstoneReactive = data.redstoneReactive;
        this.setConnectionTypesRaw(data.connectionTypes);
        this.setColor(data.color);
        this.transit.putAll(data.transit);
        this.needsSync.putAll(data.needsSync);
        this.nextId = data.nextId;
        this.delay = data.delay;
        this.delayCount = data.delayCount;
    }

    protected void readFromNBT(CompoundTag nbtTags) {
        super.readFromNBT(nbtTags);
        NBTUtils.setEnumIfPresent(nbtTags, NBTConstants.COLOR, TransporterUtils::readColor, this::setColor);
    }

    public void writeToNBT(CompoundTag nbtTags) {
        super.writeToNBT(nbtTags);
        nbtTags.putInt(NBTConstants.COLOR, TransporterUtils.getColorIndex(this.getColor()));
    }

    @Nonnull
    public CompoundTag getReducedUpdateTag(CompoundTag updateTag) {
        updateTag = super.getReducedUpdateTag(updateTag);
        updateTag.putInt(NBTConstants.COLOR, TransporterUtils.getColorIndex(this.getColor()));
        return updateTag;
    }

    public void handleUpdateTag(@Nonnull CompoundTag tag) {
        super.handleUpdateTag(tag);
        NBTUtils.setEnumIfPresent(tag, NBTConstants.COLOR, TransporterUtils::readColor, this::setColor);
    }
}
