package net.epilekt.pantheon_of_doom.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStaminaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerRpgAttributes> PLAYER_STAMINA =
            CapabilityManager.get(new CapabilityToken<PlayerRpgAttributes>() {});
    private PlayerRpgAttributes stamina = null;
    private final LazyOptional<PlayerRpgAttributes> optionalS = LazyOptional.of(this::createPlRpAtStamina);
    private PlayerRpgAttributes createPlRpAtStamina() {
        if (this.stamina == null) {
            this.stamina = new PlayerRpgAttributes();
        }
        return this.stamina;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capabilityS, @Nullable Direction direction) {
        if (capabilityS == PLAYER_STAMINA) {
            return optionalS.cast();
        }
        return LazyOptional.empty();
    }
    @Override
    public CompoundTag serializeNBT() {
        return null;
    }
    @Override
    public void deserializeNBT(CompoundTag compoundTag) {

    }
}
