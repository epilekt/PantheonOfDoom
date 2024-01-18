package net.epilekt.pantheon_of_doom.capabilities;

import net.epilekt.pantheon_of_doom.attributes.RpgAttributes;
import net.epilekt.pantheon_of_doom.config.ModCfg;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public abstract class PlayerMovement implements Stamina, ICapabilityProvider{
    public static final int RECOVERY_DELAY = 10;

    public final Player player;
    private PlayerState state = PlayerState.IDLE;

    private int stamina = ModCfg.startingStamina();
    private boolean depleted;
    private int recoveryDelay;
    private int staminaVessels;
    private int heartContainers;

    public PlayerMovement(Player player){
        this.player = Objects.requireNonNull(player);
    }

    public PlayerState getState(){
        return this.state;
    }
    public void setState(PlayerState state){
        this.state = Objects.requireNonNull(state);
    }

    @Override public int getStamina(){
        return stamina;
    }
    public void setStamina(int stamina){
        this.stamina = stamina;
    }
    @Override public boolean isDepleted(){
        return depleted;
    }
    @Override public void setDepleted(boolean depleted){
        this.depleted = depleted;
    }

    @Override public int giveStamina(int amount, boolean simulate){
        if(amount<=0) return 0;
        int maxStamina = getMaxStamina();
        int staminaToGive = Math.min(amount, maxStamina-stamina);
        if(staminaToGive<=0) return 0;
        if(!simulate) stamina += staminaToGive;
        return staminaToGive;
    }

    @Override public int takeStamina(int amount, boolean simulate, boolean ignoreDepletion){
        if(amount<=0||(isDepleted()&&!ignoreDepletion)) return 0;
        int staminaToTake = Math.min(amount, stamina);
        if(staminaToTake<=0) return 0;
        if(!simulate) stamina -= staminaToTake;
        return staminaToTake;
    }

    public int getRecoveryDelay(){
        return recoveryDelay;
    }
    public void setRecoveryDelay(int recoveryDelay){
        this.recoveryDelay = recoveryDelay;
    }

    public int getStaminaVessels(){
        return staminaVessels;
    }
    public void setStaminaVessels(int staminaVessels){
        this.staminaVessels = Math.max(0, staminaVessels);
    }
    public int getHeartContainers(){
        return heartContainers;
    }
    public void setHeartContainers(int heartContainers){
        this.heartContainers = Math.max(0, heartContainers);
    }

    @Override public int getMaxStamina(){
        AttributeInstance attribute = player.getAttribute(RpgAttributes.MAX_STAMINA.get());
        if(attribute!=null) return (int)attribute.getValue();
        //ParagliderMod.LOGGER.error("Player {} doesn't have max stamina attribute", player);
        return ModCfg.maxStamina(staminaVessels);
    }

    public boolean canUseParaglider(){
        return player.isCreative()||!depleted;
    }

    public abstract boolean isSprinting();

    public abstract void update();

    protected void updateStamina(){
        if(state.isConsume()){
            recoveryDelay = RECOVERY_DELAY;
            if(!depleted&&ModCfg.runningConsumesStamina()) {
                stamina = Math.max(0, stamina + state.change());
            }
        }else if(recoveryDelay>0) recoveryDelay--;
        else if(state.change()>0) stamina = Math.min(getMaxStamina(), stamina+state.change());
    }
}