package net.epilekt.pantheon_of_doom.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Caps {
    private Caps(){}
    public static final Capability<PlayerMovement> playerMovement = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<Stamina> stamina = CapabilityManager.get(new CapabilityToken<>(){});
}
