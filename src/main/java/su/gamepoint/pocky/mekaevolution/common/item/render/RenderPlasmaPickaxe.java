package su.gamepoint.pocky.mekaevolution.common.item.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mekanism.client.render.item.MekanismISTER;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dudko Roman
 */
public class RenderPlasmaPickaxe extends MekanismISTER {

    public static final RenderPlasmaPickaxe RENDERER = new RenderPlasmaPickaxe();
    private ModelPlasmaPickaxe plasmaPickaxe;

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        plasmaPickaxe = new ModelPlasmaPickaxe(getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull TransformType transformType, @NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light, int overlayLight) {
        matrix.pushPose();
        matrix.translate(0.5, 0.5, 0.5);
        matrix.mulPose(Vector3f.ZP.rotationDegrees(180));
        //PlasmaPickaxe item = (PlasmaPickaxe) stack.getItem();
        //double energyPercentage = StorageUtils.getStoredEnergyFromNBT(stack).divideToLevel(item.getMaxEnergy().get());
        plasmaPickaxe.render(matrix, renderer, light, overlayLight, stack.hasFoil(), 0.0F);
        matrix.popPose();
    }
}
