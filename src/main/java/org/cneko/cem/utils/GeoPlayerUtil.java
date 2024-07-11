package org.cneko.cem.utils;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.*;

import java.util.HashMap;
import java.util.Map;

public class GeoPlayerUtil {
    public static Map<String ,RawAnimation> animations = new HashMap<>();

    public static void registerControllers(Player player,AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(player,"idle", 20, player::Anim));
        controllers.add(new AnimationController<>(player,"walk", 20, player::Anim));
        controllers.add(new AnimationController<>(player,"run", 20, player::Anim));
        controllers.add(new AnimationController<>(player,"sleep", 20, player::Anim));
        //controllers.add(new AnimationController<>(player,"sleep_end", 20, player::Anim));
        //controllers.add(new AnimationController<>(player,"jump", 20, player::Anim));
        controllers.add(new AnimationController<>(player,"fall", 20, player::Anim));
        //controllers.add(new AnimationController<>(player,"attack", 20, player::Anim));
        //controllers.add(new AnimationController<>(player,"death", 20, player::Anim));
        //controllers.add(new AnimationController<>(player,"hurt", 20, player::Anim));
        controllers.add(new AnimationController<>(player,"swim", 20, player::Anim));
        controllers.add(new AnimationController<>(player,"swim_idle_on_water", 20, player::Anim));
        controllers.add(new AnimationController<>(player,"swim_idle_under_water", 20, player::Anim));
        // -------------------- 保存到 Map 中 ---------------------------------------------------
        animations.put("idle",PlayerAnimations.IDLE);
        animations.put("walk",PlayerAnimations.WALK);
        animations.put("run",PlayerAnimations.RUN);
        animations.put("sleep",PlayerAnimations.SLEEP);
        animations.put("sleep_end",PlayerAnimations.SLEEP_END);
        animations.put("jump",PlayerAnimations.JUMP);
        animations.put("fall",PlayerAnimations.FALL);
        animations.put("attack",PlayerAnimations.ATTACK);
        animations.put("death",PlayerAnimations.DEATH);
        animations.put("hurt",PlayerAnimations.HURT);
        animations.put("swim",PlayerAnimations.SWIM);
        animations.put("swim_idle_on_water",PlayerAnimations.SWIM_IDLE_ON_WATER);
        animations.put("swim_idle_under_water",PlayerAnimations.SWIM_IDLE_UNDER_WATER);
    }

    public static PlayState playAnim(Player player,final AnimationState<Player> event){
        if (event.isMoving()){
            // =============================== 玩家在移动 ==========================
            if (!player.isInLiquid() && !player.isInPowderSnow){
                // +++++++++++++++++++++++ 正常在陆地行走 ++++++++++++++++++++++++
                if (player.getDeltaMovement().length() < 0.1){
                    // ----------------- 慢行 -----------------
                    event.setAnimation(PlayerAnimations.WALK);
                }else {
                    // ----------------- 快行 -----------------
                    event.setAnimation(PlayerAnimations.RUN);
                }
                return PlayState.CONTINUE;
            } else if (player.isSwimming()) {
                // ++++++++++++++++++++++++ 游泳中 ++++++++++++++++++++++++++
                event.setAnimation(PlayerAnimations.SWIM);
                return PlayState.CONTINUE;
            }
        }
        if (player.isSleeping()){
            // -------------------------- 睡觉中 ------------------------------
            event.setAnimation(PlayerAnimations.SLEEP);
            return PlayState.CONTINUE;
        }
        if (player.isFallFlying()) {
            // -------------------------- 掉落中 --------------------------
            event.setAnimation(PlayerAnimations.FALL);
            return PlayState.CONTINUE;
        }
        // =================================== 静止 =====================================
        if (player.isInLiquid()) {
            // ++++++++++++++++++++++++++++++++ 在流体中 ++++++++++++++++++++++++++++++
            if (player.isEyeInFluid(FluidTags.WATER) || player.isEyeInFluid(FluidTags.LAVA)) {
                // ------------------------- 在水下 ---------------------------
                event.setAnimation(PlayerAnimations.SWIM_IDLE_UNDER_WATER);
                return PlayState.CONTINUE;
            }else {
                // -------------------------- 在水上 ---------------------------
                event.setAnimation(PlayerAnimations.SWIM_IDLE_ON_WATER);
            }
        }else {
            // ----------------------------- 在陆地中 -----------------------------
            event.setAnimation(PlayerAnimations.IDLE);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    public static class PlayerAnimations {
        public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
        public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
        public static final RawAnimation RUN = RawAnimation.begin().thenLoop("run");
        public static final RawAnimation SLEEP = RawAnimation.begin().then("sleep", Animation.LoopType.LOOP);
        public static final RawAnimation SLEEP_END = RawAnimation.begin().then("sleep_end", Animation.LoopType.PLAY_ONCE);
        public static final RawAnimation JUMP = RawAnimation.begin().then("jump", Animation.LoopType.PLAY_ONCE);
        public static final RawAnimation FALL = RawAnimation.begin().then("fall", Animation.LoopType.PLAY_ONCE);
        public static final RawAnimation ATTACK = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
        public static final RawAnimation DEATH = RawAnimation.begin().then("death", Animation.LoopType.PLAY_ONCE);
        public static final RawAnimation HURT = RawAnimation.begin().then("hurt", Animation.LoopType.PLAY_ONCE);
        public static final RawAnimation SWIM = RawAnimation.begin().thenLoop("swim");
        public static final RawAnimation SWIM_IDLE_ON_WATER = RawAnimation.begin().thenLoop("swim_idle_on_water");
        public static final RawAnimation SWIM_IDLE_UNDER_WATER = RawAnimation.begin().thenLoop("swim_idle_under_water");
    }
}
