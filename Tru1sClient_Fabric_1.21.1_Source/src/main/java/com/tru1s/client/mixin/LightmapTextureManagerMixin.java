package com.tru1s.client.mixin;

import com.tru1s.client.Tru1sClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @ModifyVariable(method = "update", at = @At(value = "STORE"), ordinal = 0)
    private float modifyBrightness(float brightness) {
        if (Tru1sClient.fullbrightEnabled) {
            return 15.0f;
        }
        return brightness;
    }
}
