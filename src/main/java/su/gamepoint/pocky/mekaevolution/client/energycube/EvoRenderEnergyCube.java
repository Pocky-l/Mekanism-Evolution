package su.gamepoint.pocky.mekaevolution.client.energycube;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mekanism.client.MekanismClient;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.tileentity.MekanismTileEntityRenderer;
import mekanism.common.base.ProfilerConstants;
import mekanism.common.util.MekanismUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.ECTier;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoTileEntityEnergyCube;

/**
 * @author Dudko Roman
 */
public class EvoRenderEnergyCube extends MekanismTileEntityRenderer<EvoTileEntityEnergyCube> {

    public static final Vector3f coreVec = new Vector3f(0.0F, MekanismUtils.ONE_OVER_ROOT_TWO, MekanismUtils.ONE_OVER_ROOT_TWO);
    private final EvoModelEnergyCube model;
    private final EvoModelEnergyCube.ModelEnergyCore core;

    public EvoRenderEnergyCube(BlockEntityRendererProvider.Context context) {
        super(context);
        model = new EvoModelEnergyCube(context.getModelSet());
        core = new EvoModelEnergyCube.ModelEnergyCore(context.getModelSet());
    }

    @Override
    protected void render(EvoTileEntityEnergyCube tile, float partialTick, PoseStack matrix, MultiBufferSource renderer, int light, int overlayLight, ProfilerFiller profiler) {
        profiler.push(ProfilerConstants.FRAME);
        matrix.pushPose();
        matrix.translate(0.5, 1.5, 0.5);
        switch (tile.getDirection()) {
            case DOWN -> {
                matrix.mulPose(Vector3f.XN.rotationDegrees(90));
                matrix.translate(0, 1, -1);
            }
            case UP -> {
                matrix.mulPose(Vector3f.XP.rotationDegrees(90));
                matrix.translate(0, 1, 1);
            }
            //Otherwise, use the helper method for handling different face options because it is one of them
            default -> MekanismRenderer.rotate(matrix, tile.getDirection(), 0, 180, 90, 270);
        }
        matrix.mulPose(Vector3f.ZP.rotationDegrees(180));
        profiler.push(ProfilerConstants.CORNERS);
        model.render(matrix, renderer, light, overlayLight, ECTier.getColor(tile.getTier()), false, false);
        profiler.popPush(ProfilerConstants.SIDES);
        model.renderSidesBatched(tile, matrix, renderer, light, overlayLight);
        profiler.pop();//End sides
        matrix.popPose();

        profiler.popPush(ProfilerConstants.CORE);//End frame start core
        float energyScale = tile.getEnergyScale();
        if (energyScale > 0) {
            matrix.pushPose();
            matrix.translate(0.5, 0.5, 0.5);
            float ticks = MekanismClient.ticksPassed + partialTick;
            matrix.scale(0.4F, 0.4F, 0.4F);
            matrix.translate(0, Math.sin(Math.toRadians(3 * ticks)) / 7, 0);
            float scaledTicks = 4 * ticks;
            matrix.mulPose(Vector3f.YP.rotationDegrees(scaledTicks));
            matrix.mulPose(coreVec.rotationDegrees(36F + scaledTicks));
            core.render(matrix, renderer, MekanismRenderer.FULL_LIGHT, overlayLight, ECTier.getColor(tile.getTier()), energyScale);

            matrix.popPose();
        }
        profiler.pop();
    }

    @Override
    protected String getProfilerSection() {
        return ProfilerConstants.ENERGY_CUBE;
    }
}
