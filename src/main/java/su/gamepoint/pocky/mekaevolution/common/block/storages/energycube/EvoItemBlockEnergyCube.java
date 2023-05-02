package su.gamepoint.pocky.mekaevolution.common.block.storages.energycube;

import mekanism.api.text.EnumColor;
import mekanism.client.render.RenderPropertiesProvider;
import mekanism.common.MekanismLang;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.capabilities.energy.item.ItemStackEnergyHandler;
import mekanism.common.config.MekanismConfig;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.item.interfaces.IItemSustainedInventory;
import mekanism.common.tier.EnergyCubeTier;
import mekanism.common.util.StorageUtils;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import su.gamepoint.pocky.mekaevolution.client.energycube.EvoRenderEnergyCubeItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author Dudko Roman
 */
public class EvoItemBlockEnergyCube extends ItemBlockTooltip<EvoBlockEnergyCube> implements IItemSustainedInventory {
    public EvoItemBlockEnergyCube(EvoBlockEnergyCube block) {
        super(block);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new RenderPropertiesProvider.MekRenderProperties(EvoRenderEnergyCubeItem.RENDERER));
    }

    @Nonnull
    @Override
    public EnergyCubeTier getTier() {
        return Attribute.getTier(getBlock(), EnergyCubeTier.class);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level world, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        StorageUtils.addStoredEnergy(stack, tooltip, true);
        tooltip.add(MekanismLang.CAPACITY.translateColored(EnumColor.INDIGO, EnumColor.GRAY, EnergyDisplay.of(ECTier.getMaxEnergy(getTier()))));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    protected void addTypeDetails(@Nonnull ItemStack stack, Level world, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        //Don't call super so that we can exclude the stored energy from being shown as we show it in hover text
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
        if (allowdedIn(group)) {
            EnergyCubeTier tier = Attribute.getTier(getBlock(), EnergyCubeTier.class);
            ItemStack stack = new ItemStack(this);

            //Add the empty and charged variants
            items.add(stack);
            assert tier != null;
            items.add(StorageUtils.getFilledEnergyVariant(stack.copy(), ECTier.getMaxEnergy(tier)));
        }
    }

    @Override
    public boolean isBarVisible(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(@Nonnull ItemStack stack) {
        return StorageUtils.getEnergyBarWidth(stack);
    }

    @Override
    public int getBarColor(@Nonnull ItemStack stack) {
        return MekanismConfig.client.energyColor.get();
    }

    @Override
    protected void gatherCapabilities(List<ItemCapabilityWrapper.ItemCapability> capabilities, ItemStack stack, CompoundTag nbt) {
        super.gatherCapabilities(capabilities, stack, nbt);
        ItemCapabilityWrapper.ItemCapability capability = EvoRateLimitEnergyHandler.create(Attribute.getTier(getBlock(), EnergyCubeTier.class));
        int index = IntStream.range(0, capabilities.size()).filter(i -> capabilities.get(i) instanceof ItemStackEnergyHandler).findFirst().orElse(-1);
        if (index != -1) {
            //This is likely always the path that will be taken
            capabilities.set(index, capability);
        } else {
            capabilities.add(capability);
        }
    }
}
