package mod.arcomit.slashblade_acceleratedrendering.mixin;

import mod.arcomit.slashblade_acceleratedrendering.utils.AcceleratedUtils;
import mods.flammpfeil.slashblade.client.renderer.entity.BladeItemEntityRenderer;
import mods.flammpfeil.slashblade.client.renderer.layers.LayerMainBlade;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = BladeItemEntityRenderer.class, remap = false)
public class BladeItemEntityRendererMixin {

    @Inject(
            method = "Lmods/flammpfeil/slashblade/client/renderer/entity/BladeItemEntityRenderer;renderBlade(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD")
    )
    private void onRenderBladeStart(CallbackInfo ci) {
        AcceleratedUtils.setAccelerated(true);
    }

    @Inject(
            method = "Lmods/flammpfeil/slashblade/client/renderer/entity/BladeItemEntityRenderer;renderBlade(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("RETURN")
    )
    private void onRenderBladeEnd(CallbackInfo ci) {
        AcceleratedUtils.setAccelerated(false);
    }
}
