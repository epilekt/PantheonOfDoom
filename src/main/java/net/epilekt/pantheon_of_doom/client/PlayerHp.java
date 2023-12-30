package net.epilekt.pantheon_of_doom.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH; //??

public class PlayerHp {

    //public static void modifyPlayerHealth(LivingEntity player, float newMaxHealth) {
    //    player.getAttribute(MAX_HEALTH).getModifiers().forEach(attributeModifier -> {
    //        if (attributeModifier.getName().equals("InitialHealth")) {
    //            player.getAttribute(MAX_HEALTH).removeModifier(attributeModifier);
    //        }
    //    });
    //    player.getAttribute(MAX_HEALTH).addPermanentModifier(new AttributeModifier("InitialHealth", 100f, AttributeModifier.Operation.ADDITION));
    //    player.setHealth(100f);
    //}

    public static final ResourceLocation BASE = new ResourceLocation(PantheonOfDoom.MOD_ID,
            "textures/gui/overlays/base_bar.png");
    public static final ResourceLocation HP_POINTS = new ResourceLocation(PantheonOfDoom.MOD_ID,
            "textures/gui/overlays/test.png");

    public static final IGuiOverlay BASE_HUD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2 - 91;
        int y = height + 9;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, BASE);



        GuiComponent.blit(poseStack, x, y - 54, 0, 0, 100, 16, 100, 16);



    });

    public static final IGuiOverlay HEALTH_HUD = ((gui, poseStack, partialTick, width, height) -> {
            int x = width / 2 - 91;
            int y = height + 9;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1f,1f,1f,1f);
            RenderSystem.setShaderTexture(0, HP_POINTS);

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            int player_health = (int) player.getAttribute(MAX_HEALTH).getBaseValue();
            int current_player_health = (int) player.getHealth();
            for (int i = 0; i < 10; i++) {
                int spriteX = 0;
                int spriteY = 3;
                int spriteWidth = 15;
                int spriteHeight = 12;
                float spacing = 0.1f;
                float totalWidth = (spriteWidth + spacing) * 10;
                if (current_player_health > i * (player_health / 10)) {
                    spriteX = 15;
                }
                float xPos = x + i * (spriteWidth + spacing) - totalWidth / 2;
                GuiComponent.blit(poseStack, (int) xPos, y - 54, spriteX, spriteY, spriteWidth, spriteHeight, 100, 16);
                //GuiComponent.blit(poseStack, x, y - 54, spriteX, spriteY, spriteWidth, spriteHeight, 100, 16);
            }
        }
            //for (int i = 0; i < 10; i++)     {
            //    GuiComponent.blit(poseStack, x + (i * 9), y - 54, 0, 3, 15, 12, 9, 16);
            //}
        });


}


