package net.epilekt.pantheon_of_doom.util;


import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.epilekt.pantheon_of_doom.attributes.RpgAttributes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.intellij.lang.annotations.Identifier;

public class StaminaHandler {
    private static final RangedAttribute MAX_STAMINA = (RangedAttribute) Registry.ATTRIBUTE.get(new ResourceLocation("minecraft", "Stamina"));

    public static void consumeStamina(Player player, double amount) {
        if (player.getAttributeValue(MAX_STAMINA) != 0.0D) {
            double currentStamina = player.getAttributeValue(MAX_STAMINA);
            double newStamina = Math.max(0, currentStamina - amount);
            player.getAttributeValue(MAX_STAMINA);
        }
    }

    public static void regenerateStamina(Player player, double amount) {
        if (player.getAttributeValue(MAX_STAMINA) != 0.0D) {
            double currentStamina = player.getAttributeValue(MAX_STAMINA);
            double newStamina = Math.min(100, currentStamina + amount);
            player.getAttributeValue(MAX_STAMINA);
        }
    }

    public static void handleSprinting(Player player) {
        if (player.isSprinting()) {
            consumeStamina(player, 1.0); // Example: Consuming 1 stamina point per tick while sprinting
        }
    }

    public static void handleJumping(Player player) {
        if (player.jumpFromGround(this.e)) {
            consumeStamina(player, 10.0); // Example: Consuming 10 stamina points on jumping
        }
    }

    public static void handleAttacking(Player player) {
        if (player.is) {
            consumeStamina(player, 5.0); // Example: Consuming 5 stamina points per attack
        }
    }
}
