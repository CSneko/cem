package org.cneko.cem.mixins;

import net.minecraft.world.entity.player.Player;
import org.cneko.cem.client.api.events.EventResults;
import org.cneko.cem.client.api.events.PlayerRenderEvents;
import org.cneko.cem.utils.GeoPlayerUtil;
import org.cneko.cem.utils.PlayerAnim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

@Mixin(Player.class)
public abstract class PlayerMixin implements GeoEntity, PlayerAnim {
    @Unique
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        Player player = (Player)(Object)this;
        GeoPlayerUtil.registerControllers(player,controllers);
        PlayerRenderEvents.REGISTER_CONTROLLERS.invoker().registerControllers(controllers);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public <E extends Player> PlayState Anim(final AnimationState<Player> event) {
        Player player = (Player)(Object)this;
        EventResults result = PlayerRenderEvents.PLAY_ANIM.invoker().playAnim(player ,event);
        if(result == EventResults.CANCEL){
            return PlayState.STOP;
        }else if(result == EventResults.SUCCESS){
            return PlayState.CONTINUE;
        }else if(result == EventResults.FAIL){
            return PlayState.STOP;
        }else if(result == EventResults.PASS){
            return PlayState.STOP;
        }

        return GeoPlayerUtil.playAnim(player, event);
    }
}
