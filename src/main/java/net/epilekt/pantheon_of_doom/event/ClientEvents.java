package net.epilekt.pantheon_of_doom.event;

import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.epilekt.pantheon_of_doom.client.PlayerHp;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = PantheonOfDoom.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents1 {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("base", PlayerHp.BASE_HUD);
        }
    }
    @Mod.EventBusSubscriber(modid = PantheonOfDoom.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents2 {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("health", PlayerHp.HEALTH_HUD);
        }
    }

}
