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

    private static final VoxelShape[] SIDES;
    private static final VoxelShape[] SIDES_PULL;
    private static final VoxelShape[] SIDES_PUSH;
    public static final VoxelShape CENTER;

    public EvoBlockSmallTransmitter() {
    }

    public EvoBlockSmallTransmitter(Properties properties) {
        super(properties);
    }

    public static VoxelShape getSideForType(ConnectionType type, Direction side) {
        if (type == ConnectionType.PUSH) {
            return SIDES_PUSH[side.ordinal()];
        } else {
            return type == ConnectionType.PULL ? SIDES_PULL[side.ordinal()] : SIDES[side.ordinal()];
        }
    }

    protected VoxelShape getCenter() {
        return CENTER;
    }

    protected VoxelShape getSide(ConnectionType type, Direction side) {
        return getSideForType(type, side);
    }

    static {
        SIDES = new VoxelShape[EnumUtils.DIRECTIONS.length];
        SIDES_PULL = new VoxelShape[EnumUtils.DIRECTIONS.length];
        SIDES_PUSH = new VoxelShape[EnumUtils.DIRECTIONS.length];
        VoxelShapeUtils.setShape(box(5.0, 0.0, 5.0, 11.0, 5.0, 11.0), SIDES, true);
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(box(5.0, 4.0, 5.0, 11.0, 5.0, 11.0), box(6.0, 2.0, 6.0, 10.0, 4.0, 10.0), box(4.0, 0.0, 4.0, 12.0, 2.0, 12.0)), SIDES_PULL, true);
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(box(5.0, 3.0, 5.0, 11.0, 5.0, 11.0), box(6.0, 1.0, 6.0, 10.0, 3.0, 10.0), box(7.0, 0.0, 7.0, 9.0, 1.0, 9.0)), SIDES_PUSH, true);
        CENTER = box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);
    }
}
