package su.gamepoint.pocky.mekaevolution.common.item.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mekanism.client.model.MekanismJavaModel;
import mekanism.client.model.ModelPartData;
import mekanism.client.render.MekanismRenderType;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;

import java.util.List;

/**
 * @author Dudko Roman
 */
public class ModelPlasmaSword extends MekanismJavaModel {

    public static final ModelLayerLocation PLASMA_SWORD_LAYER = new ModelLayerLocation(new ResourceLocation(MekanismEvolutionMod.MODID, "plasma_sword"), "main");
    private static final ResourceLocation PLASMA_SWORD_TEXTURE = new ResourceLocation(MekanismEvolutionMod.MODID, "render/plasma_sword.png");
    private static final ResourceLocation PLASMA_SWORD_BLADE_TEXTURE = new ResourceLocation(MekanismEvolutionMod.MODID, "render/plasma_sword_blade.png");

    private static final ModelPartData HANDLE = new ModelPartData("handle", CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(6.75F, -12F, 7.5F, 2F, 11F, 2F));


    private static final ModelPartData CAP = new ModelPartData("cap", CubeListBuilder.create()
            .texOffs(8, 10)
            .addBox(-4.75F, -15.5F, 7F, 3F, 3F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE_FRAME_1 = new ModelPartData("coreFrame1", CubeListBuilder.create()
            .texOffs(8, 5)
            .addBox(3.5F, -7.5F, 7.0F, 7F, 2F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE_FRAME_2 = new ModelPartData("coreFrame2", CubeListBuilder.create()
            .texOffs(8, 0)
            .addBox(3.5F, -2.5F, 7.0F, 7F, 2F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE_FRAME_3 = new ModelPartData("coreFrame3", CubeListBuilder.create()
            .texOffs(8, 21)
            .addBox(3.5F, -5.5F, 7.0F, 2F, 3F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE_FRAME_4 = new ModelPartData("coreFrame4", CubeListBuilder.create()
            .texOffs(17, 18)
            .addBox(8.5F, -5.5F, 7.0F, 2F, 3F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE = new ModelPartData("core", CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(5.0F, -6F, 7.5F, 4F, 4F, 2F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CATKIN = new ModelPartData("catkin", CubeListBuilder.create()
            .texOffs(0, 7)
            .addBox(4.5F, -20.3F, 8.5F, 6F, 7F, 0F));

        /*
        BLADE
         */

    private static final ModelPartData B1 = new ModelPartData("b1", CubeListBuilder.create()
            .texOffs(12, 0)
            .addBox(8F, 2F, 8F, 3F, 40F, 1F));

    private static final ModelPartData B2 = new ModelPartData("b2", CubeListBuilder.create()
            .texOffs(20, 0)
            .addBox(7F, 2F, 8F, 1F, 6F, 1F));

    private static final ModelPartData B3 = new ModelPartData("b3", CubeListBuilder.create()
            .texOffs(24, 0)
            .addBox(6F, 2F, 8F, 1F, 5F, 1F));

    private static final ModelPartData B4 = new ModelPartData("b4", CubeListBuilder.create()
            .texOffs(20, 7)
            .addBox(6F, 9F, 8F, 1F, 5F, 1F));

    private static final ModelPartData B5 = new ModelPartData("b5", CubeListBuilder.create()
            .texOffs(20, 7)
            .addBox(5F, 8F, 8F, 1F, 5F, 1F));

    private static final ModelPartData B6 = new ModelPartData("b6", CubeListBuilder.create()
            .texOffs(28, 0)
            .addBox(4F, 7F, 8F, 1F, 5F, 1F));

    private static final ModelPartData B7 = new ModelPartData("b7", CubeListBuilder.create()
            .texOffs(24, 13)
            .addBox(6F, 16F, 8F, 1F, 17F, 1F));

    private static final ModelPartData B8 = new ModelPartData("b8", CubeListBuilder.create()
            .texOffs(20, 13)
            .addBox(5F, 15F, 8F, 1F, 19F, 1F));

    private static final ModelPartData B9 = new ModelPartData("b9", CubeListBuilder.create()
            .texOffs(24, 6)
            .addBox(9F, 42F, 8F, 1F, 1F, 1F));

    private static final ModelPartData B10 = new ModelPartData("b10", CubeListBuilder.create()
            .texOffs(28, 6)
            .addBox(8F, 42F, 8F, 1F, 2F, 1F));

    private static final ModelPartData B11 = new ModelPartData("b11", CubeListBuilder.create()
            .texOffs(0, 14)
            .addBox(7F, 34F, 8F, 1F, 11F, 1F));

    private static final ModelPartData B12 = new ModelPartData("b12", CubeListBuilder.create()
            .texOffs(4, 14)
            .addBox(6F, 35F, 8F, 1F, 11F, 1F));

    public static LayerDefinition createLayerDefinition() {
        return createLayerDefinition(32, 64, HANDLE, CAP, CORE,
                CORE_FRAME_1, CORE_FRAME_2, CORE_FRAME_3, CORE_FRAME_4, CATKIN, B1, B2, B3, B4,
                B5, B6, B7, B8, B9, B10, B11, B12);
    }

    private final RenderType BLADE_RENDER_TYPE = MekanismRenderType.BLADE.apply(PLASMA_SWORD_BLADE_TEXTURE);
    private final RenderType RENDER_TYPE = renderType(PLASMA_SWORD_TEXTURE);
    private final List<ModelPart> parts;
    private final List<ModelPart> bladeParts;

    public ModelPlasmaSword(EntityModelSet entityModelSet) {
        super(RenderType::entitySolid);
        ModelPart root = entityModelSet.bakeLayer(PLASMA_SWORD_LAYER);
        parts = getRenderableParts(root, HANDLE, CAP, CORE_FRAME_1, CORE_FRAME_2,
                CORE_FRAME_3, CORE_FRAME_4);
        bladeParts = getRenderableParts(root, CORE, CATKIN, B1, B2, B3, B4,
                B5, B6, B7, B8, B9, B10, B11, B12);
    }

    public void render(@NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light, int overlayLight, boolean hasEffect, float alpha) {
        renderToBuffer(matrix, getVertexConsumer(renderer, RENDER_TYPE, hasEffect), light, overlayLight, 1, 1, 1, 1);
        renderPartsToBuffer(bladeParts, matrix, getVertexConsumer(renderer, BLADE_RENDER_TYPE, hasEffect), LightTexture.FULL_BRIGHT, overlayLight, 1, 1, 1, alpha);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int light, int overlayLight, float red, float green, float blue, float alpha) {
        renderPartsToBuffer(parts, poseStack, vertexConsumer, light, overlayLight, red, green, blue, alpha);
    }
}
