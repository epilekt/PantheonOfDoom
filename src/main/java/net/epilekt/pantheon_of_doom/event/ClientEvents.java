package net.epilekt.pantheon_of_doom.event;

import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.epilekt.pantheon_of_doom.client.PlayerHp;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = PantheonOfDoom.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RpgHUDs {
        @SubscribeEvent
        public static void renderGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("base", PlayerHp.BASE_BAR);
            event.registerAboveAll("health", PlayerHp.HEALTH_HUD);
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
