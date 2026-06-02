package com.teammangomilk.corpseextended.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.corpse.entities.CorpseEntity;
import de.maxhenkel.corpse.entities.CorpseRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScaledCorpseRenderer extends CorpseRenderer
{
    public ScaledCorpseRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(CorpseEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLight)
    {
        float scale = ClientScaleCache.get(entity.getId());
        stack.pushPose();
        stack.scale(scale, scale, scale);
        super.render(entity, entityYaw, partialTicks, stack, buffer, packedLight);
        stack.popPose();
    }
}
