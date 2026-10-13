package com.tru1s.client.ui;

import com.tru1s.client.Tru1sClient;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.font.TextRenderer;

public class HudOverlay {
    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
            if (client.options.hudHidden || client.player == null) return;

            TextRenderer tr = client.textRenderer;
            int x = 4;
            int y = 4;

            // Render Client Title & Fake FPS
            context.drawTextWithShadow(tr, "§dTru1s Client v1.0 §8| §bFPS: " + Tru1sClient.simulatedFps, x, y, 0xFFFFFFFF);
            y += 12;

            // Render Active Modules
            if (Tru1sClient.flightEnabled) { context.drawTextWithShadow(tr, "§7[§aFlight§7]", x, y, 0xFF55FF55); y += 10; }
            if (Tru1sClient.noFallEnabled) { context.drawTextWithShadow(tr, "§7[§aNoFall§7]", x, y, 0xFF55FF55); y += 10; }
            if (Tru1sClient.waterWalkerEnabled) { context.drawTextWithShadow(tr, "§7[§aWaterWalker§7]", x, y, 0xFF55FF55); y += 10; }
            if (Tru1sClient.fastBreakEnabled) { context.drawTextWithShadow(tr, "§7[§aFastBreak§7]", x, y, 0xFF55FF55); y += 10; }
            if (Tru1sClient.fullbrightEnabled) { context.drawTextWithShadow(tr, "§7[§aFullbright§7]", x, y, 0xFF55FF55); y += 10; }
            if (Tru1sClient.blockEspEnabled) { context.drawTextWithShadow(tr, "§7[§aBlockESP (" + Tru1sClient.targetEspBlocks.size() + ")§7]", x, y, 0xFF55FF55); y += 10; }
        });
    }
}
