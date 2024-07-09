package org.cneko.cem.mixins;

import net.minecraft.world.entity.player.Player;
import org.cneko.cem.client.api.events.EventResults;
import org.cneko.cem.client.api.events.PlayerRenderEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

@Mixin(Player.class)
public class PlayerMixin implements GeoEntity {
    @Unique
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        PlayerRenderEvents.REGISTER_CONTROLLERS.invoker().registerControllers(controllers);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    protected <E extends Player> PlayState Anim(final AnimationState<Player> event) {
        EventResults result = PlayerRenderEvents.PLAY_ANIM.invoker().playAnim(event);
        if(result == EventResults.CANCEL){
            return PlayState.STOP;
        }else if(result == EventResults.SUCCESS){
            return PlayState.CONTINUE;
        }else if(result == EventResults.FAIL){
            return PlayState.STOP;
        }else if(result == EventResults.PASS){
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }
}
