package net.epilekt.pantheon_of_doom.event;

import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.epilekt.pantheon_of_doom.gui.overlays.PlayerHp;
import net.epilekt.pantheon_of_doom.gui.overlays.PlayerStamina;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.epilekt.pantheon_of_doom.util.StaminaHandler.*;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = PantheonOfDoom.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RpgHUDs {
        @SubscribeEvent
        public static void renderGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("base", PlayerHp.BASE_BAR);
            event.registerAboveAll("front", PlayerHp.FRONT_BAR);
            event.registerAboveAll("health", PlayerHp.HEALTH_HUD);
            event.registerAboveAll("stamina", PlayerStamina.STAMINA_HUD);
        }

        @SubscribeEvent
        public static void onStaminaUpdate(TickEvent.PlayerTickEvent event) {

            Player player = (Player) event.player;
            boolean b = true;

            if (getStamina(player) <= 0.0F) {
                player.setSprinting(false);
                b = false;
                if (player.tickCount % 120 == 0)
                {
                    b = true;
                }
            }

            if(player.isSprinting()) {
                decreaseStamina(player, 0.25F);
            }
            else if (!player.isSprinting() && b) regenStamina(player, 0.25F);

        }

    }
    @SubscribeEvent
    public static void renderVanillaHUDs(RenderGuiOverlayEvent.Pre event) {
        if (VanillaGuiOverlay.PLAYER_HEALTH.type() == event.getOverlay()) {
            event.setCanceled(true);
        }
        if (VanillaGuiOverlay.ARMOR_LEVEL.type() == event.getOverlay()) {
            event.setCanceled(true);
        }
    }
}
