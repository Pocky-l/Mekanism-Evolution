package su.gamepoint.morepipes;

import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import su.gamepoint.morepipes.config.PipeConfig;
import su.gamepoint.morepipes.registers.BlockRegister;
import su.gamepoint.morepipes.registers.TileEntityTypes;

@Mod(MorePipesMod.MODID)
public class MorePipesMod {

    public static final String MODID = "morepipes";

    private static PipeConfig config = new PipeConfig();

    public MorePipesMod() {

        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModContainer modContainer = ModLoadingContext.get().getActiveContainer();

        MekanismConfigHelper.registerConfig(modContainer, config);

        BlockRegister.REGISTRY_BLOCK.register(eventBus);
        TileEntityTypes.TILE_ENTITY_TYPES.register(eventBus);

        eventBus.register(this);
    }

    public static PipeConfig getConfig() {
        return config;
    }
}
