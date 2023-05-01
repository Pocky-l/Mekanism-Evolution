package su.gamepoint.pocky.mekaevolution.client.events;

import mekanism.api.providers.IBlockProvider;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.tier.EnergyCubeTier;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.client.EvoRenderLogisticalTransporter;
import su.gamepoint.pocky.mekaevolution.client.EvoRenderMechanicalPipe;
import su.gamepoint.pocky.mekaevolution.client.EvoRenderUniversalCable;
import su.gamepoint.pocky.mekaevolution.client.energycube.EvoEnergyCubeModelLoader;
import su.gamepoint.pocky.mekaevolution.client.energycube.EvoModelEnergyCore;
import su.gamepoint.pocky.mekaevolution.client.energycube.EvoRenderEnergyCube;
import su.gamepoint.pocky.mekaevolution.client.energycube.EvoRenderEnergyCubeItem;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.ECTier;
import su.gamepoint.pocky.mekaevolution.registers.BlockRegister;
import su.gamepoint.pocky.mekaevolution.registers.TileEntityTypes;

/**
 * @author Dudko Roman
 */
@Mod.EventBusSubscriber(modid = MekanismEvolutionMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRender {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ClientRegistrationUtil.bindTileEntityRenderer(event, EvoRenderUniversalCable::new, TileEntityTypes.ABSOLUTE_UNIVERSAL_CABLE,
                TileEntityTypes.SUPREME_UNIVERSAL_CABLE, TileEntityTypes.COSMIC_UNIVERSAL_CABLE, TileEntityTypes.INFINITE_UNIVERSAL_CABLE);

        ClientRegistrationUtil.bindTileEntityRenderer(event, EvoRenderLogisticalTransporter::new, TileEntityTypes.ABSOLUTE_LOGISTICAL_TRANSPORTER,
                TileEntityTypes.SUPREME_LOGISTICAL_TRANSPORTER, TileEntityTypes.COSMIC_LOGISTICAL_TRANSPORTER, TileEntityTypes.INFINITE_LOGISTICAL_TRANSPORTER);

        ClientRegistrationUtil.bindTileEntityRenderer(event, EvoRenderMechanicalPipe::new, TileEntityTypes.ABSOLUTE_MECHANICAL_PIPE,
                TileEntityTypes.SUPREME_MECHANICAL_PIPE, TileEntityTypes.COSMIC_MECHANICAL_PIPE, TileEntityTypes.INFINITE_MECHANICAL_PIPE);

        ClientRegistrationUtil.bindTileEntityRenderer(event, EvoRenderEnergyCube::new, TileEntityTypes.ABSOLUTE_ENERGY_CUBE, TileEntityTypes.SUPREME_ENERGY_CUBE,
                TileEntityTypes.COSMIC_ENERGY_CUBE, TileEntityTypes.INFINITE_ENERGY_CUBE);
    }

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Post event) {
        TextureAtlas map = event.getAtlas();
        EvoRenderLogisticalTransporter.onStitch(map);
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        ClientRegistrationUtil.registerClientReloadListeners(event, EvoRenderEnergyCubeItem.RENDERER);
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register("energy_cube", EvoEnergyCubeModelLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EvoModelEnergyCore.CORE_LAYER, EvoModelEnergyCore::createLayerDefinition);
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        ClientRegistrationUtil.registerBlockColorHandler(event, (state, world, pos, index) -> {
                    if (index == 1) {
                        EnergyCubeTier tier = Attribute.getTier(state.getBlock(), EnergyCubeTier.class);
                        if (tier != null) {
                            float[] color = ECTier.getColor(tier);
                            return MekanismRenderer.getColorARGB(color[0], color[1], color[2], 1);
                        }
                    }
                    return -1;
                }, BlockRegister.ABSOLUTE_ENERGY_CUBE, BlockRegister.SUPREME_ENERGY_CUBE, BlockRegister.COSMIC_ENERGY_CUBE,
                BlockRegister.INFINITE_ENERGY_CUBE);
    }
}
