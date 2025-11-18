package mod.arcomit.slashblade_acceleratedrendering.mixin;

import mod.arcomit.slashblade_acceleratedrendering.utils.AcceleratedUtils;
import mods.flammpfeil.slashblade.client.renderer.layers.LayerMainBlade;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = LayerMainBlade.class, remap = false)
public class LayerMainBladeMixin {

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD")
    )
    private void onRenderStart(CallbackInfo ci) {
        AcceleratedUtils.setAccelerated(true);
    }

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("RETURN")
    )
    private void onRenderEnd(CallbackInfo ci) {
        AcceleratedUtils.setAccelerated(false);
    }
}


