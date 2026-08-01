package net.greenjab.jabsfixedcombat.registry.registries;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.*;

import java.util.function.ToIntFunction;

public class GameRuleRegistry {
    public static final GameRuleCategory JABSFIXEDCOMBAT = GameRuleCategory.register(JabsFixedCombat.id("aac_jabsfixedcombat"));

    public static GameRule<Boolean> REQUIRE_TOTEM_USE;
    public static GameRule<Boolean> RAID_REPLACE_EVOKERS_WITH_ILLUSIONERS;

    public static GameRule<Boolean> NERF_VANILLA_SPEARS;
    public static GameRule<Boolean> SPEARS_ONLY_HORIZONTAL;

    public static GameRule<Integer> STAMINA_DRAIN_SPEED;
    public static GameRule<Boolean> EAT_DURATION_PROPORTIONAL_TO_FOOD;
    public static GameRule<Boolean> EAT_HIT_CANCELLING;

    public static GameRule<Boolean> RESPAWN_WITH_LESS_HEALTH;
    public static GameRule<Boolean> PARTIAL_KEEP_INVENTORY;
    public static GameRule<Integer> ITEM_DEATH_DESPAWN_TIME;

    public static GameRule<Boolean> STRONGER_MOBS;
    public static GameRule<Boolean> MOBS_LEAVE_VEHICLES_WHEN_ATTACKED;

    public static GameRule<Boolean> BETTER_WITHER_FIGHT;
    public static GameRule<Boolean> BETTER_DRAGON_FIGHT;
    public static GameRule<Boolean> DRAGON_WORLD_BORDER_BEFORE_KILL;

    public static void registerGameRules() {
        System.out.println("register GameRules");
        REQUIRE_TOTEM_USE = registerBoolean("require_totem_use", false);
        RAID_REPLACE_EVOKERS_WITH_ILLUSIONERS = registerBoolean("raid_replace_evokers_with_illusioners", true);

        NERF_VANILLA_SPEARS = registerBoolean("nerf_vanilla_spears", true);
        SPEARS_ONLY_HORIZONTAL = registerBoolean("spears_only_horizontal", true);

        STAMINA_DRAIN_SPEED = registerInteger("stamina_drain_speed", 100, 0, 1000);
        EAT_DURATION_PROPORTIONAL_TO_FOOD = registerBoolean("eat_duration_proportional_to_food", true);
        EAT_HIT_CANCELLING = registerBoolean("eat_hit_cancelling", true);

        RESPAWN_WITH_LESS_HEALTH = registerBoolean("respawn_with_less_health", true);
        PARTIAL_KEEP_INVENTORY = registerBoolean("partial_keep_inventory", false);
        ITEM_DEATH_DESPAWN_TIME = registerInteger("item_death_despawn_time", 30, 0, 30);

        STRONGER_MOBS = registerBoolean("stronger_mobs", true);
        MOBS_LEAVE_VEHICLES_WHEN_ATTACKED = registerBoolean("mobs_leave_vehicles_when_attacked", true);

        BETTER_WITHER_FIGHT = registerBoolean("better_wither_fight", true);
        BETTER_DRAGON_FIGHT = registerBoolean("better_dragon_fight", true);
        DRAGON_WORLD_BORDER_BEFORE_KILL = registerBoolean("dragon_world_border_before_kill", true);
    }

    private static GameRule<Boolean> registerBoolean(String name, boolean defaultValue) {
        return register(name, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, defaultValue,
                FeatureFlagSet.of(), GameRuleTypeVisitor::visitBoolean,value -> value ? 1 : 0);
    }

    private static GameRule<Integer> registerInteger(
            final String id, final int defaultValue, final int min, final int max) {
        return register(id, GameRuleType.INT, IntegerArgumentType.integer(min, max), Codec.intRange(min, max),
                defaultValue, FeatureFlagSet.of(), GameRuleTypeVisitor::visitInteger, i -> i);
    }

    private static <T> GameRule<T> register(String name, GameRuleType type,
                                            ArgumentType<T> argumentType, Codec<T> codec, T defaultValue, FeatureFlagSet requiredFeatures,
                                            GameRules.VisitorCaller<T> acceptor, ToIntFunction<T> commandResultSupplier) {
        return Registry.register(BuiltInRegistries.GAME_RULE, JabsFixedCombat.id(name),
                new GameRule<>(GameRuleRegistry.JABSFIXEDCOMBAT, type, argumentType, acceptor, codec, commandResultSupplier, defaultValue, requiredFeatures));
    }
}
