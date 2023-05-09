package su.gamepoint.pocky.mekaevolution.common.item;

import com.google.common.collect.Multimap;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.api.math.FloatingLongSupplier;
import mekanism.api.text.EnumColor;
import mekanism.client.render.RenderPropertiesProvider;
import mekanism.common.MekanismLang;
import mekanism.common.config.MekanismConfig;
import mekanism.common.item.ItemEnergized;
import mekanism.common.tags.MekanismTags;
import mekanism.common.util.StorageUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStack.TooltipPart;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import su.gamepoint.pocky.mekaevolution.client.item.render.RenderPlasmaPickaxe;
import su.gamepoint.pocky.mekaevolution.utils.EvoFloatingLong;
import su.gamepoint.pocky.mekaevolution.utils.EvolutionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Dudko Roman
 */
public class PlasmaPickaxe extends ItemEnergized {
    public static final Set<ToolAction> ALWAYS_SUPPORTED_ACTIONS = Set.of(ToolActions.PICKAXE_DIG);

    private static final FloatingLongSupplier CHARGE_RATE = MekanismConfig.gear.disassemblerChargeRate; // TODO
    private static final FloatingLongSupplier MAX_ENERGY = new EvoFloatingLong(FloatingLong.create(10000000L));

    public PlasmaPickaxe(Properties properties) {
        super(CHARGE_RATE, MAX_ENERGY, properties.rarity(Rarity.RARE).setNoRepair());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new RenderPropertiesProvider.MekRenderProperties(RenderPlasmaPickaxe.RENDERER));
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull BlockState state) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(MekanismLang.DISASSEMBLER_EFFICIENCY.translateColored(EnumColor.INDIGO, getEfficiency()));
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction action) {
        return ALWAYS_SUPPORTED_ACTIONS.contains(action);
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
        FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();
        FloatingLong energyCost = MekanismConfig.gear.disassemblerEnergyUsageWeapon.get();
        int minDamage = MekanismConfig.gear.disassemblerMinDamage.get();
        int damageDifference = MekanismConfig.gear.disassemblerMaxDamage.get() - minDamage;
        //If we don't have enough power use it at a reduced power level
        double percent = 1;
        if (energy.smallerThan(energyCost)) {
            percent = energy.divideToLevel(energyCost);
        }
        float damage = (float) (minDamage + damageDifference * percent);
        if (attacker instanceof Player player) {
            target.hurt(DamageSource.playerAttack(player), damage);
        } else {
            target.hurt(DamageSource.mobAttack(attacker), damage);
        }
        if (energyContainer != null && !energy.isZero()) {
            energyContainer.extract(energyCost, Action.EXECUTE, AutomationType.MANUAL);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) {
        IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
        if (energyContainer == null) {
            return 0;
        }
        //Use raw hardness to get the best guess of if it is zero or not
        FloatingLong energyRequired = getDestroyEnergy();
        FloatingLong energyAvailable = energyContainer.extract(energyRequired, Action.SIMULATE, AutomationType.MANUAL);
        if (energyAvailable.smallerThan(energyRequired)) {
            //If we can't extract all the energy we need to break it go at base speed reduced by how much we actually have available
            return getEfficiency() * energyAvailable.divide(energyRequired).floatValue();
        }
        return getEfficiency();
    }

    private float getEfficiency() {
        return 50F;
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull Level world, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entityliving) {
        IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
        if (energyContainer != null) {
            energyContainer.extract(getDestroyEnergy(), Action.EXECUTE, AutomationType.MANUAL);
        }
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        if (player.level.isClientSide || player.isCreative()) {
            return super.onBlockStartBreak(stack, pos, player);
        }
        IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
        if (energyContainer != null) {
            Level world = player.level;
            BlockState state = world.getBlockState(pos);
            FloatingLong energyRequired = getDestroyEnergy();
            if (energyContainer.extract(energyRequired, Action.SIMULATE, AutomationType.MANUAL).greaterOrEqual(energyRequired)) {

                //Object2IntMap<BlockPos> found = ModuleVeinMiningUnit.findPositions(world, Map.of(pos, state), 0, Object2BooleanMaps.singleton(state.getBlock(), true));
                EvolutionUtils.excavateArea(energyContainer, energyRequired, world, pos, (ServerPlayer) player, stack,
                        this, hardness -> FloatingLong.ZERO,
                        (hardness, distance, bs) -> getDestroyEnergy().multiply(0.5 * Math.pow(distance, 1.5)),
                        3, 3, 1);
            }
        }
        return super.onBlockStartBreak(stack, pos, player);
    }

    private FloatingLong getDestroyEnergy() {
        return FloatingLong.create(128);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlot slot, @Nonnull ItemStack stack) {
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public int getDefaultTooltipHideFlags(@Nonnull ItemStack stack) {
        return super.getDefaultTooltipHideFlags(stack) | TooltipPart.MODIFIERS.getMask();
    }

    @Override
    public boolean isEnchantable(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    public FloatingLongSupplier getMaxEnergy() {
        return MAX_ENERGY;
    }
}
