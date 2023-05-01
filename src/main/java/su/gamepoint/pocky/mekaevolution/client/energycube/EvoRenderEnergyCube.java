package su.gamepoint.pocky.mekaevolution.client.energycube;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mekanism.client.render.RenderTickHandler;
import mekanism.client.render.tileentity.ModelTileEntityRenderer;
import mekanism.common.base.ProfilerConstants;
import mekanism.common.util.MekanismUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.ECTier;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoTileEntityEnergyCube;

/**
 * @author Dudko Roman
 */
public class EvoRenderEnergyCube extends ModelTileEntityRenderer<EvoTileEntityEnergyCube, EvoModelEnergyCore> {

    public static final Vector3f coreVec = new Vector3f(0.0F, MekanismUtils.ONE_OVER_ROOT_TWO, MekanismUtils.ONE_OVER_ROOT_TWO);

    public EvoRenderEnergyCube(BlockEntityRendererProvider.Context context) {
        super(context, EvoModelEnergyCore::new);
    }

    @Override
    protected void render(EvoTileEntityEnergyCube tile, float partialTicks, PoseStack matrix, MultiBufferSource renderer, int light, int overlayLight, ProfilerFiller profiler) {
        float energyScale = tile.getEnergyScale();
        Vec3 renderPos = Vec3.atCenterOf(tile.getBlockPos());
        RenderTickHandler.addTransparentRenderer(EvoModelEnergyCore.BATCHED_RENDER_TYPE, new RenderTickHandler.LazyRender() {
            @Override
            public void render(Camera camera, VertexConsumer buffer, PoseStack poseStack, int renderTick, float partialTick, ProfilerFiller profiler) {
                float ticks = renderTick + partialTick;
                float scaledTicks = 4 * ticks;
                poseStack.pushPose();
                poseStack.translate(renderPos.x, renderPos.y, renderPos.z);
                poseStack.scale(0.4F, 0.4F, 0.4F);
                poseStack.translate(0, Math.sin(Math.toRadians(3 * ticks)) / 7, 0);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(scaledTicks));
                poseStack.mulPose(coreVec.rotationDegrees(36F + scaledTicks));
                model.render(poseStack, buffer, LightTexture.FULL_BRIGHT, overlayLight, ECTier.getColor(tile.getTier()), energyScale);
                poseStack.popPose();
            }

            @Override
            public Vec3 getCenterPos(float partialTick) {
                return renderPos;
            }

            @Override
            public String getProfilerSection() {
                return ProfilerConstants.ENERGY_CUBE_CORE;
            }
        });
    }

    @Override
    protected String getProfilerSection() {
        return ProfilerConstants.ENERGY_CUBE;
    }

    @Override
    public boolean shouldRender(EvoTileEntityEnergyCube tile, Vec3 camera) {
        return ECTier.getMaxEnergy(tile.getTier()).intValue() > 0 && super.shouldRender(tile, camera);
    }
}
