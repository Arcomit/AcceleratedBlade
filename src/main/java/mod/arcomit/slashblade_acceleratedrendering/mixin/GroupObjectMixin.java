package mod.arcomit.slashblade_acceleratedrendering.mixin;

import com.github.argon4w.acceleratedrendering.core.CoreFeature;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.IBufferGraph;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.builders.VertexConsumerExtension;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.renderers.IAcceleratedRenderer;
import com.github.argon4w.acceleratedrendering.core.meshes.IMesh;
import com.github.argon4w.acceleratedrendering.core.meshes.collectors.CulledMeshCollector;
import com.github.argon4w.acceleratedrendering.features.entities.AcceleratedEntityRenderingFeature;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.experimental.ExtensionMethod;
import mod.arcomit.slashblade_acceleratedrendering.utils.AcceleratedUtils;
import mods.flammpfeil.slashblade.client.renderer.model.obj.Face;
import mods.flammpfeil.slashblade.client.renderer.model.obj.GroupObject;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.FastColor;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Arcomit
 * @CreateTime: 2025-08-07 12:58
 * @Description: TODO:未完成
 */
@Pseudo
@ExtensionMethod(VertexConsumerExtension.class)
@Mixin          (value = GroupObject.class,remap = false)
public class GroupObjectMixin implements IAcceleratedRenderer<Void> {

    @Shadow public ArrayList<Face> faces;

    @Unique private final   Map<IBufferGraph, IMesh> meshes = new Object2ObjectOpenHashMap<>();

    @Inject(
            method		= "Lmods/flammpfeil/slashblade/client/renderer/model/obj/GroupObject;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;)V",
            at			= @At("HEAD"),
            cancellable	= true
    )
    public void compileFast(
            VertexConsumer vertexConsumer,
            CallbackInfo   ci
    ) {
        var extension = vertexConsumer.getAccelerated();
        if (AcceleratedEntityRenderingFeature.isEnabled()
                && AcceleratedEntityRenderingFeature.shouldUseAcceleratedPipeline()
                && (
                        CoreFeature.isRenderingLevel()
                                || (
                                        CoreFeature.isRenderingGui()
                                            && AcceleratedEntityRenderingFeature.shouldAccelerateInGui()
                        )
                                || CoreFeature.isRenderingHand()
                )
                && extension.isAccelerated()
                && AcceleratedUtils.isAccelerated()
        ) {
            ci.cancel();

            PoseStack poseStack = Face.matrix;
            if (poseStack == null) return;
            Color col = Face.col;

            int color = FastColor.ARGB32.color
                    (
                            col.getAlpha (),
                            col.getRed    (),
                            col.getGreen(),
                            col.getBlue   ()
                    );

            if (faces.size() > 0) {
                extension.doRender(this, null,
                        poseStack.last().pose(),
                        poseStack.last().normal(),
                        Face.lightmap,
                        OverlayTexture.NO_OVERLAY,
                        color
                );
            }
        }

    }

    @Unique
    @Override
    public void render(
            VertexConsumer	vertexConsumer,
            Void			context,
            Matrix4f		transform,
            Matrix3f		normal,
            int				light,
            int				overlay,
            int				color
    ) {

        var extension	= vertexConsumer.getAccelerated	();
        var mesh		= meshes		.get			(extension);

        extension.beginTransform(transform, normal);

        if (mesh != null) {
            mesh.write(
                    extension,
                    color,
                    light,
                    overlay
            );

            extension.endTransform();
            return;
        }

        var culledMeshCollector	= new CulledMeshCollector(extension);
        var meshBuilder			= extension.decorate	 (culledMeshCollector);

        Face.resetMatrix();
        Face.resetUvOperator();
        Face.resetAlphaOverride();
        Face.resetCol();
        for (var face : faces) {
            face.addFaceForRender(meshBuilder);
        }

        culledMeshCollector.flush();

        mesh = AcceleratedEntityRenderingFeature
                .getMeshType()
                .getBuilder	()
                .build		(culledMeshCollector);

        meshes	.put	(extension, mesh);
        mesh	.write	(
                extension,
                color,
                light,
                overlay
        );

        extension.endTransform();

    }
}
