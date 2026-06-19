package net.greenjab.jabsfixedcombat.registry.registries;

import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.registry.item.EchoFruitItem;
import net.greenjab.jabsfixedcombat.registry.item.NewTotemItem;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.block.Block;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ItemRegistry {

    public static final Item BROKEN_TOTEM = register("broken_totem", Item::new, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final Item ECHO_TOTEM = register(
            "echo_totem", NewTotemItem::new, new Item.Properties().stacksTo(1)
                    .rarity(Rarity.UNCOMMON)
                    .component(DataComponents.DEATH_PROTECTION, DeathProtection.TOTEM_OF_UNDYING));
    public static final Item ECHO_FRUIT = register(
            "echo_fruit", EchoFruitItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).food(Foods.CHORUS_FRUIT));

    public static final Item SPEAR = register(
            "spear", new Item.Properties().rarity(Rarity.EPIC).spear(ToolMaterial.DIAMOND, 1.05F, 1.075F, 0.5F, 3.0F, 7.5F, 4.0F, 5.1F, 10.0F, 4.6F)
    );


    public static final Consumable GLOW_BERRIES_EFFECT = food().onConsume(new ApplyStatusEffectsConsumeEffect(
            new MobEffectInstance(MobEffects.GLOWING, 200, 0), 1F)).build();

    public static Consumable.Builder food() {
        return Consumable.builder().consumeSeconds(1.6F).animation(ItemUseAnimation.EAT).sound(SoundEvents.GENERIC_EAT).hasConsumeParticles(true);
    }

    /** This is used, IntelliJ just doesn't realise */
    public static final Holder<Potion> BLINDNESS = register("blindness", new Potion("blindness", new MobEffectInstance(MobEffects.BLINDNESS, 800)));
    public static final Holder<Potion> LEVITATION = register("levitation", new Potion("levitation", new MobEffectInstance(MobEffects.LEVITATION, 1200)));


    public static Item register(String id, Item.Properties settings) {
        return register(keyOf(id), Item::new, settings);
    }
    public static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties settings) {
        return register(keyOf(id), factory, settings);
    }
    private static ResourceKey<Item> keyOf(String id) {
        return ResourceKey.create(Registries.ITEM, JabsFixedCombat.id(id));
    }
    public static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
    public static Item register(Block block) {
        return register(block, BlockItem::new, new Item.Properties());
    }
    public static Item register(Block block, Item.Properties settings) {
        return register(block, BlockItem::new, settings);
    }
    public static Item register(Block block, BiFunction<Block, Item.Properties, Item> factory) {
        return register(block, factory, new Item.Properties());
    }
    public static Item register(Block block, BiFunction<Block, Item.Properties, Item> factory, Item.Properties settings) {
        return register(
                keyOf(block.builtInRegistryHolder().key()),
                itemSettings -> factory.apply(block, itemSettings),
                settings.useBlockDescriptionPrefix()
        );
    }
    private static ResourceKey<Item> keyOf(ResourceKey<Block> blockKey) {
        return ResourceKey.create(Registries.ITEM, blockKey.identifier());
    }

    private static Holder<Potion> register(String name, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, JabsFixedCombat.id(name), potion);
    }

    public static void registerItems() {
        System.out.println("register Items");
    }

}
