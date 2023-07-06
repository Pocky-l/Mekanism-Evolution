package su.gamepoint.pocky.mekaevolution.common.item;

import com.google.common.collect.Multimap;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.api.math.FloatingLongSupplier;
import mekanism.client.render.RenderPropertiesProvider;
import mekanism.common.config.MekanismConfig;
import mekanism.common.item.ItemEnergized;
import mekanism.common.tags.MekanismTags;
import mekanism.common.util.StorageUtils;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import su.gamepoint.pocky.mekaevolution.common.item.render.RenderPlasmaSword;
import su.gamepoint.pocky.mekaevolution.utils.EvoFloatingLong;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Dudko Roman
 */
public class PlasmaSword extends ItemEnergized {

    public static final Set<ToolAction> ALWAYS_SUPPORTED_ACTIONS = ToolActions.DEFAULT_SWORD_ACTIONS;

    private static final FloatingLongSupplier CHARGE_RATE = MekanismConfig.gear.disassemblerChargeRate; // TODO
    private static final FloatingLongSupplier MAX_ENERGY = new EvoFloatingLong(FloatingLong.create(10000000L));

    public PlasmaSword(Properties properties) {
        super(CHARGE_RATE, MAX_ENERGY, properties.rarity(Rarity.RARE).setNoRepair());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new RenderPropertiesProvider.MekRenderProperties(RenderPlasmaSword.RENDERER));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level world, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction action) {
        return ALWAYS_SUPPORTED_ACTIONS.contains(action);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull Entity target) {
        //If it is a vehicle that we want to damage
        if (target.getType().is(MekanismTags.Entities.HURTABLE_VEHICLES)) {
            if (target.isAttackable() && !target.skipAttackInteraction(player)) {
                //Always apply max damage to vehicles that can be hurt regardless of energy and don't actually
                target.hurt(DamageSource.playerAttack(player), MekanismConfig.gear.disassemblerMaxDamage.get());
                //Note: We fall through and call super regardless so any other processing that may need to happen, happens,
                // this is similar to how we return super from hurtEnemy
            }
        }
        return super.onLeftClickEntity(stack, player, target);
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
