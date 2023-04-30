package su.gamepoint.pocky.mekaevolution.client.events;

import mekanism.client.ClientRegistrationUtil;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.client.energycube.gui.EvoGuiEnergyCube;
import su.gamepoint.pocky.mekaevolution.registers.EvoContainerTypes;

/**
 * @author Dudko Roman
 */
@Mod.EventBusSubscriber(modid = MekanismEvolutionMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientGuiRegister {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
        ClientRegistrationUtil.registerScreen(EvoContainerTypes.ENERGY_CUBE, EvoGuiEnergyCube::new);
    }
}
