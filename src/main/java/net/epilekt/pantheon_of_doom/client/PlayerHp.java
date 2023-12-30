package net.epilekt.pantheon_of_doom.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.epilekt.pantheon_of_doom.config.forge.ClientConfigs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH; //??

public class PlayerHp {
    public static final ResourceLocation BASE = new ResourceLocation(PantheonOfDoom.MOD_ID,
            "textures/gui/overlays/basebar.png");
    public static final ResourceLocation HP_POINTS = new ResourceLocation(PantheonOfDoom.MOD_ID,
            "textures/gui/overlays/hp_sprite.png");

    public static final IGuiOverlay BASE_HUD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2 - 91;
        int y = height + 9;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, BASE);
        GuiComponent.blit(poseStack, x, y - 54, 0, 0, 100, 16, 100, 16);
        for (int i = 0; i < 10; i++)     {

        }
    });

    public static final IGuiOverlay HEALTH_HUD = ((gui, poseStack, partialTick, width, height) -> {
            int x = width / 2 - 91;
            int y = height + 9;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1f,1f,1f,1f);
            RenderSystem.setShaderTexture(0, HP_POINTS);

            for (int i = 0; i < 10; i++)     {
                GuiComponent.blit(poseStack, x + (i * 9), y - 54, 0, 3, 15, 12, 9, 16);
            }
        });


}


