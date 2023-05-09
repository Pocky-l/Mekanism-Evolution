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
public class ModelPlasmaPickaxe extends MekanismJavaModel {

    public static final ModelLayerLocation PLASMA_PICKAXE_LAYER = new ModelLayerLocation(new ResourceLocation(MekanismEvolutionMod.MODID, "plasma_pickaxe"), "main");
    private static final ResourceLocation PLASMA_PICKAXE_TEXTURE = new ResourceLocation(MekanismEvolutionMod.MODID, "render/plasma_pickaxe.png");
    private static final ResourceLocation PLASMA_PICKAXE_BLADE_TEXTURE = new ResourceLocation(MekanismEvolutionMod.MODID, "render/plasma_pickaxe_blade.png");

    private static final ModelPartData HANDLE = new ModelPartData("handle", CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(6.75F, -12.0F, 7.5F, 2F, 29F, 2F));


    private static final ModelPartData CAP = new ModelPartData("cap", CubeListBuilder.create()
            .texOffs(8, 10)
            .addBox(-4.75F, -15.5F, 7.0F, 3F, 3F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData RING_DOWN = new ModelPartData("ringDown", CubeListBuilder.create()
            .texOffs(8, 16)
            .addBox(6.25F, -10.0F, 7.0F, 3F, 2F, 3F));

    private static final ModelPartData RING_UP = new ModelPartData("ringUp", CubeListBuilder.create()
            .texOffs(17, 13)
            .addBox(6.25F, 13.0F, 7.0F, 3F, 2F, 3F));

    private static final ModelPartData CORE_FRAME_1 = new ModelPartData("coreFrame1", CubeListBuilder.create()
            .texOffs(8, 5)
            .addBox(16.5F, 5.5F, 7.0F, 7F, 2F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE_FRAME_2 = new ModelPartData("coreFrame2", CubeListBuilder.create()
            .texOffs(8, 0)
            .addBox(16.5F, 10.5F, 7.0F, 7F, 2F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE_FRAME_3 = new ModelPartData("coreFrame3", CubeListBuilder.create()
            .texOffs(8, 21)
            .addBox(16.5F, 7.5F, 7.0F, 2F, 3F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE_FRAME_4 = new ModelPartData("coreFrame4", CubeListBuilder.create()
            .texOffs(17, 18)
            .addBox(21.5F, 7.5F, 7.0F, 2F, 3F, 3F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CORE = new ModelPartData("core", CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(18.0F, 7.3F, 7.5F, 4F, 4F, 2F),
            PartPose.rotation(0.0F, 0.0F, 0.7853982F));

    private static final ModelPartData CATKIN_LEFT = new ModelPartData("catkinLeft", CubeListBuilder.create()
            .texOffs(6, 6)
            .addBox(2.8F, 13.5F, 8.5F, 3F, 7F, 0F));

    private static final ModelPartData CATKIN_RIGHT = new ModelPartData("catkinRight", CubeListBuilder.create()
            .texOffs(0, 6)
            .addBox(9.75F, 13.5F, 8.5F, 3F, 7F, 0F));

        /*
        BLADE
         */

    private static final ModelPartData B1 = new ModelPartData("b1", CubeListBuilder.create()
            .texOffs(10, 16).mirror()
            .addBox(26F, 17F, 8F, 1F, 4F, 1F));

    private static final ModelPartData B2 = new ModelPartData("b2", CubeListBuilder.create()
            .texOffs(0, 18).mirror()
            .addBox(25F, 19F, 8F, 1F, 4F, 1F));

    private static final ModelPartData B3 = new ModelPartData("b3", CubeListBuilder.create()
            .texOffs(14, 18).mirror()
            .addBox(24F, 20F, 8F, 1F, 4F, 1F));

    private static final ModelPartData B4 = new ModelPartData("b4", CubeListBuilder.create()
            .texOffs(18, 18).mirror()
            .addBox(23F, 21F, 8F, 1F, 4F, 1F));

    private static final ModelPartData B5 = new ModelPartData("b5", CubeListBuilder.create()
            .texOffs(16, 9).mirror()
            .addBox(22F, 22F, 8F, 1F, 4F, 1F));

    private static final ModelPartData B6 = new ModelPartData("b6", CubeListBuilder.create()
            .texOffs(0, 13).mirror()
            .addBox(20F, 23F, 8F, 2F, 4F, 1F));

    private static final ModelPartData B7 = new ModelPartData("b7", CubeListBuilder.create()
            .texOffs(19, 4).mirror()
            .addBox(18F, 24F, 8F, 2F, 1F, 1F));

    private static final ModelPartData B8 = new ModelPartData("b8", CubeListBuilder.create()
            .texOffs(12, 5).mirror()
            .addBox(17F, 25F, 8F, 3F, 3F, 1F));

    private static final ModelPartData B9 = new ModelPartData("b9", CubeListBuilder.create()
            .texOffs(12, 0).mirror()
            .addBox(14F, 25F, 8F, 3F, 4F, 1F));

    private static final ModelPartData B10 = new ModelPartData("b10", CubeListBuilder.create()
            .texOffs(6, 13).mirror()
            .addBox(13F, 25F, 8F, 1F, 5F, 1F));

    private static final ModelPartData B11 = new ModelPartData("b11", CubeListBuilder.create()
            .texOffs(12, 9).mirror()
            .addBox(12F, 24F, 8F, 1F, 6F, 1F));

    private static final ModelPartData B12 = new ModelPartData("b12", CubeListBuilder.create()
            .texOffs(20, 0).mirror()
            .addBox(11F, 23F, 8F, 1F, 3F, 1F));

    private static final ModelPartData B13 = new ModelPartData("b13", CubeListBuilder.create()
            .texOffs(19, 3).mirror()
            .addBox(10F, 24F, 8F, 1F, 1F, 1F));

    private static final ModelPartData B14 = new ModelPartData("b14", CubeListBuilder.create()
            .texOffs(15, 15).mirror()
            .addBox(9F, 22F, 8F, 2F, 2F, 1F));

    private static final ModelPartData B15 = new ModelPartData("b15", CubeListBuilder.create()
            .texOffs(20, 6).mirror()
            .addBox(11F, 28F, 8F, 1F, 2F, 1F));

    private static final ModelPartData B16 = new ModelPartData("b16", CubeListBuilder.create()
            .texOffs(4, 19).mirror()
            .addBox(9F, 29F, 8F, 2F, 1F, 1F));


    private static final ModelPartData R1 = new ModelPartData("r1", CubeListBuilder.create()
            .texOffs(10, 16)
            .addBox(-11F, 17F, 8F, 1F, 4F, 1F));

    private static final ModelPartData R2 = new ModelPartData("r2", CubeListBuilder.create()
            .texOffs(0, 18)
            .addBox(-10F, 19F, 8F, 1F, 4F, 1F));

    private static final ModelPartData R3 = new ModelPartData("r3", CubeListBuilder.create()
            .texOffs(14, 18)
            .addBox(-9F, 20F, 8F, 1F, 4F, 1F));

    private static final ModelPartData R4 = new ModelPartData("r4", CubeListBuilder.create()
            .texOffs(18, 18)
            .addBox(-8F, 21F, 8F, 1F, 4F, 1F));

    private static final ModelPartData R5 = new ModelPartData("r5", CubeListBuilder.create()
            .texOffs(16, 9)
            .addBox(-7F, 22F, 8F, 1F, 4F, 1F));

    private static final ModelPartData R6 = new ModelPartData("r6", CubeListBuilder.create()
            .texOffs(0, 13)
            .addBox(-6F, 23F, 8F, 2F, 4F, 1F));

    private static final ModelPartData R7 = new ModelPartData("r7", CubeListBuilder.create()
            .texOffs(19, 4)
            .addBox(-4F, 24F, 8F, 2F, 1F, 1F));

    private static final ModelPartData R8 = new ModelPartData("r8", CubeListBuilder.create()
            .texOffs(12, 5)
            .addBox(-4F, 25F, 8F, 3F, 3F, 1F));

    private static final ModelPartData R9 = new ModelPartData("r9", CubeListBuilder.create()
            .texOffs(12, 0)
            .addBox(-1F, 25F, 8F, 3F, 4F, 1F));

    private static final ModelPartData R10 = new ModelPartData("r10", CubeListBuilder.create()
            .texOffs(6, 13)
            .addBox(2F, 25F, 8F, 1F, 5F, 1F));

    private static final ModelPartData R11 = new ModelPartData("r11", CubeListBuilder.create()
            .texOffs(12, 9)
            .addBox(3F, 24F, 8F, 1F, 6F, 1F));

    private static final ModelPartData R12 = new ModelPartData("r12", CubeListBuilder.create()
            .texOffs(20, 0)
            .addBox(4F, 23F, 8F, 1F, 3F, 1F));

    private static final ModelPartData R13 = new ModelPartData("r13", CubeListBuilder.create()
            .texOffs(19, 13)
            .addBox(5F, 24F, 8F, 1F, 1F, 1F));

    private static final ModelPartData R14 = new ModelPartData("r14", CubeListBuilder.create()
            .texOffs(15, 15)
            .addBox(5F, 22F, 8F, 2F, 2F, 1F));

    private static final ModelPartData R15 = new ModelPartData("r15", CubeListBuilder.create()
            .texOffs(20, 6)
            .addBox(4F, 28F, 8F, 1F, 2F, 1F));

    private static final ModelPartData R16 = new ModelPartData("r16", CubeListBuilder.create()
            .texOffs(4, 19)
            .addBox(5F, 29F, 8F, 2F, 1F, 1F));

    public static LayerDefinition createLayerDefinition() {
        return createLayerDefinition(32, 32, HANDLE, CAP, RING_UP, RING_DOWN, CORE,
                CORE_FRAME_1, CORE_FRAME_2, CORE_FRAME_3, CORE_FRAME_4, CATKIN_LEFT, CATKIN_RIGHT,
                B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16,
                R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16);
    }

    private final RenderType BLADE_RENDER_TYPE = MekanismRenderType.BLADE.apply(PLASMA_PICKAXE_BLADE_TEXTURE);
    private final RenderType RENDER_TYPE = renderType(PLASMA_PICKAXE_TEXTURE);
    private final List<ModelPart> parts;
    private final List<ModelPart> bladeParts;

    public ModelPlasmaPickaxe(EntityModelSet entityModelSet) {
        super(RenderType::entitySolid);
        ModelPart root = entityModelSet.bakeLayer(PLASMA_PICKAXE_LAYER);
        parts = getRenderableParts(root, HANDLE, CAP, RING_UP, RING_DOWN, CORE_FRAME_1, CORE_FRAME_2,
                CORE_FRAME_3, CORE_FRAME_4);
        bladeParts = getRenderableParts(root, CORE, CATKIN_LEFT, CATKIN_RIGHT,
                B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16,
                R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16);
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
