package su.gamepoint.pocky.mekaevolution.client.events;

import mekanism.client.ClientRegistrationUtil;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.client.EvoRenderLogisticalTransporter;
import su.gamepoint.pocky.mekaevolution.client.EvoRenderMechanicalPipe;
import su.gamepoint.pocky.mekaevolution.client.EvoRenderUniversalCable;
import su.gamepoint.pocky.mekaevolution.client.energycube.EvoRenderEnergyCube;
import su.gamepoint.pocky.mekaevolution.client.energycube.EvoRenderEnergyCubeItem;
import su.gamepoint.pocky.mekaevolution.common.item.render.ModelPlasmaPickaxe;
import su.gamepoint.pocky.mekaevolution.common.item.render.RenderPlasmaPickaxe;
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
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.ABSOLUTE_UNIVERSAL_CABLE.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.SUPREME_UNIVERSAL_CABLE.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.COSMIC_UNIVERSAL_CABLE.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.INFINITE_UNIVERSAL_CABLE.getBlock(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(BlockRegister.ABSOLUTE_LOGISTICAL_TRANSPORTER.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.SUPREME_LOGISTICAL_TRANSPORTER.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.COSMIC_LOGISTICAL_TRANSPORTER.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.INFINITE_LOGISTICAL_TRANSPORTER.getBlock(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(BlockRegister.ABSOLUTE_MECHANICAL_PIPE.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.SUPREME_MECHANICAL_PIPE.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.COSMIC_MECHANICAL_PIPE.getBlock(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.INFINITE_MECHANICAL_PIPE.getBlock(), RenderType.translucent());

        });
    }

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Post event) {
        TextureAtlas map = event.getAtlas();
        EvoRenderLogisticalTransporter.onStitch(map);
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        ClientRegistrationUtil.registerClientReloadListeners(event, EvoRenderEnergyCubeItem.RENDERER,
                RenderPlasmaPickaxe.RENDERER);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelPlasmaPickaxe.PLASMA_PICKAXE_LAYER, ModelPlasmaPickaxe::createLayerDefinition);
    }
}
