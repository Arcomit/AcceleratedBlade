package mod.arcomit.slashblade_acceleratedrendering.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import mods.flammpfeil.slashblade.client.renderer.util.BladeRenderState;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL14;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-11-19 16:36
 * @Description: TODO
 */
public class RenderStateUtils  extends RenderStateShard {

    public RenderStateUtils(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }

    private static final Map<ResourceLocation, RenderType> slashBladeBlendCache = new HashMap<>();
    private static final Map<ResourceLocation, RenderType> slashBladeBlendColorWriteCache = new HashMap<>();
    private static final Map<ResourceLocation, RenderType> slashBladeBlendLuminousCache = new HashMap<>();
    private static final Map<ChargeEffectKey, RenderType> chargeEffectCache = new HashMap<>();
    private static final Map<ResourceLocation, RenderType> luminousDepthWriteCache = new HashMap<>();
    private static final Map<ResourceLocation, RenderType> reverseLuminousCache = new HashMap<>();

    public static RenderType getSlashBladeBlend(ResourceLocation texture) {
        return slashBladeBlendCache.computeIfAbsent(texture, t -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER)
                    .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                    .setTextureState(new RenderStateShard.TextureStateShard(t, false, true))
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                    .createCompositeState(true);

            return RenderType.create("slashblade_blend_" + t, DefaultVertexFormat.NEW_ENTITY,
                    VertexFormat.Mode.TRIANGLES, 256, true, false, state);
        });
    }

    public static RenderType getSlashBladeGlint() {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_GLINT_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ENTITY, true, false))
                .setWriteMaskState(COLOR_WRITE)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setTransparencyState(GLINT_TRANSPARENCY)
                .setOutputState(ITEM_ENTITY_TARGET)
                .setTexturingState(ENTITY_GLINT_TEXTURING)
                .setOverlayState(OVERLAY)
                .createCompositeState(false);
        return RenderType.create("slashblade_glint", DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.TRIANGLES, 256, true, false, state);
    }

    public static RenderType getSlashBladeItemGlint() {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_GLINT_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ITEM, true, false))
                .setWriteMaskState(COLOR_WRITE)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setTransparencyState(GLINT_TRANSPARENCY)
                .setOutputState(ITEM_ENTITY_TARGET)
                .setTexturingState(GLINT_TEXTURING)
                .setOverlayState(OVERLAY)
                .createCompositeState(false);
        return RenderType.create("slashblade_glint", DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.TRIANGLES, 256, true, false, state);
    }

    public static RenderType getSlashBladeBlendColorWrite(ResourceLocation texture) {
        return slashBladeBlendColorWriteCache.computeIfAbsent(texture, t -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                    .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                    .setTextureState(new RenderStateShard.TextureStateShard(t, false, true))
                    .setTransparencyState(LIGHTNING_ADDITIVE_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false);

            return RenderType.create("slashblade_blend_write_color_" + t, DefaultVertexFormat.NEW_ENTITY,
                    VertexFormat.Mode.TRIANGLES, 256, false, true, state);
        });
    }

    protected static final RenderStateShard.TransparencyStateShard LIGHTNING_ADDITIVE_TRANSPARENCY =
            new RenderStateShard.TransparencyStateShard(
                    "lightning_additive_transparency", () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE,
                        GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            }, () -> {
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            });

    public static RenderType getSlashBladeBlendLuminous(ResourceLocation texture) {
        return slashBladeBlendLuminousCache.computeIfAbsent(texture, t -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                    .setOutputState(ITEM_ENTITY_TARGET)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setTextureState(new RenderStateShard.TextureStateShard(t, true, true))
                    .setTransparencyState(LIGHTNING_ADDITIVE_TRANSPARENCY)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false);

            return RenderType.create("slashblade_blend_luminous_" + t, DefaultVertexFormat.NEW_ENTITY,
                    VertexFormat.Mode.TRIANGLES, 256, false, true, state);
        });
    }

    public static RenderType getChargeEffect(ResourceLocation texture, float x, float y) {
        ChargeEffectKey key = new ChargeEffectKey(texture, x, y);
        return chargeEffectCache.computeIfAbsent(key, k -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
                    .setOutputState(ITEM_ENTITY_TARGET)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setTextureState(new RenderStateShard.TextureStateShard(k.texture, false, true))
                    .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(k.x, k.y))
                    .setTransparencyState(LIGHTNING_ADDITIVE_TRANSPARENCY)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .createCompositeState(false);

            return RenderType.create("slashblade_charge_effect_" + k.texture + "_" + k.x + "_" + k.y,
                    DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 256, false, true, state);
        });
    }

    public static RenderType getSlashBladeBlendLuminousDepthWrite(ResourceLocation texture) {
        return luminousDepthWriteCache.computeIfAbsent(texture, t -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                    .setOutputState(RenderStateShard.PARTICLES_TARGET)
                    .setTextureState(new RenderStateShard.TextureStateShard(t, true, true))
                    .setTransparencyState(LIGHTNING_ADDITIVE_TRANSPARENCY)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .createCompositeState(false);

            return RenderType.create("slashblade_blend_luminous_depth_write_" + t,
                    DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 256, false, true, state);
        });
    }

    protected static final RenderStateShard.TransparencyStateShard LIGHTNING_REVERSE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
            "lightning_reverse_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        RenderSystem.blendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT);
    }, () -> {
        RenderSystem.blendEquation(GL14.GL_FUNC_ADD);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static RenderType getSlashBladeBlendReverseLuminous(ResourceLocation texture) {
        return reverseLuminousCache.computeIfAbsent(texture, t -> {
            RenderType.CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                    .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                    .setTextureState(new RenderStateShard.TextureStateShard(t, true, true))
                    .setTransparencyState(LIGHTNING_REVERSE_TRANSPARENCY)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false);

            return RenderType.create("slashblade_blend_reverse_luminous_" + t,
                    DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 256, false, true, state);
        });
    }


    private record ChargeEffectKey(ResourceLocation texture, float x, float y) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ChargeEffectKey that = (ChargeEffectKey) o;
            return Float.compare(that.x, x) == 0 &&
                    Float.compare(that.y, y) == 0 &&
                    texture.equals(that.texture);
        }

    }
}
