package org.cneko.cem.client.api.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;

public class PlayerRenderEvents {
    /**
     * Called when the player is rendering their item in hand.
     */
    public static Event<RenderItemInHand> RENDER_ITEM_IN_HAND = EventFactory.createArrayBacked(RenderItemInHand.class,
            (listeners) -> (poseStack, multiBufferSource, i, f, g, humanoidArm) -> {
                for (RenderItemInHand listener : listeners) {
                    EventResults result = listener.renderItemInHand(poseStack,multiBufferSource,i,f,g,humanoidArm);
                    if (result != EventResults.PASS) {
                        return result;
                    }
                }
                return EventResults.PASS;
            });

    /**
     * Called when the player is registering their controllers.
     */
    public static Event<RegisterControllers> REGISTER_CONTROLLERS = EventFactory.createArrayBacked(RegisterControllers.class,
            (listeners) -> (controllers) -> {
                for (RegisterControllers listener : listeners) {
                    listener.registerControllers(controllers);
                }
            });
    /**
     * Called when the player is rendering.
     */
    public static Event<RenderPlayer> RENDER_PLAYER = EventFactory.createArrayBacked(RenderPlayer.class,
            (listeners) -> (entity, f, g, poseStack, multiBufferSource, i) -> {
                for (RenderPlayer listener : listeners) {
                    EventResults result = listener.renderPlayer(entity,f,g,poseStack,multiBufferSource,i);
                    if (result != EventResults.PASS) {
                        return result;
                    }
                }
                return EventResults.PASS;
            });

    /**
     * Called when the player is playing an animation.
     */
    public static Event<PlayAnim> PLAY_ANIM = EventFactory.createArrayBacked(PlayAnim.class,
            (listeners) -> (player,event) -> {
                for (PlayAnim listener : listeners) {
                    EventResults result = listener.playAnim(player,event);
                    if (result != EventResults.PASS) {
                        return result;
                    }
                }
                return EventResults.PASS;
            });

    public interface RenderItemInHand {
        EventResults renderItemInHand(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, float f, float g, HumanoidArm humanoidArm);
    }
    public interface RegisterControllers {
        void registerControllers(AnimatableManager.ControllerRegistrar controllers);
    }
    public interface RenderPlayer {
        EventResults renderPlayer(Player entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i);
    }
    public interface PlayAnim {
        EventResults playAnim(Player player,final AnimationState<Player> event);
    }
}
