package su.gamepoint.pocky.mekaevolution.common.block.storages.energycube;

import mekanism.api.NBTConstants;
import mekanism.api.RelativeSide;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeStateFacing;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tier.EnergyCubeTier;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ISlotInfo;
import mekanism.common.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dudko Roman
 */
public class EvoBlockEnergyCube extends BlockTile.BlockTileModel<EvoTileEntityEnergyCube, Machine<EvoTileEntityEnergyCube>> {
    private static final VoxelShape[] bounds = new VoxelShape[256];

    static {
        VoxelShape frame = VoxelShapeUtils.combine(
                box(0, 0, 0, 3, 3, 16),
                box(0, 3, 0, 3, 16, 3),
                box(0, 3, 13, 3, 16, 16),
                box(0, 13, 3, 3, 16, 13),
                box(3, 0, 0, 16, 3, 3),
                box(3, 0, 13, 16, 3, 16),
                box(3, 13, 0, 16, 16, 3),
                box(3, 13, 13, 16, 16, 16),
                box(13, 0, 3, 16, 3, 13),
                box(13, 3, 0, 16, 13, 3),
                box(13, 3, 13, 16, 13, 16),
                box(13, 13, 3, 16, 16, 13),
                box(12.5, 14.9, 7.5, 13.5, 15.9, 8.5),//ledTop1
                box(2.5, 14.9, 7.5, 3.5, 15.9, 8.5),//ledTop2
                box(12.5, 7.5, 0.1, 13.5, 8.5, 1.1),//ledBack1
                box(2.5, 7.5, 0.1, 3.5, 8.5, 1.1),//ledBack2
                box(2.5, 0.1, 7.5, 3.5, 1.1, 8.5),//ledBottom2
                box(12.5, 0.1, 7.5, 13.5, 1.1, 8.5),//ledBottom1
                box(12.5, 7.5, 14.9, 13.5, 8.5, 15.9),//ledFront1
                box(2.5, 7.5, 14.9, 3.5, 8.5, 15.9),//ledFront2
                box(0.1, 7.5, 2.5, 1.1, 8.5, 3.5),//ledRight2
                box(0.1, 7.5, 12.5, 1.1, 8.5, 13.5),//ledRight1
                box(14.9, 7.5, 2.5, 15.9, 8.5, 3.5),//ledLeft1
                box(14.9, 7.5, 12.5, 15.9, 8.5, 13.5)//ledLeft2
        );
        VoxelShape frontPanel = VoxelShapeUtils.combine(
                box(3, 5, 14, 13, 11, 15),//connectorFrontToggle
                box(4, 4, 15, 12, 12, 16)//portFrontToggle
        );
        VoxelShape rightPanel = VoxelShapeUtils.combine(
                box(1, 5, 3, 2, 11, 13),//connectorRightToggle
                box(0, 4, 4, 1, 12, 12)//portRightToggle
        );
        VoxelShape leftPanel = VoxelShapeUtils.combine(
                box(14, 5, 3, 15, 11, 13),//connectorLeftToggle
                box(15, 4, 4, 16, 12, 12)//portLeftToggle
        );
        VoxelShape backPanel = VoxelShapeUtils.combine(
                box(3, 5, 1, 13, 11, 2),//connectorBackToggle
                box(4, 4, 0, 12, 12, 1)//portBackToggle
        );
        VoxelShape topPanel = VoxelShapeUtils.combine(
                box(3, 14, 5, 13, 15, 11),//connectorTopToggle
                box(4, 15, 4, 12, 16, 12)//portTopToggle
        );
        VoxelShape bottomPanel = VoxelShapeUtils.combine(
                box(3, 1, 5, 13, 2, 11),//connectorBottomToggle
                box(4, 0, 4, 12, 1, 12)//portBottomToggle
        );
        VoxelShape frameRotated = VoxelShapeUtils.rotate(frame, Rotation.CLOCKWISE_90);
        VoxelShape topRotated = VoxelShapeUtils.rotate(topPanel, Rotation.CLOCKWISE_90);
        VoxelShape bottomRotated = VoxelShapeUtils.rotate(bottomPanel, Rotation.CLOCKWISE_90);
        VoxelShape frameRotatedAlt = VoxelShapeUtils.rotate(frame, Direction.NORTH);
        VoxelShape rightRotated = VoxelShapeUtils.rotate(rightPanel, Direction.NORTH);
        VoxelShape leftRotated = VoxelShapeUtils.rotate(leftPanel, Direction.NORTH);
        for (int rotated = 0; rotated < 3; rotated++) {
            //If we don't need to rotate anything, this is zero
            // If we need to rotate the top and bottom frames, this is one
            // If we need to rotate the left and right frames, this is two
            boolean rotateVertical = rotated == 1;
            boolean rotateHorizontal = rotated == 2;
            VoxelShape baseFrame = rotateVertical ? frameRotated : rotateHorizontal ? frameRotatedAlt : frame;
            for (int top = 0; top < 2; top++) {
                VoxelShape withTop = top == 0 ? baseFrame : Shapes.or(baseFrame, rotateVertical ? topRotated : topPanel);
                for (int bottom = 0; bottom < 2; bottom++) {
                    VoxelShape withBottom = bottom == 0 ? withTop : Shapes.or(withTop, rotateVertical ? bottomRotated : bottomPanel);
                    for (int front = 0; front < 2; front++) {
                        VoxelShape withFront = front == 0 ? withBottom : Shapes.or(withBottom, frontPanel);
                        for (int back = 0; back < 2; back++) {
                            VoxelShape withBack = back == 0 ? withFront : Shapes.or(withFront, backPanel);
                            for (int left = 0; left < 2; left++) {
                                VoxelShape withLeft = left == 0 ? withBack : Shapes.or(withBack, rotateHorizontal ? leftRotated : leftPanel);
                                for (int right = 0; right < 2; right++) {
                                    VoxelShape withRight = right == 0 ? withLeft : Shapes.or(withLeft, rotateHorizontal ? rightRotated : rightPanel);
                                    bounds[getIndex(top, bottom, front, back, left, right, rotateVertical, rotateHorizontal)] = withRight;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 0 for an input is equivalent to false, 1 is equivalent to true
     */
    private static int getIndex(int top, int bottom, int front, int back, int left, int right, boolean rotateVertical, boolean rotateHorizontal) {
        return ((((((top | bottom << 1) | front << 2) | back << 3) | left << 4) | right << 5) | (rotateVertical ? 1 : 0) << 6) | (rotateHorizontal ? 1 : 0) << 7;
    }


    public EvoBlockEnergyCube(Machine<EvoTileEntityEnergyCube> type) {
        //Note: We require setting variable opacity so that the block state does not cache the ability of if blocks can be placed on top of the energy cube
        // this may change based on what sides are enabled. Torches cannot be placed on the sides due to vanilla checking the incorrect shape
        super(type, BlockBehaviour.Properties.of(Material.METAL).strength(2, 2.4F).requiresCorrectToolForDrops().dynamicShape());
    }

    private ItemStack withSideConfig(DataType dataType) {
        CompoundTag sideConfig = new CompoundTag();
        for (RelativeSide side : EnumUtils.SIDES) {
            NBTUtils.writeEnum(sideConfig, NBTConstants.SIDE + side.ordinal(), dataType);
        }
        CompoundTag configNBT = new CompoundTag();
        configNBT.put(NBTConstants.CONFIG + TransmissionType.ENERGY.ordinal(), sideConfig);
        ItemStack stack = new ItemStack(this);
        ItemDataUtils.setCompound(stack, NBTConstants.COMPONENT_CONFIG, configNBT);
        return stack;
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab group, @NotNull NonNullList<ItemStack> items) {
        EnergyCubeTier tier = Attribute.getTier(this, EnergyCubeTier.class);
        if (tier == EnergyCubeTier.CREATIVE) {
            //Add the empty and charged variants
            items.add(withSideConfig(DataType.INPUT));
            items.add(StorageUtils.getFilledEnergyVariant(withSideConfig(DataType.OUTPUT), ECTier.getMaxEnergy(tier)));
        } else {
            super.fillItemCategory(group, items);
            if (tier != null) {
                //This should never be null, but validate it just in case, and then add the charged variant
                items.add(StorageUtils.getFilledEnergyVariant(new ItemStack(this), ECTier.getMaxEnergy(tier)));
            }
        }
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        EvoTileEntityEnergyCube energyCube = WorldUtils.getTileEntity(EvoTileEntityEnergyCube.class, world, pos, true);
        int index;
        if (energyCube == null) {
            //Default to facing north all enabled
            index = getIndex(1, 1, 1, 1, 1, 1, false, false);
        } else {
            ConfigInfo energyConfig = energyCube.configComponent.getConfig(TransmissionType.ENERGY);
            if (energyConfig == null) {
                //Default to facing north all enabled
                index = getIndex(1, 1, 1, 1, 1, 1, false, false);
            } else {
                Direction facing = Attribute.get(this, AttributeStateFacing.class).getDirection(state);
                index = getIndex(
                        isSideEnabled(energyConfig, facing, Direction.UP),//top
                        isSideEnabled(energyConfig, facing, Direction.DOWN),//bottom
                        isSideEnabled(energyConfig, facing, Direction.SOUTH),//front
                        isSideEnabled(energyConfig, facing, Direction.NORTH),//back
                        isSideEnabled(energyConfig, facing, Direction.EAST),//left
                        isSideEnabled(energyConfig, facing, Direction.WEST),//right
                        facing == Direction.EAST || facing == Direction.WEST,
                        facing == Direction.DOWN || facing == Direction.UP
                );
            }
        }
        return bounds[index];
    }

    /**
     * @return 1 if the side is enabled, 0 otherwise
     */
    private static int isSideEnabled(ConfigInfo energyConfig, Direction facing, Direction side) {
        ISlotInfo slotInfo = energyConfig.getSlotInfo(RelativeSide.fromDirections(facing, side));
        return slotInfo != null && slotInfo.isEnabled() ? 1 : 0;
    }
}
