package su.gamepoint.morepipes.common.block.transmitter.logisticaltransporter;

import mekanism.api.RelativeSide;
import mekanism.api.text.EnumColor;
import mekanism.common.content.network.transmitter.LogisticalTransporter;
import mekanism.common.content.network.transmitter.LogisticalTransporterBase;
import mekanism.common.content.transporter.TransporterManager;
import mekanism.common.content.transporter.TransporterStack;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.TileEntityLogisticalSorter;
import mekanism.common.tile.interfaces.ISideConfiguration;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import mekanism.common.util.CapabilityUtils;
import mekanism.common.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * @author Dudko Roman
 */
public class MoreTransporterUtils {
    public static final List<EnumColor> colors;

    private MoreTransporterUtils() {
    }

    @Nullable
    public static EnumColor readColor(int inputColor) {
        return inputColor == -1 ? null : colors.get(inputColor);
    }

    public static int getColorIndex(@Nullable EnumColor color) {
        return color == null ? -1 : colors.indexOf(color);
    }

    public static boolean isValidAcceptorOnSide(BlockEntity tile, Direction side) {
        if (tile instanceof TileEntityTransmitter transmitter) {
            if (TransmissionType.ITEM.checkTransmissionType(transmitter)) {
                return false;
            }
        }

        return InventoryUtils.isItemHandler(tile, side.getOpposite());
    }

    public static EnumColor increment(EnumColor color) {
        if (color == null) {
            return (EnumColor)colors.get(0);
        } else {
            int index = colors.indexOf(color);
            return index == colors.size() - 1 ? null : (EnumColor)colors.get(index + 1);
        }
    }

    public static EnumColor decrement(EnumColor color) {
        if (color == null) {
            return (EnumColor)colors.get(colors.size() - 1);
        } else {
            int index = colors.indexOf(color);
            return index == 0 ? null : (EnumColor)colors.get(index - 1);
        }
    }

    public static void drop(LogisticalTransporterBase transporter, TransporterStack stack) {
        BlockPos blockPos = transporter.getTilePos();
        if (stack.hasPath()) {
            float[] pos = getStackPosition(transporter, stack, 0.0F);
            blockPos = blockPos.offset((double)pos[0], (double)pos[1], (double)pos[2]);
        }

        TransporterManager.remove(transporter.getTileWorld(), stack);
        Block.popResource(transporter.getTileWorld(), blockPos, stack.itemStack);
    }

    public static float[] getStackPosition(LogisticalTransporterBase transporter, TransporterStack stack, float partial) {
        Direction side = stack.getSide(transporter);
        float progress = ((float)stack.progress + partial) / 100.0F - 0.5F;
        return new float[]{0.5F + (float)side.getStepX() * progress, 0.25F + (float)side.getStepY() * progress, 0.5F + (float)side.getStepZ() * progress};
    }

    public static void incrementColor(MoreLogisticalTransporter tile) {
        EnumColor color = tile.getColor();
        if (color == null) {
            tile.setColor((EnumColor)colors.get(0));
        } else {
            int index = colors.indexOf(color);
            if (index == colors.size() - 1) {
                tile.setColor((EnumColor)null);
            } else {
                tile.setColor((EnumColor)colors.get(index + 1));
            }
        }

    }

    public static boolean canInsert(BlockEntity tile, EnumColor color, ItemStack itemStack, Direction side, boolean force) {
        if (force && tile instanceof TileEntityLogisticalSorter sorter) {
            return sorter.canSendHome(itemStack);
        } else {
            if (!force && tile instanceof ISideConfiguration config) {
                if (config.getEjector().hasStrictInput()) {
                    Direction tileSide = config.getDirection();
                    EnumColor configColor = config.getEjector().getInputColor(RelativeSide.fromDirections(tileSide, side.getOpposite()));
                    if (configColor != null && configColor != color) {
                        return false;
                    }
                }
            }

            Optional<IItemHandler> capability = CapabilityUtils.getCapability(tile, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).resolve();
            if (capability.isPresent()) {
                IItemHandler inventory = (IItemHandler)capability.get();

                for(int i = 0; i < inventory.getSlots(); ++i) {
                    if (inventory.isItemValid(i, itemStack)) {
                        ItemStack rejects = inventory.insertItem(i, itemStack, true);
                        if (TransporterManager.didEmit(itemStack, rejects)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    static {
        colors = List.of(EnumColor.DARK_BLUE, EnumColor.DARK_GREEN, EnumColor.DARK_AQUA, EnumColor.DARK_RED, EnumColor.PURPLE, EnumColor.INDIGO, EnumColor.BRIGHT_GREEN, EnumColor.AQUA, EnumColor.RED, EnumColor.PINK, EnumColor.YELLOW, EnumColor.BLACK);
    }
}
