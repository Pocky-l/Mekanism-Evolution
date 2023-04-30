package su.gamepoint.pocky.mekaevolution.config;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import su.gamepoint.pocky.mekaevolution.MekanismEvolutionMod;

public class EvoConfig extends BaseMekanismConfig {

    private final ForgeConfigSpec configSpec;
    public final CachedFloatingLongValue absoluteUniversalCableCapacity;
    public final CachedFloatingLongValue supremeUniversalCableCapacity;
    public final CachedFloatingLongValue cosmicUniversalCableCapacity;
    public final CachedFloatingLongValue infiniteUniversalCableCapacity;

    public final CachedFloatingLongValue absoluteMechanicalPipeCapacity;
    public final CachedFloatingLongValue absoluteMechanicalPipePullAmount;
    public final CachedFloatingLongValue supremeMechanicalPipeCapacity;
    public final CachedFloatingLongValue supremeMechanicalPipePullAmount;
    public final CachedFloatingLongValue cosmicMechanicalPipeCapacity;
    public final CachedFloatingLongValue cosmicMechanicalPipePullAmount;
    public final CachedFloatingLongValue infiniteMechanicalPipeCapacity;
    public final CachedFloatingLongValue infiniteMechanicalPipePullAmount;

    public final CachedFloatingLongValue absoluteLogisticalTransporterSpeed;
    public final CachedFloatingLongValue absoluteLogisticalTransporterPullAmount;
    public final CachedFloatingLongValue supremeLogisticalTransporterSpeed;
    public final CachedFloatingLongValue supremeLogisticalTransporterPullAmount;
    public final CachedFloatingLongValue cosmicLogisticalTransporterSpeed;
    public final CachedFloatingLongValue cosmicLogisticalTransporterPullAmount;
    public final CachedFloatingLongValue infiniteLogisticalTransporterSpeed;
    public final CachedFloatingLongValue infiniteLogisticalTransporterPullAmount;

    public EvoConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        final String noteUC = "Internal buffer in Joules of each 'TIER' universal cable";
        builder.comment("Universal Cables").push(MekanismEvolutionMod.MODID);
        this.absoluteUniversalCableCapacity = CachedFloatingLongValue.define(this, builder, noteUC, "absoluteUniversalCable", FloatingLong.createConst(65536000L));
        this.supremeUniversalCableCapacity = CachedFloatingLongValue.define(this, builder, noteUC, "supremeUniversalCable", FloatingLong.createConst(524288000L));
        this.cosmicUniversalCableCapacity = CachedFloatingLongValue.define(this, builder, noteUC, "cosmicUniversalCable", FloatingLong.createConst(4194304000L));
        this.infiniteUniversalCableCapacity = CachedFloatingLongValue.define(this, builder, noteUC, "infiniteUniversalCable", FloatingLong.createConst(33554432000L));

        final String noteMP = "Capacity of 'TIER' mechanical pipes in mB.";
        final String noteMP2 = "Pump rate of 'TIER' mechanical pipes in mB/t.";
        builder.comment("Mechanical Pipes").push(MekanismEvolutionMod.MODID);
        this.absoluteMechanicalPipeCapacity = CachedFloatingLongValue.define(this, builder, noteMP, "absoluteMechanicalPipesCapacity", FloatingLong.createConst(512000L));
        this.absoluteMechanicalPipePullAmount = CachedFloatingLongValue.define(this, builder, noteMP2, "absoluteMechanicalPipesPullAmount", FloatingLong.createConst(128000));
        this.supremeMechanicalPipeCapacity = CachedFloatingLongValue.define(this, builder, noteMP, "supremeMechanicalPipesCapacity", FloatingLong.createConst(2048000L));
        this.supremeMechanicalPipePullAmount = CachedFloatingLongValue.define(this, builder, noteMP2, "supremeMechanicalPipesPullAmount", FloatingLong.createConst(512000));
        this.cosmicMechanicalPipeCapacity = CachedFloatingLongValue.define(this, builder, noteMP, "cosmicMechanicalPipesCapacity", FloatingLong.createConst(8192000L));
        this.cosmicMechanicalPipePullAmount = CachedFloatingLongValue.define(this, builder, noteMP2, "cosmicMechanicalPipesPullAmount", FloatingLong.createConst(2048000));
        this.infiniteMechanicalPipeCapacity = CachedFloatingLongValue.define(this, builder, noteMP, "infiniteMechanicalPipesCapacity", FloatingLong.createConst(32768000L));
        this.infiniteMechanicalPipePullAmount = CachedFloatingLongValue.define(this, builder, noteMP2, "infiniteMechanicalPipesPullAmount", FloatingLong.createConst(8192000));

        final String noteLT = "Five times the travel speed in m/s of 'TIER' logistical transporter.";
        final String noteLT2 = "Item throughput rate of 'TIER' logistical transporters in items/half second.";
        builder.comment("Logistical Transporters").push(MekanismEvolutionMod.MODID);
        this.absoluteLogisticalTransporterSpeed = CachedFloatingLongValue.define(this, builder, noteLT, "absoluteLogisticalTransporterSpeed", FloatingLong.createConst(100));
        this.absoluteLogisticalTransporterPullAmount = CachedFloatingLongValue.define(this, builder, noteLT2, "absoluteLogisticalTransporterPullAmount", FloatingLong.createConst(128));
        this.supremeLogisticalTransporterSpeed = CachedFloatingLongValue.define(this, builder, noteLT, "supremeLogisticalTransporterSpeed", FloatingLong.createConst(200));
        this.supremeLogisticalTransporterPullAmount = CachedFloatingLongValue.define(this, builder, noteLT2, "supremeLogisticalTransporterPullAmount", FloatingLong.createConst(256));
        this.cosmicLogisticalTransporterSpeed = CachedFloatingLongValue.define(this, builder, noteLT, "cosmicLogisticalTransporterSpeed", FloatingLong.createConst(400));
        this.cosmicLogisticalTransporterPullAmount = CachedFloatingLongValue.define(this, builder, noteLT2, "cosmicLogisticalTransporterPullAmount", FloatingLong.createConst(512));
        this.infiniteLogisticalTransporterSpeed = CachedFloatingLongValue.define(this, builder, noteLT, "infiniteLogisticalTransporterSpeed", FloatingLong.createConst(800));
        this.infiniteLogisticalTransporterPullAmount = CachedFloatingLongValue.define(this, builder, noteLT2, "infiniteLogisticalTransporterPullAmount", FloatingLong.createConst(1024));

        builder.pop();
        this.configSpec = builder.build();
    }

    public String getFileName() {
        return MekanismEvolutionMod.MODID;
    }

    public ForgeConfigSpec getConfigSpec() {
        return this.configSpec;
    }

    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }
}
