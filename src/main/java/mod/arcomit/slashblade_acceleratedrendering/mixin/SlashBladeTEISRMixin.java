package mod.arcomit.slashblade_acceleratedrendering.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.arcomit.slashblade_acceleratedrendering.utils.AcceleratedUtils;
import mods.flammpfeil.slashblade.client.renderer.SlashBladeTEISR;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = SlashBladeTEISR.class, remap = false)
public class SlashBladeTEISRMixin {

    @Inject(
            method = "renderBlade",
            at = @At("HEAD")
    )
    private void onRenderBladeStart(ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, CallbackInfoReturnable<Boolean> cir) {
        AcceleratedUtils.setAccelerated(true);
    }

    @Inject(
            method = "renderBlade",
            at = @At("RETURN")
    )
    private void onRenderBladeEnd(ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, CallbackInfoReturnable<Boolean> cir) {
        AcceleratedUtils.setAccelerated(false);
    }
}

