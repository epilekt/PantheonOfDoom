package net.epilekt.pantheon_of_doom.attributes; //https://github.com/ElenaiDev/Feathers/blob/1.19.2/src/main/java/com/elenai/feathers/attributes/FeathersAttributes.java

import net.epilekt.pantheon_of_doom.PantheonOfDoom;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = PantheonOfDoom.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RpgAttributes {

    public static final HashMap<RegistryObject<Attribute>, UUID> UUIDS = new HashMap<>();
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, PantheonOfDoom.MOD_ID);

    public static final RegistryObject<Attribute> STAMINA = registerAttribute("pantheon_of_doom.stamina", (id) ->
            new RangedAttribute(id, 100.0D, 0.0D, 1024.0D).setSyncable(true), "f47ac10b-58cc-4372-a567-0e02b2c3d479");
    public static final RegistryObject<Attribute> STAMINA_REGEN = registerAttribute("pantheon_of_doom.stamina_regen", (id) ->
            new RangedAttribute(id, 1.0D, 0.0D, 1024.0D).setSyncable(true), "38400000-8cf0-11bd-b23e-10b96e4ef00d");
    public static final RegistryObject<Attribute> MANA = registerAttribute("pantheon_of_doom.mana", (id) ->
            new RangedAttribute(id, 100.0D, 0.0D, 1024.0D).setSyncable(true), "550e8400-e29b-41d4-a716-446655440000");
    public static final RegistryObject<Attribute> MANA_REGEN = registerAttribute("pantheon_of_doom.mana_regen", (id) ->
            new RangedAttribute(id, 1.0D, 0.0D, 1024.0D).setSyncable(true), "c1a0d783-ccba-4cb6-9028-7bad37af68e6");
    public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute, String uuid) {
        return registerAttribute(name, attribute, String.valueOf(UUID.fromString(uuid)));
    }
    public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute, UUID uuid) {
        RegistryObject<Attribute> registryObject = ATTRIBUTES.register(name, () -> attribute.apply(name));
        UUIDS.put(registryObject, uuid);
        return registryObject;
    }
    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> e : event.getTypes())
            if (e == EntityType.PLAYER) for (RegistryObject<Attribute> v : ATTRIBUTES.getEntries())
                event.add(e, v.get());
    }
}
