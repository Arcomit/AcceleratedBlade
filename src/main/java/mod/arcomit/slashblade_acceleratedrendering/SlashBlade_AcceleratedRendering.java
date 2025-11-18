package mod.arcomit.slashblade_acceleratedrendering;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-02 16:48
 * @Description: MOD主类
 */
@SuppressWarnings("removal")
@Mod(SlashBlade_AcceleratedRendering.MODID)
public class SlashBlade_AcceleratedRendering {

    public static final String MODID = "slashblade_acceleratedrendering";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SlashBlade_AcceleratedRendering() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(SlashBlade_AcceleratedRendering.MODID, path);
    }
}
