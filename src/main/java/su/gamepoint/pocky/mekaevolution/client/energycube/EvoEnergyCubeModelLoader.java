package su.gamepoint.pocky.mekaevolution.client.energycube;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import mekanism.api.RelativeSide;
import mekanism.common.util.EnumUtils;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import org.jetbrains.annotations.NotNull;


/**
 * @author Dudko Roman
 */
public class EvoEnergyCubeModelLoader implements IGeometryLoader<EvoEnergyCubeGeometry> {

    public static final EvoEnergyCubeModelLoader INSTANCE = new EvoEnergyCubeModelLoader();

    private EvoEnergyCubeModelLoader() {
    }

    @NotNull
    @Override
    public EvoEnergyCubeGeometry read(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext ctx) {
        List<BlockElement> frame = readElements(jsonObject, ctx, "frame");
        Map<RelativeSide, List<BlockElement>> leds = new EnumMap<>(RelativeSide.class);
        Map<RelativeSide, List<BlockElement>> ports = new EnumMap<>(RelativeSide.class);
        for (RelativeSide side : EnumUtils.SIDES) {
            String name = side.name().toLowerCase(Locale.ROOT);
            leds.put(side, readElements(jsonObject, ctx, name + "LEDs"));
            ports.put(side, readElements(jsonObject, ctx, name + "Port"));
        }
        return new EvoEnergyCubeGeometry(frame, leds, ports);
    }

    private List<BlockElement> readElements(JsonObject jsonObject, JsonDeserializationContext ctx, String key) {
        List<BlockElement> elements = new ArrayList<>();
        for (JsonElement element : GsonHelper.getAsJsonArray(jsonObject, key)) {
            elements.add(ctx.deserialize(element, BlockElement.class));
        }
        if (elements.isEmpty()) {
            throw new JsonParseException("Energy cube models requires a \"" + key + "\" element with at least one element.");
        }
        return elements;
    }
}
