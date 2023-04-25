package su.gamepoint.morepipes.config;

import mekanism.common.config.BaseMekanismConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import su.gamepoint.morepipes.MorePipesMod;

public class PipeConfig extends BaseMekanismConfig {

    private final ForgeConfigSpec configSpec = null;

    public PipeConfig() {

    }

    public String getFileName() {
        return MorePipesMod.MODID;
    }

    public ForgeConfigSpec getConfigSpec() {
        return this.configSpec;
    }

    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }
}
