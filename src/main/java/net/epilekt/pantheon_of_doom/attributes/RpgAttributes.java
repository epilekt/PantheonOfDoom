package net.epilekt.pantheon_of_doom.attributes;

import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;


import static net.minecraft.data.BuiltinRegistries.register;
import static net.minecraftforge.registries.ForgeRegistries.ATTRIBUTES;

@Mod.EventBusSubscriber(modid = PantheonOfDoom.MOD_ID)
public class RpgAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, PantheonOfDoom.MOD_ID);
    //public static final Attribute MAX_STAMINA = new RangedAttribute("Stamina", 100.0D, 0.0D, 500.0D).setSyncable(true);
    public static final Attribute MAX_MANA = new RangedAttribute("Mana", 100.0D, 0.0D, 500.0D).setSyncable(true);
    public static final RegistryObject<Attribute> MAX_STAMINA = ATTRIBUTES.register("max_stamina", () -> new RangedAttribute("max_stamina", 0, 0, Double.MAX_VALUE).setSyncable(true));
}
