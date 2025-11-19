package mod.arcomit.slashblade_acceleratedrendering.mixin;

import mod.arcomit.slashblade_acceleratedrendering.utils.RenderStateUtils;
import mods.flammpfeil.slashblade.client.renderer.util.BladeRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-11-19
 * @Description: Mixin to completely override BladeRenderState methods
 */
@Mixin(value = BladeRenderState.class, remap = false)
public class BladeRenderStateMixin {

    @Overwrite
    public static RenderType getSlashBladeBlend(ResourceLocation texture) {
        return RenderStateUtils.getSlashBladeBlend(texture);
    }

    @Overwrite
    public static RenderType getSlashBladeGlint() {
        return RenderStateUtils.getSlashBladeGlint();
    }

    @Overwrite
    public static RenderType getSlashBladeItemGlint() {
        return RenderStateUtils.getSlashBladeItemGlint();
    }

    @Overwrite
    public static RenderType getSlashBladeBlendColorWrite(ResourceLocation texture) {
        return RenderStateUtils.getSlashBladeBlendColorWrite(texture);
    }

    @Overwrite
    public static RenderType getSlashBladeBlendLuminous(ResourceLocation texture) {
        return RenderStateUtils.getSlashBladeBlendLuminous(texture);
    }

    @Overwrite
    public static RenderType getChargeEffect(ResourceLocation texture, float x, float y) {
        return RenderStateUtils.getChargeEffect(texture,x,y);
    }

    @Overwrite
    public static RenderType getSlashBladeBlendLuminousDepthWrite(ResourceLocation texture) {
        return RenderStateUtils.getSlashBladeBlendLuminousDepthWrite(texture);
    }

    @Overwrite
    public static RenderType getSlashBladeBlendReverseLuminous(ResourceLocation texture) {
        return RenderStateUtils.getSlashBladeBlendReverseLuminous(texture);
    }
}

