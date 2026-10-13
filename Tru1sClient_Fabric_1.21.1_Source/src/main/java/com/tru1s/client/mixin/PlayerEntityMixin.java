package com.tru1s.client.mixin;

import com.tru1s.client.Tru1sClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract boolean isSneaking();

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (Tru1sClient.flightEnabled) {
            player.getAbilities().allowFlying = true;
            player.getAbilities().flying = true;
        } else if (!player.getAbilities().creativeMode) {
            player.getAbilities().allowFlying = false;
            player.getAbilities().flying = false;
        }

        if (Tru1sClient.waterWalkerEnabled && player.isTouchingWater() && !this.isSneaking()) {
            player.setVelocity(player.getVelocity().x, 0.08, player.getVelocity().z);
            player.setOnGround(true);
        }
    }
}
