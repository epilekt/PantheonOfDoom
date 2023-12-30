package net.epilekt.pantheon_of_doom.world.entity.ai;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class RpgAttributes { //В исходном коде игры все значени аттрибутов с ключевым словом final... но ведь они меняются в течении игры... Возможно неизмены именно значения RangedAttribute.

    public static final Attribute MANA = new RangedAttribute("generic.mana", 100.0D,0.0D, 1024.0D).setSyncable(true);
    public static final Attribute STAMINA = new RangedAttribute("generic.stamina", 100.0D, 0.0D, 512.0D).setSyncable(true);

}
