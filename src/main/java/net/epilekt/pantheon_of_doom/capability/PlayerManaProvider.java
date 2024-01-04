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
public class PlayerManaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerRpgAttributes> PLAYER_MANA =
            CapabilityManager.get(new CapabilityToken<PlayerRpgAttributes>() {});
    private PlayerRpgAttributes mana = null;
    private final LazyOptional<PlayerRpgAttributes> optionalM = LazyOptional.of(this::createPlRpAtMana);
    private PlayerRpgAttributes createPlRpAtMana() {
        if (this.mana == null) {
            this.mana = new PlayerRpgAttributes();
        }
        return this.mana;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capabilityM, @Nullable Direction direction) {
        if (capabilityM == PLAYER_MANA) {
            return optionalM.cast();
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

