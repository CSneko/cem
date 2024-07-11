package org.cneko.cem.utils;

import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;

public interface PlayerAnim {
    PlayState playAnim(Player player, final AnimationState<Player> event);
}
