package su.gamepoint.pocky.mekaevolution.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.transmitter.RenderTransmitterBase;
import mekanism.common.base.ProfilerConstants;
import mekanism.common.content.network.EnergyNetwork;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable.EvoTileEntityUniversalCable;
import su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable.EvoUniversalCable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author Dudko Roman
 */
@ParametersAreNonnullByDefault
public class EvoRenderUniversalCable extends RenderTransmitterBase<EvoTileEntityUniversalCable> {

    public EvoRenderUniversalCable(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void render(EvoTileEntityUniversalCable tile, float partialTick, PoseStack matrix, MultiBufferSource renderer, int light, int overlayLight,
                          ProfilerFiller profiler) {
        EnergyNetwork network = tile.getTransmitter().getTransmitterNetwork();
        matrix.pushPose();
        matrix.translate(0.5, 0.5, 0.5);
        renderModel(tile, matrix, renderer.getBuffer(Sheets.translucentCullBlockSheet()), 0xFFFFFF, network.currentScale, LightTexture.FULL_BRIGHT,
                overlayLight, MekanismRenderer.energyIcon);
        matrix.popPose();
    }

    @Override
    protected String getProfilerSection() {
        return ProfilerConstants.UNIVERSAL_CABLE;
    }

    @Override
    protected boolean shouldRenderTransmitter(EvoTileEntityUniversalCable tile, Vec3 camera) {
        EvoUniversalCable cable = tile.getTransmitter();
        if (cable.hasTransmitterNetwork()) {
            EnergyNetwork network = cable.getTransmitterNetwork();
            //Note: We don't check if the network is empty as we don't actually ever sync the energy value to the client
            return network.currentScale > 0;
        }
        return false;
    }
}
