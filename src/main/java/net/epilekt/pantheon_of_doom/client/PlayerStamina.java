package net.epilekt.pantheon_of_doom.client;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.checkerframework.checker.signature.qual.Identifier;

public class PlayerStamina {

    static String PATH_STAMINA = "net.epilekt.pantheon_of_doom.world.entity.ai.RpgAttributes";
    private static final RangedAttribute PLAYER_STAMINA = (RangedAttribute) Registry.ATTRIBUTE.get(new ResourceLocation(PATH_STAMINA, "generic.stamina"));

}
