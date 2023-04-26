package su.gamepoint.morepipes.events;

import mekanism.client.ClientRegistrationUtil;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import su.gamepoint.morepipes.MorePipesMod;
import su.gamepoint.morepipes.client.MoreRenderLogisticalTransporter;
import su.gamepoint.morepipes.client.MoreRenderMechanicalPipe;
import su.gamepoint.morepipes.client.MoreRenderUniversalCable;
import su.gamepoint.morepipes.registers.BlockRegister;
import su.gamepoint.morepipes.registers.TileEntityTypes;

/**
 * @author Dudko Roman
 */
@Mod.EventBusSubscriber(modid = MorePipesMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRender {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ClientRegistrationUtil.bindTileEntityRenderer(event, MoreRenderUniversalCable::new, TileEntityTypes.ABSOLUTE_UNIVERSAL_CABLE,
                TileEntityTypes.SUPREME_UNIVERSAL_CABLE, TileEntityTypes.COSMIC_UNIVERSAL_CABLE, TileEntityTypes.INFINITE_UNIVERSAL_CABLE);

        ClientRegistrationUtil.bindTileEntityRenderer(event, MoreRenderLogisticalTransporter::new, TileEntityTypes.ABSOLUTE_LOGISTICAL_TRANSPORTER,
                TileEntityTypes.SUPREME_LOGISTICAL_TRANSPORTER, TileEntityTypes.COSMIC_LOGISTICAL_TRANSPORTER, TileEntityTypes.INFINITE_LOGISTICAL_TRANSPORTER);

        ClientRegistrationUtil.bindTileEntityRenderer(event, MoreRenderMechanicalPipe::new, TileEntityTypes.ABSOLUTE_MECHANICAL_PIPE,
                TileEntityTypes.SUPREME_MECHANICAL_PIPE, TileEntityTypes.COSMIC_MECHANICAL_PIPE, TileEntityTypes.INFINITE_MECHANICAL_PIPE);
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
        MoreRenderLogisticalTransporter.onStitch(map);
    }
}
