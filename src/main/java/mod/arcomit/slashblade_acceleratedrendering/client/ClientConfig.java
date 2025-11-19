package mod.arcomit.slashblade_acceleratedrendering.client;

import mod.arcomit.slashblade_acceleratedrendering.SlashBlade_AcceleratedRendering;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

public class ClientConfig {
    // client
    public static ForgeConfigSpec.BooleanValue ENABLES_ACCELERATED_RENDERING;

    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("DEBUG Setting");
        ENABLES_ACCELERATED_RENDERING = builder.comment("Enable the accelerated rendering?[Default:true]")
                .define("enablesAcceleratedRendering", true);
        builder.pop();
        SPEC = builder.build();
    }
}
