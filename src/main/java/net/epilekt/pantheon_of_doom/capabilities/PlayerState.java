package net.epilekt.pantheon_of_doom.capabilities;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Objects;

public enum PlayerState{
    IDLE("idle", false, 20),
    RUNNING("running", false, -10),
    SWIMMING("swimming", false, -6),
    RIDING("riding", false, 20),
    ASCENDING("ascending", true, -3);

    public final String id;

    private final boolean sprinting;
    public final int defaultChange;

    private ForgeConfigSpec.IntValue change;

    PlayerState(String id, boolean sprinting, int defaultChange){
        this.id = id;
        this.sprinting = sprinting;
        this.defaultChange = defaultChange;
    }

    public boolean isSprinting(){
        return sprinting;
    }

    public int change(){
        return change.get();
    }

    public boolean isConsume(){
        return change()<0;
    }

    public void setConfig(ForgeConfigSpec.IntValue change){
        if(this.change!=null) throw new IllegalStateException("Multiple config entry");
        this.change = Objects.requireNonNull(change);
    }

    public static PlayerState of(int meta){
        PlayerState[] values = values();
        return values[meta%values.length];
    }
}