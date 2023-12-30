package net.epilekt.pantheon_of_doom.event;

import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.epilekt.pantheon_of_doom.client.PlayerHp;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//import static net.epilekt.pantheon_of_doom.client.PlayerHp.modifyPlayerHealth;

public class ClientEvents {


    //@SubscribeEvent
    //public static void onPlayerHealthChanged(LivingHurtEvent event) {
    //    LivingEntity player = event.getEntity();
    //    if (player instanceof Player) {
    //        modifyPlayerHealth(player, 100f);
    //    }
    //}
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
