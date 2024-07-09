package org.cneko.cem.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.cneko.cem.client.api.events.PlayerRenderEvents;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GeoPlayerRender extends GeoEntityRenderer<Player> {
    // TODO : 完善代码 & 添加API
    public GeoPlayerRender(EntityRendererProvider.Context renderManager) {
        super(renderManager,new GeoPlayerModel());
    }

    @Override
    public void render(Player entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        PlayerRenderEvents.RENDER_PLAYER.invoker().renderPlayer(entity, f, g, poseStack, multiBufferSource, i);
    }

    // TODO : 添加API
    public static class GeoPlayerModel extends GeoModel<Player> {
        @Override
        public ResourceLocation getModelResource(Player animatable) {
            return ResourceLocation.fromNamespaceAndPath("cem", "geo/entity/player.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(Player animatable) {
            return ResourceLocation.fromNamespaceAndPath("cem", "textures/entity/player.png");
        }

        @Override
        public ResourceLocation getAnimationResource(Player animatable) {
            return ResourceLocation.fromNamespaceAndPath("cem", "animations/entity/player.animation.json");
        }
    }
}
