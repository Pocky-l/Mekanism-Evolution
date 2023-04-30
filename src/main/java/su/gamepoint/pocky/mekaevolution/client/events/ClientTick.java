package su.gamepoint.pocky.mekaevolution.client.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.ECTier;

/**
 * @author Dudko Roman
 */
@Mod.EventBusSubscriber(modid = MekanismEvolutionMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientTick {

    @SubscribeEvent
    public void onTickClientTick(TickEvent.ClientTickEvent event) {
        ECTier.tick();
    }
}
