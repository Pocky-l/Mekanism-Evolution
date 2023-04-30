package su.gamepoint.pocky.mekaevolution.client.energycube;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mekanism.client.MekanismClient;
import mekanism.client.model.ModelEnergyCube;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.item.MekanismISTER;
import mekanism.common.tier.EnergyCubeTier;
import mekanism.common.util.StorageUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.ECTier;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoItemBlockEnergyCube;

import javax.annotation.Nonnull;

/**
 * @author Dudko Roman
 */
public class EvoRenderEnergyCubeItem extends MekanismISTER {

    public static final EvoRenderEnergyCubeItem RENDERER = new EvoRenderEnergyCubeItem();
    private EvoModelEnergyCube energyCube;
    private EvoModelEnergyCube.ModelEnergyCore core;

    @Override
    public void onResourceManagerReload(@Nonnull ResourceManager resourceManager) {
        energyCube = new EvoModelEnergyCube(getEntityModels());
        core = new EvoModelEnergyCube.ModelEnergyCore(getEntityModels());
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemTransforms.TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource renderer, int light, int overlayLight) {
        EnergyCubeTier tier = ((EvoItemBlockEnergyCube) stack.getItem()).getTier();
        matrix.pushPose();
        matrix.translate(0.5, 0.5, 0.5);
        matrix.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrix.pushPose();
        matrix.translate(0, -1, 0);
        //TODO: Instead of having this be a thing, make it do it from model like the block does?
        energyCube.render(matrix, renderer, light, overlayLight, ECTier.getColor(tier), true, stack.hasFoil());
        energyCube.renderSidesBatched(stack, tier, matrix, renderer, light, overlayLight, stack.hasFoil());
        matrix.popPose();
        double energyPercentage = StorageUtils.getStoredEnergyFromNBT(stack).divideToLevel(ECTier.getMaxEnergy(tier));
        if (energyPercentage > 0) {
            matrix.scale(0.4F, 0.4F, 0.4F);
            matrix.translate(0, Math.sin(Math.toRadians(3 * MekanismClient.ticksPassed)) / 7, 0);
            matrix.mulPose(Vector3f.YP.rotationDegrees(4 * MekanismClient.ticksPassed));
            matrix.mulPose(EvoRenderEnergyCube.coreVec.rotationDegrees(36F + 4 * MekanismClient.ticksPassed));
            core.render(matrix, renderer, MekanismRenderer.FULL_LIGHT, overlayLight, ECTier.getColor(tier), (float) energyPercentage);
        }
        matrix.popPose();
    }
}
