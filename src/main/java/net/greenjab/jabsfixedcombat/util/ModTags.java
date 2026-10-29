package net.greenjab.jabsfixedcombat.util;

import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static final TagKey<EntityType<?>> VEHICLES = TagKey.create(Registries.ENTITY_TYPE, JabsFixedCombat.id("vehicles"));

    public static final TagKey<Block> GLASS_PANES = TagKey.create(Registries.BLOCK, JabsFixedCombat.id("glass_panes"));

    public static final TagKey<DamageType> NETHERITE_WITHSTANDS = TagKey.create(Registries.DAMAGE_TYPE, JabsFixedCombat.id("netherite_withstands"));


    public static final TagKey<Item> BREEZE_ROD = TagKey.create(Registries.ITEM, JabsFixedCombat.id("breeze_rod"));
    public static final TagKey<Item> COPPER_ARMOR = TagKey.create(Registries.ITEM, JabsFixedCombat.id("copper_armor"));
    public static final TagKey<Item> UNBREAKABLE = TagKey.create(Registries.ITEM, JabsFixedCombat.id("unbreakable"));
    public static final TagKey<Item> PARTIAL_KEEP_INVENTORY = TagKey.create(Registries.ITEM, JabsFixedCombat.id("partial_keep_inventory"));

}
