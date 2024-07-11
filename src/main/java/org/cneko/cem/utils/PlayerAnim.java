package org.cneko.cem.utils;

import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;

public interface PlayerAnim {
    default <E extends Player> PlayState Anim(AnimationState<Player> event) {
        return PlayState.STOP;
    }
}
