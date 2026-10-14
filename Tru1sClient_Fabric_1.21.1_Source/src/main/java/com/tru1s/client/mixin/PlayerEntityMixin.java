package com.tru1s.client.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    // Rensa ut oanvända shadow-metoder som ändrats i Minecraft 1.21.1
}
