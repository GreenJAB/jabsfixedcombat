package net.greenjab.jabsfixedcombat.registry.registries;

import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootTableRegistry {

    public static final ResourceKey<LootTable> GOAT_MUTTON = registerLoot_Table("entity/goat_mutton");
    public static final ResourceKey<LootTable> CHARGED_CREEPER_ZOMBIE_TABLE = registerLoot_Table("gameplay/charged_creeper/zombie");
    public static final ResourceKey<LootTable> CHARGED_CREEPER_SKELETON_TABLE = registerLoot_Table("gameplay/charged_creeper/skeleton");
    private static ResourceKey<LootTable> registerLoot_Table(String id) {
        return registerLootTable(ResourceKey.create(Registries.LOOT_TABLE, JabsFixedCombat.id(id)));
    }
    private static ResourceKey<LootTable> registerLootTable(ResourceKey<LootTable> key) {
        if (BuiltInLootTables.LOCATIONS.add(key)) {
            return key;
        } else {
            throw new IllegalArgumentException(key.identifier() + " is already a registered built-in loot table");
        }
    }

    public static void registerLootTable() {
        System.out.println("register LootTables");
    }

}
