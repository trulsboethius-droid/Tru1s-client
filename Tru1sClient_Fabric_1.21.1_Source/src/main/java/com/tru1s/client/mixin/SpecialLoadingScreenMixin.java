package com.tru1s.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public class SpecialLoadingScreenMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        context.fillGradient(0, 0, width, height, 0xFF02000A, 0xFF10001F);
        context.drawCenteredTextWithShadow(net.minecraft.client.MinecraftClient.getInstance().textRenderer, 
                "§d§lLoading Tru1s Client Galaxy Universe...", width / 2, height / 2 - 20, 0xFFFFFFFF);
    }
}
