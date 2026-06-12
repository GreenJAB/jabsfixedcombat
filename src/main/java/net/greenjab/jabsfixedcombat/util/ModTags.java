package net.greenjab.jabsfixedcombat.util;

import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<EntityType<?>> VEHICLES = TagKey.create(Registries.ENTITY_TYPE, JabsFixedCombat.id("vehicles"));

    public static final TagKey<Item> COPPER_ARMOR = TagKey.create(Registries.ITEM, JabsFixedCombat.id("copper_armor"));
    public static final TagKey<Item> UNBREAKABLE = TagKey.create(Registries.ITEM, JabsFixedCombat.id("unbreakable"));
}
