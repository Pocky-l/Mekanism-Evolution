package su.gamepoint.pocky.mekaevolution;

import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import su.gamepoint.pocky.mekaevolution.client.events.ClientTick;
import su.gamepoint.pocky.mekaevolution.config.EvoConfig;
import su.gamepoint.pocky.mekaevolution.registers.BlockRegister;
import su.gamepoint.pocky.mekaevolution.registers.EvoContainerTypes;
import su.gamepoint.pocky.mekaevolution.registers.ItemRegister;
import su.gamepoint.pocky.mekaevolution.registers.TileEntityTypes;

@Mod(MekanismEvolutionMod.MODID)
public class MekanismEvolutionMod {

    public static final String MODID = "mekaevolution";

    private static final EvoConfig config = new EvoConfig();

    public MekanismEvolutionMod() {

        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModContainer modContainer = ModLoadingContext.get().getActiveContainer();

        MekanismConfigHelper.registerConfig(modContainer, config);

        BlockRegister.REGISTRY_BLOCK.register(eventBus);
        ItemRegister.REGISTRY_ITEM.register(eventBus);
        TileEntityTypes.TILE_ENTITY_TYPES.register(eventBus);
        EvoContainerTypes.CONTAINER_TYPES.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new ClientTick());

        eventBus.register(this);
    }

    public static EvoConfig getConfig() {
        return config;
    }
}
