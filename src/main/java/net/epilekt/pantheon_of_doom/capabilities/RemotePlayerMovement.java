package net.epilekt.pantheon_of_doom.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * PlayerMovement with no unique action at update. Used as simple data container.
 */
public class RemotePlayerMovement extends PlayerMovement{
    private boolean sprinting;

    public RemotePlayerMovement(Player player){
        super(player);
    }

    @Override public boolean isSprinting(){
        return sprinting;
    }
    @Override public void update(){}
    public void setSprinting(boolean sprinting){
        this.sprinting = sprinting;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return null;
    }
}
