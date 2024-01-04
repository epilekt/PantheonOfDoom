package net.epilekt.pantheon_of_doom.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerRpgAttributes {

    private int stamina = 100; private int mana = 100;
    private int maxStamina = 1024; private int maxMana = 1024;
    private int cdStaminaReduction = 5; private int cdManaReduction = 5; //cooldowns
    private final int MIN_STAMINA = 0; private final int MIN_MANA = 0;
    private int enduranceStamina = 0; private int enduranceMana = 0;
    //endurance - выносливость, судя по всему работает как "насыщение" в аттрибуте голода,
    //то есть очки насыщения, очки для каждого уровня "голода"... для Маны по-моему
    //лишний параметр, ну посмотрим
    private int cooldown = 0; private final int MIN_COOLDOWN = 0;

    public int getStamina() {
        return stamina;
    }
    public int getMana() {
        return mana;
    }
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public int getMaxStamina() {
        return maxStamina;
    }
    public int getMaxMana() {
        return maxMana;
    }
    public void setMaxStamina(int stamina) {
        this.maxStamina = stamina;
        if (getStamina() > stamina) {
            setStamina(stamina);
        }
    }
    public void setMaxMana(int mana) {
        this.maxMana = mana;
        if (getStamina() > mana) {
            setStamina(mana);
        }
    }
    public int getStaminaRegen() {
        return  this.cdStaminaReduction;
    }
    public int getManaRegen() {
        return  this.cdManaReduction;
    }
    public void setStaminaRegen(int ticks) {
        this.cdStaminaReduction = ticks;
    }
    public void setManaRegen(int ticks) {
        this.cdManaReduction = ticks;
    }
    public void addStamina(int stamina) {
        this.stamina = Math.min(this.stamina + stamina, maxStamina);
    }
    public void addMana(int mana) {
        this.mana = Math.min(this.mana + mana, maxMana);
    }
    public void subStamina(int stamina) {
        this.stamina = Math.max(this.stamina - stamina, MIN_STAMINA);
    }
    public void subMana(int mana) {
        this.mana = Math.max(this.mana - mana, MIN_MANA);
    }
    public void copyFrom(PlayerRpgAttributes source) {
        this.stamina = source.stamina; this.mana = source.mana;
        this.enduranceStamina = source.enduranceStamina; this.enduranceMana = source.enduranceMana;
        this.cooldown = source.cooldown;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("stamina", this.stamina); nbt.putInt("mana", this.mana);
        nbt.putInt("max_stamina", this.maxStamina); nbt.putInt("max_mana", this.maxMana);
        nbt.putInt("cd_stamina_reduction", this.cdStaminaReduction); nbt.putInt("cd_mana_reduction", this.cdManaReduction);
        nbt.putInt("endurance_stamina", this.enduranceStamina); nbt.putInt("endurance_mana", this.enduranceMana);
        nbt.putInt("cooldown", this.cooldown);
    }
    public void loadNBTData(CompoundTag nbt) {
        this.stamina = nbt.getInt("stamina"); this.mana = nbt.getInt("mana");
        this.maxStamina = nbt.getInt("max_stamina"); this.maxMana = nbt.getInt("max_mana");
        this.cdStaminaReduction = nbt.getInt("cd_stamina_reduction"); this.cdManaReduction = nbt.getInt("cd_mana_reduction");
        this.enduranceStamina = nbt.getInt("endurance_stamina"); this.enduranceMana = nbt.getInt("endurance_mana");
        this.cooldown = nbt.getInt("cooldown");
    }
    public int getCooldown() {
        return cooldown;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public void subCooldown(int ticks) {
        this.cooldown = Math.max(this.cooldown - ticks, MIN_COOLDOWN);
    }
    public int getEnduranceStamina() {
        return enduranceStamina;
    }
    public int getEnduranceMana() {
        return enduranceMana;
    }
    public void setEnduranceStamina(int enduranceStamina) {
        this.enduranceStamina = enduranceStamina;
    }
    public void setEnduranceMana(int enduranceMana) {
        this.enduranceMana = enduranceMana;
    }
    public void addEnduranceStamina(int stamina) {
        this.enduranceStamina = this.enduranceStamina + stamina;
    }
    public void addEnduranceMana(int mana) {
        this.enduranceMana = this.enduranceMana + mana;
    }

}
