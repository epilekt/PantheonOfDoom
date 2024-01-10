package net.epilekt.pantheon_of_doom.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static net.epilekt.pantheon_of_doom.attributes.RpgAttributes.STAMINA;
import static net.epilekt.pantheon_of_doom.attributes.RpgAttributes.registerAttribute;

public class PlayerStamina {
    public static final ResourceLocation BASE = new ResourceLocation(PantheonOfDoom.MOD_ID,
            "textures/gui/overlays/back_bar.png");
    public static final ResourceLocation STAMINA_POINTS = new ResourceLocation(PantheonOfDoom.MOD_ID,
            "textures/gui/overlays/stamina_point.png"); //need better texture, maybe darker
    public static final IGuiOverlay BASE_BAR = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2 - 242;
        int y = height + 28;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, BASE);
        //----------------------screen x and y coordinates----instant pixel x,y------------sprite width--------------image width----
        GuiComponent.blit(poseStack, x, y - 54, 0, 0, 108, 16, 108, 16);
    });
    public static final IGuiOverlay STAMINA_HUD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2 - 238;
        int y = height + 32;

        Player player = Minecraft.getInstance().player;
        int player_stamina = (int) player.getAttributeBaseValue(STAMINA.get());
        //int player_stamina = (int) player.getAttribute(STAMINA).getBaseValue();
        int current_player_stamina = (int) player.getHealth();
        String staminaString = current_player_stamina + "/" + player_stamina;
        int TEXT_COLOR = ChatFormatting.WHITE.getColor();
        gui.getFont().draw(poseStack, staminaString, x, y, TEXT_COLOR);//just fuckin text x/20 hp, suka blyat, why doesnt work

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, STAMINA_POINTS);
        if (player != null) {
            int fullSegments = current_player_stamina;
            for (int i = 0; i < 100; i++) { //in future add 100 sprites? for 100 hp and more and smoothest animation
                int spriteX = 0;
                int spriteY = 0;
                int spriteWidth = 5;
                int spriteHeight = 8;
                float xPos = x + i * spriteWidth;
                if (i < fullSegments) { //full sprite
                    GuiComponent.blit(poseStack, (int) xPos, y - 74, spriteX, spriteY, spriteWidth, spriteHeight, 10, 8);
                } else { //empty sprite
                    GuiComponent.blit(poseStack, (int) xPos, y, spriteX + spriteWidth, spriteY, spriteWidth, spriteHeight, 10, 8);
                }
            }
        }
    });
}
