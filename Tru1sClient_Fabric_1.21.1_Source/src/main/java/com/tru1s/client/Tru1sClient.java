package com.tru1s.client;

import com.tru1s.client.ui.Tru1sGuiScreen;
import com.tru1s.client.ui.HudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tru1sClient implements ClientModInitializer {
    public static KeyBinding openGuiKey;
    
    // Cheat States
    public static boolean flightEnabled = false;
    public static boolean noFallEnabled = false;
    public static boolean waterWalkerEnabled = false;
    public static boolean fastBreakEnabled = false;
    public static boolean fullbrightEnabled = false;
    public static boolean blockEspEnabled = false;

    // Interactive ESP Block Selector
    public static Set<String> targetEspBlocks = new HashSet<>(Set.of(
            "minecraft:diamond_ore", 
            "minecraft:deepslate_diamond_ore", 
            "minecraft:ancient_debris"
    ));

    // Waypoints System
    public static class Waypoint {
        public String name;
        public int x, y, z;
        public Waypoint(String name, int x, int y, int z) {
            this.name = name; this.x = x; this.y = y; this.z = z;
        }
    }
    public static List<Waypoint> waypoints = new ArrayList<>();

    // Fake FPS Simulation
    public static int simulatedFps = 31823;
    private static int fpsTickTimer = 0;

    @Override
    public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tru1sclient.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.tru1sclient.main"
        ));

        HudOverlay.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new Tru1sGuiScreen());
                }
            }

            // Simulate realistic FPS fluctuations around 31,823
            fpsTickTimer++;
            if (fpsTickTimer > 15) {
                int delta = (int)(Math.random() * 41) - 20;
                simulatedFps = 31823 + delta;
                fpsTickTimer = 0;
            }
        });
    }
}
