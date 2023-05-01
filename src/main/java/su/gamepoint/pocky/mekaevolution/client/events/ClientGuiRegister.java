package su.gamepoint.pocky.mekaevolution.client.events;

import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.client.energycube.gui.EvoGuiEnergyCube;
import su.gamepoint.pocky.mekaevolution.registers.EvoContainerTypes;

/**
 * @author Dudko Roman
 */
@Mod.EventBusSubscriber(modid = MekanismEvolutionMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientGuiRegister {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registry.MENU_REGISTRY, helper -> {
            ClientRegistrationUtil.registerScreen(EvoContainerTypes.ENERGY_CUBE, EvoGuiEnergyCube::new);
        });
    }
}
