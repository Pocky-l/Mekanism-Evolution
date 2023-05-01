package su.gamepoint.pocky.mekaevolution.client.energycube;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mekanism.api.NBTConstants;
import mekanism.api.RelativeSide;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.item.MekanismISTER;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tier.EnergyCubeTier;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.util.EnumUtils;
import mekanism.common.util.ItemDataUtils;
import mekanism.common.util.StorageUtils;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.ECTier;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoItemBlockEnergyCube;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoTileEntityEnergyCube;

/**
 * @author Dudko Roman
 */
public class EvoRenderEnergyCubeItem extends MekanismISTER {

    public static final EvoRenderEnergyCubeItem RENDERER = new EvoRenderEnergyCubeItem();
    private EvoModelEnergyCore core;

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        core = new EvoModelEnergyCore(getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemTransforms.TransformType transformType, @NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light,
                             int overlayLight) {
        EnergyCubeTier tier = ((EvoItemBlockEnergyCube) stack.getItem()).getTier();

        EvoTileEntityEnergyCube.CubeSideState[] sideStates = new EvoTileEntityEnergyCube.CubeSideState[EnumUtils.SIDES.length];
        CompoundTag configData = ItemDataUtils.getDataMapIfPresent(stack);
        if (configData != null && configData.contains(NBTConstants.COMPONENT_CONFIG, Tag.TAG_COMPOUND)) {
            CompoundTag sideConfig = configData.getCompound(NBTConstants.COMPONENT_CONFIG).getCompound(NBTConstants.CONFIG + TransmissionType.ENERGY.ordinal());
            //TODO: Maybe improve on this, but for now this is a decent way of making it not have disabled sides show
            for (RelativeSide side : EnumUtils.SIDES) {
                DataType dataType = DataType.byIndexStatic(sideConfig.getInt(NBTConstants.SIDE + side.ordinal()));
                EvoTileEntityEnergyCube.CubeSideState state = EvoTileEntityEnergyCube.CubeSideState.INACTIVE;
                if (dataType != DataType.NONE) {
                    state = dataType.canOutput() ? EvoTileEntityEnergyCube.CubeSideState.ACTIVE_LIT : EvoTileEntityEnergyCube.CubeSideState.ACTIVE_UNLIT;
                }
                sideStates[side.ordinal()] = state;
            }
        } else {
            for (RelativeSide side : EnumUtils.SIDES) {
                sideStates[side.ordinal()] = tier == EnergyCubeTier.CREATIVE || side == RelativeSide.FRONT ? EvoTileEntityEnergyCube.CubeSideState.ACTIVE_LIT : EvoTileEntityEnergyCube.CubeSideState.ACTIVE_UNLIT;
            }
        }
        ModelData modelData = ModelData.builder().with(EvoTileEntityEnergyCube.SIDE_STATE_PROPERTY, sideStates).build();
        renderBlockItem(stack, transformType, matrix, renderer, light, overlayLight, modelData);
        double energyPercentage = StorageUtils.getStoredEnergyFromNBT(stack).divideToLevel(ECTier.getMaxEnergy(tier));
        if (energyPercentage > 0) {
            float ticks = MekanismRenderer.getPartialTick();
            float scaledTicks = 4 * ticks;
            matrix.pushPose();
            matrix.translate(0.5, 0.5, 0.5);
            matrix.scale(0.4F, 0.4F, 0.4F);
            matrix.translate(0, Math.sin(Math.toRadians(3 * ticks)) / 7, 0);
            matrix.mulPose(Vector3f.YP.rotationDegrees(scaledTicks));
            matrix.mulPose(EvoRenderEnergyCube.coreVec.rotationDegrees(36F + scaledTicks));
            core.render(matrix, renderer, LightTexture.FULL_BRIGHT, overlayLight, ECTier.getColor(tier), (float) energyPercentage);
            matrix.popPose();
        }
    }
}
