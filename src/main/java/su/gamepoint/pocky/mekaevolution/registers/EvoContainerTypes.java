package su.gamepoint.pocky.mekaevolution.registers;

import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;
import su.gamepoint.pocky.mekaevolution.common.block.storages.energycube.EvoTileEntityEnergyCube;

/**
 * @author Dudko Roman
 */
public class EvoContainerTypes {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MekanismEvolutionMod.MODID);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<EvoTileEntityEnergyCube>> ENERGY_CUBE = CONTAINER_TYPES.custom("energy_cube1", EvoTileEntityEnergyCube.class).armorSideBar(180, 41, 0).build();

}
