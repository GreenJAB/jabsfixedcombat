package net.greenjab.jabsfixedcombat.registry.registries;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jspecify.annotations.NonNull;

public class LootTableAdditions {

    public static void registerLootTableAdds() {
        System.out.println("register LootTableAdds");

        LootTableEvents.MODIFY.register((key, tableBuilder, _, holder) -> {
            HolderLookup.RegistryLookup<Enchantment> enchantments = holder.lookupOrThrow(Registries.ENCHANTMENT);
            if (key==BuiltInLootTables.ANCIENT_CITY) {
                tableBuilder.pool(LootPool.lootPool().add(LootItem.lootTableItem(Items.AIR).setWeight(5))
                        .add(enchantedArmor(enchantments, Items.DIAMOND_HELMET))
                        .add(enchantedArmor(enchantments, Items.DIAMOND_CHESTPLATE))
                        .add(enchantedArmor(enchantments, Items.DIAMOND_BOOTS))
                        .build());
                tableBuilder.pool(LootPool.lootPool().add(LootItem.lootTableItem(Items.ECHO_SHARD)).build());
            } else if (key== EntityType.GOAT.getDefaultLootTable().get()) {
                LootPool.Builder poolBuilder = LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableRegistry.GOAT_MUTTON));
                tableBuilder.pool(poolBuilder.build());
            } else if (key==BuiltInLootTables.CHARGED_CREEPER) {
                tableBuilder.pool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableRegistry.CHARGED_CREEPER_ZOMBIE_TABLE).when(LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(holder.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.DROWNED))))).build());
                tableBuilder.pool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableRegistry.CHARGED_CREEPER_ZOMBIE_TABLE).when(LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(holder.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.HUSK))))).build());

                tableBuilder.pool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableRegistry.CHARGED_CREEPER_SKELETON_TABLE).when(LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(holder.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.BOGGED))))).build());
                tableBuilder.pool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableRegistry.CHARGED_CREEPER_SKELETON_TABLE).when(LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(holder.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.STRAY))))).build());
                tableBuilder.pool(LootPool.lootPool().add(NestedLootTable.lootTableReference(LootTableRegistry.CHARGED_CREEPER_SKELETON_TABLE).when(LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(holder.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.PARCHED))))).build());
            }
	  });
    }

    private static LootPoolSingletonContainer.@NonNull Builder<?> enchantedArmor(HolderLookup.RegistryLookup<Enchantment> enchantments, Item armor) {
        return LootItem.lootTableItem(armor).setWeight(1)
                .apply(new EnchantWithLevelsFunction.Builder(ConstantValue.exactly(30))
                        .withOptions(enchantments.get(EnchantmentTags.ON_RANDOM_LOOT).map(named -> named)));
    }
}
