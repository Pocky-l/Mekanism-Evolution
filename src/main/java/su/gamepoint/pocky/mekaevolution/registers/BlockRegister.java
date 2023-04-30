package su.gamepoint.pocky.mekaevolution.registers;

import mekanism.common.content.blocktype.Machine;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.tier.CableTier;
import mekanism.common.tier.PipeTier;
import mekanism.common.tier.TransporterTier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoBlockEnergyCube;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoItemBlockEnergyCube;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoTileEntityEnergyCube;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable.EvoBlockUniversalCable;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable.EvoItemBlockUniversalCable;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter.EvoBlockLogisticalTransporter;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.logisticaltransporter.EvoItemBlockLogisticalTransporter;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.pipe.EvoBlockMechanicalPipe;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.pipe.EvoItemBlockMechanicalPipe;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegister {

    public static final BlockDeferredRegister REGISTRY_BLOCK = new BlockDeferredRegister(MekanismEvolutionMod.MODID);

    private static <BLOCK extends Block, ITEM extends BlockItem> BlockRegistryObject<BLOCK, ITEM> registerTieredBlock(String tierName, String suffix, Supplier<? extends BLOCK> blockSupplier, Function<BLOCK, ITEM> itemCreator) {
        return REGISTRY_BLOCK.register(tierName + suffix, blockSupplier, itemCreator);
    }

    public static final BlockRegistryObject<EvoBlockUniversalCable, EvoItemBlockUniversalCable> ABSOLUTE_UNIVERSAL_CABLE = registerUniversalCable("absolute", CableTier.BASIC);;
    public static final BlockRegistryObject<EvoBlockUniversalCable, EvoItemBlockUniversalCable> SUPREME_UNIVERSAL_CABLE = registerUniversalCable("supreme", CableTier.ADVANCED, BlockBehaviour.Properties.of(Material.PISTON).strength(1.0F, 6.0F).lightLevel((l) -> 10));
    public static final BlockRegistryObject<EvoBlockUniversalCable, EvoItemBlockUniversalCable> COSMIC_UNIVERSAL_CABLE = registerUniversalCable("cosmic", CableTier.ELITE, BlockBehaviour.Properties.of(Material.PISTON).strength(1.0F, 6.0F).lightLevel((l) -> 13));
    public static final BlockRegistryObject<EvoBlockUniversalCable, EvoItemBlockUniversalCable> INFINITE_UNIVERSAL_CABLE = registerUniversalCable("infinite", CableTier.ULTIMATE, BlockBehaviour.Properties.of(Material.PISTON).strength(1.0F, 6.0F).lightLevel((l) -> 16));

    public static final BlockRegistryObject<EvoBlockLogisticalTransporter, EvoItemBlockLogisticalTransporter> ABSOLUTE_LOGISTICAL_TRANSPORTER = registerLogisticalTransporter("absolute", TransporterTier.BASIC);
    public static final BlockRegistryObject<EvoBlockLogisticalTransporter, EvoItemBlockLogisticalTransporter> SUPREME_LOGISTICAL_TRANSPORTER = registerLogisticalTransporter("supreme", TransporterTier.ADVANCED);
    public static final BlockRegistryObject<EvoBlockLogisticalTransporter, EvoItemBlockLogisticalTransporter> COSMIC_LOGISTICAL_TRANSPORTER = registerLogisticalTransporter("cosmic", TransporterTier.ELITE);
    public static final BlockRegistryObject<EvoBlockLogisticalTransporter, EvoItemBlockLogisticalTransporter> INFINITE_LOGISTICAL_TRANSPORTER = registerLogisticalTransporter("infinite", TransporterTier.ULTIMATE);

    public static final BlockRegistryObject<EvoBlockMechanicalPipe, EvoItemBlockMechanicalPipe> ABSOLUTE_MECHANICAL_PIPE = registerMechanicalPipe("absolute", PipeTier.BASIC);
    public static final BlockRegistryObject<EvoBlockMechanicalPipe, EvoItemBlockMechanicalPipe> SUPREME_MECHANICAL_PIPE = registerMechanicalPipe("supreme", PipeTier.ADVANCED);
    public static final BlockRegistryObject<EvoBlockMechanicalPipe, EvoItemBlockMechanicalPipe> COSMIC_MECHANICAL_PIPE = registerMechanicalPipe("cosmic", PipeTier.ELITE);
    public static final BlockRegistryObject<EvoBlockMechanicalPipe, EvoItemBlockMechanicalPipe> INFINITE_MECHANICAL_PIPE = registerMechanicalPipe("infinite", PipeTier.ULTIMATE);

    public static final BlockRegistryObject<EvoBlockEnergyCube, EvoItemBlockEnergyCube> ABSOLUTE_ENERGY_CUBE = registerEnergyCube("absolute", BlockTypes.ABSOLUTE_ENERGY_CUBE);
    public static final BlockRegistryObject<EvoBlockEnergyCube, EvoItemBlockEnergyCube> SUPREME_ENERGY_CUBE = registerEnergyCube("supreme", BlockTypes.SUPREME_ENERGY_CUBE);
    public static final BlockRegistryObject<EvoBlockEnergyCube, EvoItemBlockEnergyCube> COSMIC_ENERGY_CUBE = registerEnergyCube("cosmic", BlockTypes.COSMIC_ENERGY_CUBE);
    public static final BlockRegistryObject<EvoBlockEnergyCube, EvoItemBlockEnergyCube> INFINITE_ENERGY_CUBE = registerEnergyCube("infinite", BlockTypes.INFINITE_ENERGY_CUBE);

    private static BlockRegistryObject<EvoBlockEnergyCube, EvoItemBlockEnergyCube> registerEnergyCube(String tileName, Machine<EvoTileEntityEnergyCube> type) {
        return registerTieredBlock(tileName, "_energy_cube", () -> new EvoBlockEnergyCube(type), EvoItemBlockEnergyCube::new);
    }

    private static BlockRegistryObject<EvoBlockMechanicalPipe, EvoItemBlockMechanicalPipe> registerMechanicalPipe(String tierName, PipeTier tier) {
        return registerTieredBlock(tierName, "_mechanical_pipe", () -> new EvoBlockMechanicalPipe(tier), EvoItemBlockMechanicalPipe::new);
    }

    private static BlockRegistryObject<EvoBlockLogisticalTransporter, EvoItemBlockLogisticalTransporter> registerLogisticalTransporter(String tierName, TransporterTier tier) {
        return registerTieredBlock(tierName, "_logistical_transporter", () -> new EvoBlockLogisticalTransporter(tier), EvoItemBlockLogisticalTransporter::new);
    }

    private static BlockRegistryObject<EvoBlockUniversalCable, EvoItemBlockUniversalCable> registerUniversalCable(String tierName, CableTier tier) {
        return registerTieredBlock(tierName, "_universal_cable", () -> new EvoBlockUniversalCable(tier), EvoItemBlockUniversalCable::new);
    }

    private static BlockRegistryObject<EvoBlockUniversalCable, EvoItemBlockUniversalCable> registerUniversalCable(String tierName, CableTier tier, BlockBehaviour.Properties properties) {
        return registerTieredBlock(tierName, "_universal_cable", () -> new EvoBlockUniversalCable(tier, properties), EvoItemBlockUniversalCable::new);
    }
}
