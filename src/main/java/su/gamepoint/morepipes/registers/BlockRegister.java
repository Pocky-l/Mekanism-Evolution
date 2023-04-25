package su.gamepoint.morepipes.registers;

import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.tier.CableTier;
import mekanism.common.tier.TransporterTier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import su.gamepoint.morepipes.MorePipesMod;
import su.gamepoint.morepipes.common.block.transmitter.cable.MoreBlockUniversalCable;
import su.gamepoint.morepipes.common.block.transmitter.cable.MoreItemBlockUniversalCable;
import su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter.MoreBlockLogisticalTransporter;
import su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter.MoreItemBlockLogisticalTransporter;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegister {

    public static final BlockDeferredRegister REGISTRY_BLOCK = new BlockDeferredRegister(MorePipesMod.MODID);

    private static <BLOCK extends Block, ITEM extends BlockItem> BlockRegistryObject<BLOCK, ITEM> registerTieredBlock(String tierName, String suffix, Supplier<? extends BLOCK> blockSupplier, Function<BLOCK, ITEM> itemCreator) {
        return REGISTRY_BLOCK.register(tierName + suffix, blockSupplier, itemCreator);
    }

    public static final BlockRegistryObject<MoreBlockUniversalCable, MoreItemBlockUniversalCable> ABSOLUTE_UNIVERSAL_CABLE = registerUniversalCable("absolute", CableTier.BASIC);;
    public static final BlockRegistryObject<MoreBlockUniversalCable, MoreItemBlockUniversalCable> SUPREME_UNIVERSAL_CABLE = registerUniversalCable("supreme", CableTier.ADVANCED, BlockBehaviour.Properties.of(Material.PISTON).strength(1.0F, 6.0F).lightLevel((l) -> 10));
    public static final BlockRegistryObject<MoreBlockUniversalCable, MoreItemBlockUniversalCable> COSMIC_UNIVERSAL_CABLE = registerUniversalCable("cosmic", CableTier.ELITE, BlockBehaviour.Properties.of(Material.PISTON).strength(1.0F, 6.0F).lightLevel((l) -> 13));
    public static final BlockRegistryObject<MoreBlockUniversalCable, MoreItemBlockUniversalCable> INFINITE_UNIVERSAL_CABLE = registerUniversalCable("infinite", CableTier.ULTIMATE, BlockBehaviour.Properties.of(Material.PISTON).strength(1.0F, 6.0F).lightLevel((l) -> 16));

    public static final BlockRegistryObject<MoreBlockLogisticalTransporter, MoreItemBlockLogisticalTransporter> ABSOLUTE_LOGISTICAL_TRANSPORTER = registerLogisticalTransporter("absolute", TransporterTier.BASIC);
    public static final BlockRegistryObject<MoreBlockLogisticalTransporter, MoreItemBlockLogisticalTransporter> SUPREME_LOGISTICAL_TRANSPORTER = registerLogisticalTransporter("supreme", TransporterTier.ADVANCED);
    public static final BlockRegistryObject<MoreBlockLogisticalTransporter, MoreItemBlockLogisticalTransporter> COSMIC_LOGISTICAL_TRANSPORTER = registerLogisticalTransporter("cosmic", TransporterTier.ELITE);
    public static final BlockRegistryObject<MoreBlockLogisticalTransporter, MoreItemBlockLogisticalTransporter> INFINITE_LOGISTICAL_TRANSPORTER = registerLogisticalTransporter("infinite", TransporterTier.ULTIMATE);

    private static BlockRegistryObject<MoreBlockLogisticalTransporter, MoreItemBlockLogisticalTransporter> registerLogisticalTransporter(String tierName, TransporterTier tier) {
        return registerTieredBlock(tierName, "_logistical_transporter", () -> new MoreBlockLogisticalTransporter(tier), MoreItemBlockLogisticalTransporter::new);
    }

    private static BlockRegistryObject<MoreBlockUniversalCable, MoreItemBlockUniversalCable> registerUniversalCable(String tierName, CableTier tier) {
        return registerTieredBlock(tierName, "_universal_cable", () -> new MoreBlockUniversalCable(tier), MoreItemBlockUniversalCable::new);
    }

    private static BlockRegistryObject<MoreBlockUniversalCable, MoreItemBlockUniversalCable> registerUniversalCable(String tierName, CableTier tier, BlockBehaviour.Properties properties) {
        return registerTieredBlock(tierName, "_universal_cable", () -> new MoreBlockUniversalCable(tier, properties), MoreItemBlockUniversalCable::new);
    }
}
