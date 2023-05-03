package su.gamepoint.pocky.mekaevolution.common.item;

import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.MekanismLang;
import mekanism.common.content.qio.IQIODriveItem;
import mekanism.common.tier.QIODriveTier;
import mekanism.common.util.text.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Dudko Roman
 */
public class EvoItemQIODrive extends Item implements IQIODriveItem {

    private final EvoQIODriveTier tier;

    public EvoItemQIODrive(EvoQIODriveTier tier, Item.Properties properties) {
        super(properties.stacksTo(1));
        this.tier = tier;
    }

    public void appendHoverText(@Nonnull ItemStack stack, Level world, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        IQIODriveItem.DriveMetadata meta = DriveMetadata.load(stack);
        tooltip.add(MekanismLang.QIO_ITEMS_DETAIL.translateColored(EnumColor.GRAY, EnumColor.INDIGO, TextUtils.format(meta.count()), TextUtils.format(this.getCountCapacity(stack))));
        tooltip.add(MekanismLang.QIO_TYPES_DETAIL.translateColored(EnumColor.GRAY, EnumColor.INDIGO, TextUtils.format(meta.types()), TextUtils.format(this.getTypeCapacity(stack))));
    }

    @Nonnull
    public Component getName(@Nonnull ItemStack stack) {
        return TextComponentUtil.build(this.tier.getBaseTier().getTextColor(), super.getName(stack));
    }

    public long getCountCapacity(ItemStack stack) {
        return this.tier.getMaxCount();
    }

    public int getTypeCapacity(ItemStack stack) {
        return this.tier.getMaxTypes();
    }
}