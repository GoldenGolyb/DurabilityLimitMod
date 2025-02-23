package com.DurabilityLimit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class DurabilityLimitModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null && mc.player.getMainHandStack() != null) {
                var stack = mc.player.getMainHandStack();
                if (stack.isDamageable()) {
                    int maxDamage = stack.getMaxDamage();
                    int currentDamage = stack.getDamage();
                    if ((double) currentDamage / maxDamage >= 0.95) {
                        mc.player.sendMessage(Text.literal("Инструмент скоро сломается!").formatted(Formatting.RED), true);
                        mc.player.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                    }
                }
            }
        });
	}
}