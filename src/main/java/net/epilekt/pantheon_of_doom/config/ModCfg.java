package net.epilekt.pantheon_of_doom.config;

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.epilekt.pantheon_of_doom.capabilities.PlayerState;

import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.epilekt.pantheon_of_doom.PantheonOfDoom.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Bus.MOD)
public final class ModCfg{
    private ModCfg(){}

    private static ForgeConfigSpec serverSpec;

    private static BooleanValue runningConsumesStamina;
    private static IntValue maxStamina;
    private static IntValue startingStamina;
    private static IntValue maxStaminaVessels;

    private static BooleanValue enableStaminaVessels;

    private static BooleanValue debugPlayerMovement;
    private static BooleanValue traceMovementPacket;

    //private static final double DEFAULT_STAMINA_WHEEL_X = (427-100)/854.0;
    //private static final double DEFAULT_STAMINA_WHEEL_Y = (240-15)/480.0;
    //private static double staminaWheelX = DEFAULT_STAMINA_WHEEL_X;
    //private static double staminaWheelY = DEFAULT_STAMINA_WHEEL_Y;

    public static boolean runningConsumesStamina(){
        return get(serverSpec, runningConsumesStamina);
    }

    public static int maxStamina(){
        return get(serverSpec, maxStamina);
    }
    public static int startingStamina(){
        return Math.min(maxStamina(), get(serverSpec, startingStamina));
    }
    public static int maxStaminaVessels(){
        return get(serverSpec, maxStaminaVessels);
    }


    public static int maxStamina(int staminaVessels){
        int maxStaminaVessels = maxStaminaVessels();
        int startingStamina = startingStamina();
        if(maxStaminaVessels<=0) return startingStamina;
        if(maxStaminaVessels<=staminaVessels) maxStamina();
        return startingStamina+(int)((double)staminaVessels/maxStaminaVessels*(maxStamina()-startingStamina));
    }

    public static boolean debugPlayerMovement(){
        return debugPlayerMovement.get();
    }
    public static boolean traceMovementPacket(){
        return traceMovementPacket.get();
    }

    //public static double staminaWheelX(){
    //    return staminaWheelX;
    //}
    //public static void setStaminaWheelX(double staminaWheelX){
    //    ModCfg.staminaWheelX = filterBadValue(staminaWheelX, DEFAULT_STAMINA_WHEEL_X);
    //}
    //public static double staminaWheelY(){
    //    return staminaWheelY;
    //}
    //public static void setStaminaWheelY(double staminaWheelY){
    //    ModCfg.staminaWheelY = filterBadValue(staminaWheelY, DEFAULT_STAMINA_WHEEL_Y);
    //}

    //public static void setStaminaWheel(double x, double y){
    //    setStaminaWheelX(x);
    //    setStaminaWheelY(y);
    //}

    private static double filterBadValue(double d, double defaultValue){
        if(Double.isNaN(d)) return defaultValue;
        return Mth.clamp(d, 0, 1);
    }

    private static <T> T get(ForgeConfigSpec spec, ConfigValue<T> val){
        return spec.isLoaded() ? val.get() : val.getDefault();
    }

    public static void init(){
        ForgeConfigSpec.Builder server = new ForgeConfigSpec.Builder();

        maxStamina = server.comment("Maximum amount of stamina Player can get. Do note that one third of this value is equal to one stamina wheel.")
                .defineInRange("maxStamina", 3000, 0, Integer.MAX_VALUE);
        startingStamina = server.comment("""
						Amount of stamina Player starts with. Values higher than maxStamina doesn't work.
						If you want to make this value displayed as exactly one stamina wheel, you have to make this value one third of maxStamina.""")
                .defineInRange("startingStamina", 1000, 0, Integer.MAX_VALUE);
        maxStaminaVessels = server.comment("Stamina Vessels players need to obtain max out stamina. More vessels means lesser stamina increase per vessel.")
                .defineInRange("maxStaminaVessels", 10, 0, Integer.MAX_VALUE);
        server.pop();

        runningConsumesStamina = server.comment("Actions other than paragliding or ascending will consume stamina.").define("runningAndSwimmingConsumesStamina", false);

        server.push("consumptions");
        for(PlayerState state : PlayerState.values()){
            state.setConfig(server.defineInRange(state.id+"StaminaConsumption", state.defaultChange, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
        server.pop();
        server.pop();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverSpec = server.build());

        ForgeConfigSpec.Builder common = new ForgeConfigSpec.Builder();
        common.pop();

        common.push("debug");
        debugPlayerMovement = common.define("debugPlayerMovement", false);
        traceMovementPacket = common.define("traceMovementPacket", false);
        common.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, common.build());

        loadParagliderSettings();
    }

    private static void loadParagliderSettings(){
        try{
            Path file = FMLPaths.GAMEDIR.get().resolve("paragliderSettings.nbt");
            if(Files.exists(file)){
                try(DataInputStream dis = new DataInputStream(Files.newInputStream(file))){
                    CompoundTag nbt = NbtIo.read(dis);
                    CompoundTag staminaWheel = nbt.getCompound("staminaWheel");
                    //setStaminaWheelX(staminaWheel.getDouble("x"));
                    //setStaminaWheelY(staminaWheel.getDouble("y"));
                }
            }else{
                //staminaWheelX = DEFAULT_STAMINA_WHEEL_X;
                //staminaWheelY = DEFAULT_STAMINA_WHEEL_Y;
            }
        }catch(RuntimeException|IOException ex){
            //ParagliderMod.LOGGER.error("Error occurred while loading paraglider settings: ", ex);
            //staminaWheelX = DEFAULT_STAMINA_WHEEL_X;
            //staminaWheelY = DEFAULT_STAMINA_WHEEL_Y;
        }
    }

    //public static boolean saveParagliderSettings(){
    //    try{
    //        CompoundTag nbt = new CompoundTag();
    //        CompoundTag staminaWheel = new CompoundTag();
    //        staminaWheel.putDouble("x", staminaWheelX);
    //        staminaWheel.putDouble("y", staminaWheelY);
    //        nbt.put("staminaWheel", staminaWheel);
//
    //        Path file = FMLPaths.GAMEDIR.get().resolve("paragliderSettings.nbt");
    //        try(DataOutputStream dos = new DataOutputStream(Files.newOutputStream(file, StandardOpenOption.CREATE))){
    //            NbtIo.write(nbt, dos);
    //        }
    //        ParagliderMod.LOGGER.debug("Saved paraglider settings.");
    //        return true;
    //    }catch(RuntimeException|IOException ex){
    //        ParagliderMod.LOGGER.error("Error occurred while saving paraglider settings: ", ex);
    //        return false;
    //    }
    //}
//
    //@SubscribeEvent
    //public static void onLoad(ModConfigEvent.Loading event){
    //    ModConfig cfg = event.getConfig();
    //    if(cfg.getModId().equals(MODID)&&cfg.getType()==ModConfig.Type.SERVER){
    //        windSourcesParsed = Collections.unmodifiableMap(parseWindSources());
    //    }
    //}
//
    //@SubscribeEvent
    //public static void onReload(ModConfigEvent.Reloading event){
    //    ModConfig cfg = event.getConfig();
    //    if(cfg.getModId().equals(MODID)&&cfg.getType()==ModConfig.Type.SERVER){
    //        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    //        if(server!=null) server.execute(() -> windSourcesParsed = Collections.unmodifiableMap(parseWindSources()));
    //    }
    //}

    // ((?:[a-z0-9_.-]+:)?[a-z0-9_.-]+)(?:\s*#\s*([A-Za-z0-9_.-]+\s*=\s*[A-Za-z0-9_.-]+(?:\s*,\s*[A-Za-z0-9_.-]+\s*=\s*[A-Za-z0-9_.-]+)*))?
    private static final Pattern REGEX = Pattern.compile("^((?:[a-z0-9_.-]+:)?[a-z0-9_.-]+)(?:\\s*#\\s*([A-Za-z0-9_.-]+\\s*=\\s*[A-Za-z0-9_.-]+(?:\\s*,\\s*[A-Za-z0-9_.-]+\\s*=\\s*[A-Za-z0-9_.-]+)*))?$");

    //private static Map<Block, Predicate<BlockState>> parseWindSources(){
    //    IdentityHashMap<Block, Predicate<BlockState>> map = new IdentityHashMap<>();
    //    Matcher m = REGEX.matcher("");
    //    for(String s : windSources.get()){
    //        if(!m.reset(s).matches()){
    //            warnIgnoredWindSource(s, "input pattern is incorrect");
    //            continue;
    //        }
    //        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(m.group(1)));
    //        if(block==null||block==Blocks.AIR){
    //            warnIgnoredWindSource(s, "no such block exists");
    //            continue;
    //        }
    //        Predicate<BlockState> p = parsePredicate(s, m, block);
    //        if(p!=null) map.compute(block, (k, v) -> v==null ? p : v.or(p));
    //    }
    //    return map;
    //}

    //@Nullable
    //private static Predicate<BlockState> parsePredicate(String input, Matcher matcher, Block block){
    //    String blockState = matcher.group(2);
    //    if(blockState==null) return s -> true;
//
    //    Map<String, String> properties = new HashMap<>();
    //    for(String s : blockState.split(",")){
    //        int i = s.indexOf('=');
    //        String key = s.substring(0, i);
    //        if(properties.containsKey(key)){
    //            warnIgnoredWindSource(input, "same property '{}' was defined twice", key);
    //            return null;
    //        }else properties.put(key, s.substring(i+1));
    //    }
//
    //    Map<Property<?>, Object> parsedProperties = new IdentityHashMap<>();
    //    for(Entry<String, String> e : properties.entrySet()){
    //        String key = e.getKey();
    //        Property<?> property = block.getStateDefinition().getProperty(key);
    //        if(property==null){
    //            warnIgnoredWindSource(input, "property '{}' doesn't exist on that block", key);
    //            return null;
    //        }else if(parsedProperties.containsKey(property)){
    //            warnIgnoredWindSource(input, "same property '{}' was defined twice", key);
    //            return null;
    //        }
    //        Optional<?> o = property.getValue(e.getValue());
    //        if(o.isEmpty()){
    //            warnIgnoredWindSource(input, "property '{}' doesn't contain value '{}'", key, e.getValue());
    //            return null;
    //        }
    //        parsedProperties.put(property, o.get());
    //    }
    //    BlockStatePredicate m = BlockStatePredicate.forBlock(block);
    //    for(Entry<Property<?>, Object> e : parsedProperties.entrySet()){
    //        Object v = e.getValue();
    //        m.where(e.getKey(), o -> o!=null&&o.equals(v));
    //    }
    //    return m;
    //}
//
    //private static void warnIgnoredWindSource(String input, String cause, Object... args){
    //    ParagliderMod.LOGGER.warn("Wind source '"+input+"' was ignored, because "+cause, args);
    //}
}
