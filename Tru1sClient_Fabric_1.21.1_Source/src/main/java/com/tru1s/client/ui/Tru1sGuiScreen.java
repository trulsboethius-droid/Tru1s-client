package com.tru1s.client.ui;

import com.tru1s.client.Tru1sClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class Tru1sGuiScreen extends Screen {
    private float animationTicks = 0f;
    private TextFieldWidget blockInputWidget;
    private TextFieldWidget waypointInputWidget;

    public Tru1sGuiScreen() {
        super(Text.literal("Tru1s Client - Galaxy Dashboard"));
    }

    @Override
    protected void init() {
        super.init();
        int centerX = this.width / 2 - 100;
        int startY = 50;

        // Cheat Toggles
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Flight: " + (Tru1sClient.flightEnabled ? "ON" : "OFF")),
                b -> { Tru1sClient.flightEnabled = !Tru1sClient.flightEnabled; b.setMessage(Text.literal("Flight: " + (Tru1sClient.flightEnabled ? "ON" : "OFF"))); })
                .dimensions(centerX, startY, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("No Fall: " + (Tru1sClient.noFallEnabled ? "ON" : "OFF")),
                b -> { Tru1sClient.noFallEnabled = !Tru1sClient.noFallEnabled; b.setMessage(Text.literal("No Fall: " + (Tru1sClient.noFallEnabled ? "ON" : "OFF"))); })
                .dimensions(centerX, startY + 24, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Walk on Water: " + (Tru1sClient.waterWalkerEnabled ? "ON" : "OFF")),
                b -> { Tru1sClient.waterWalkerEnabled = !Tru1sClient.waterWalkerEnabled; b.setMessage(Text.literal("Walk on Water: " + (Tru1sClient.waterWalkerEnabled ? "ON" : "OFF"))); })
                .dimensions(centerX, startY + 48, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Fast Break: " + (Tru1sClient.fastBreakEnabled ? "ON" : "OFF")),
                b -> { Tru1sClient.fastBreakEnabled = !Tru1sClient.fastBreakEnabled; b.setMessage(Text.literal("Fast Break: " + (Tru1sClient.fastBreakEnabled ? "ON" : "OFF"))); })
                .dimensions(centerX, startY + 72, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Fullbright: " + (Tru1sClient.fullbrightEnabled ? "ON" : "OFF")),
                b -> { Tru1sClient.fullbrightEnabled = !Tru1sClient.fullbrightEnabled; b.setMessage(Text.literal("Fullbright: " + (Tru1sClient.fullbrightEnabled ? "ON" : "OFF"))); })
                .dimensions(centerX, startY + 96, 200, 20).build());

        // Block ESP Toggle & Custom Block Input
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Block ESP: " + (Tru1sClient.blockEspEnabled ? "ON" : "OFF")),
                b -> { Tru1sClient.blockEspEnabled = !Tru1sClient.blockEspEnabled; b.setMessage(Text.literal("Block ESP: " + (Tru1sClient.blockEspEnabled ? "ON" : "OFF"))); })
                .dimensions(centerX, startY + 120, 200, 20).build());

        blockInputWidget = new TextFieldWidget(this.textRenderer, centerX, startY + 144, 130, 18, Text.literal("Block ID"));
        blockInputWidget.setText("minecraft:gold_ore");
        this.addDrawableChild(blockInputWidget);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Add ESP"), b -> {
            String blockId = blockInputWidget.getText().trim();
            if (!blockId.isEmpty()) {
                Tru1sClient.targetEspBlocks.add(blockId);
            }
        }).dimensions(centerX + 134, startY + 143, 66, 20).build());

        // Waypoint Creator
        waypointInputWidget = new TextFieldWidget(this.textRenderer, centerX, startY + 172, 130, 18, Text.literal("Waypoint Name"));
        waypointInputWidget.setText("Base");
        this.addDrawableChild(waypointInputWidget);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Add Waypoint"), b -> {
            if (this.client != null && this.client.player != null) {
                String wpName = waypointInputWidget.getText().trim();
                int px = (int)this.client.player.getX();
                int py = (int)this.client.player.getY();
                int pz = (int)this.client.player.getZ();
                Tru1sClient.waypoints.add(new Tru1sClient.Waypoint(wpName, px, py, pz));
            }
        }).dimensions(centerX + 134, startY + 171, 66, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        animationTicks += delta;
        
        // Galaxy Deep Space Background Gradient & Orbiting Solar System Simulation
        context.fillGradient(0, 0, this.width, this.height, 0xFF050011, 0xFF14002B);
        
        int sunX = this.width / 2;
        int sunY = 25;
        context.fill(sunX - 10, sunY - 10, sunX + 10, sunY + 10, 0xFFFFB700);
        
        double angle = animationTicks * 0.015;
        int orbitX = sunX + (int)(Math.cos(angle) * 70);
        int orbitY = sunY + (int)(Math.sin(angle) * 20);
        context.fill(orbitX - 4, orbitY - 4, orbitX + 4, orbitY + 4, 0xFF2A9D8F);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFE0A9FF);

        // Render Waypoints List Preview
        context.drawTextWithShadow(this.textRenderer, "§eSaved Waypoints (" + Tru1sClient.waypoints.size() + "):", 20, this.height - 70, 0xFFFFFFFF);
        int wpY = this.height - 58;
        for (Tru1sClient.Waypoint wp : Tru1sClient.waypoints) {
            context.drawTextWithShadow(this.textRenderer, "- " + wp.name + " [" + wp.x + ", " + wp.y + ", " + wp.z + "]", 20, wpY, 0xFFAAAAAA);
            wpY += 10;
            if (wpY > this.height - 10) break;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
