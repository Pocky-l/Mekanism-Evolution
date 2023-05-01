package su.gamepoint.pocky.mekaevolution.common.block.transmitter.cable;

import mekanism.common.lib.transmitter.ConnectionType;
import mekanism.common.util.EnumUtils;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * @author Dudko Roman
 */
public class EvoBlockSmallTransmitter extends EvoBlockTransmitter {

    private static final VoxelShape[] SIDES = new VoxelShape[EnumUtils.DIRECTIONS.length];
    private static final VoxelShape[] SIDES_PULL = new VoxelShape[EnumUtils.DIRECTIONS.length];
    private static final VoxelShape[] SIDES_PUSH = new VoxelShape[EnumUtils.DIRECTIONS.length];
    public static final VoxelShape CENTER;

    public EvoBlockSmallTransmitter() {
    }

    public EvoBlockSmallTransmitter(Properties properties) {
        super(properties);
    }

    static {
        VoxelShapeUtils.setShape(box(5, 0, 5, 11, 5, 11), SIDES, true);
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
                box(5, 4, 5, 11, 5, 11),
                box(6, 2, 6, 10, 4, 10),
                box(4, 0, 4, 12, 2, 12)
        ), SIDES_PULL, true);
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
                box(5, 3, 5, 11, 5, 11),
                box(6, 1, 6, 10, 3, 10),
                box(7, 0, 7, 9, 1, 9)
        ), SIDES_PUSH, true);
        CENTER = box(5, 5, 5, 11, 11, 11);
    }

    public static VoxelShape getSideForType(ConnectionType type, Direction side) {
        if (type == ConnectionType.PUSH) {
            return SIDES_PUSH[side.ordinal()];
        } else if (type == ConnectionType.PULL) {
            return SIDES_PULL[side.ordinal()];
        } //else normal
        return SIDES[side.ordinal()];
    }

    @Override
    protected VoxelShape getCenter() {
        return CENTER;
    }

    @Override
    protected VoxelShape getSide(ConnectionType type, Direction side) {
        return getSideForType(type, side);
    }
}
