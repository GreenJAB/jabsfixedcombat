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
    public static GameRule<Boolean> REQUIRE_TOTEM_USE;
    public static GameRule<Integer> STAMINA_DRAIN_SPEED;

    public static void registerGameRules() {
        System.out.println("register GameRules");
        REQUIRE_TOTEM_USE = registerBooleanRule("require_totem_use", GameRuleCategory.PLAYER, false);
        STAMINA_DRAIN_SPEED = registerIntRule("stamina_drain_speed", GameRuleCategory.PLAYER, 100, 1);
    }

    private static GameRule<Boolean> registerBooleanRule(String name, GameRuleCategory category, boolean defaultValue) {
        return register(name, category, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, defaultValue,
                FeatureFlagSet.of(), GameRuleTypeVisitor::visitBoolean,value -> value ? 1 : 0
        );
    }

    private static GameRule<Integer> registerIntRule(String name, GameRuleCategory category, int defaultValue, int minValue) {
        return registerIntRule(name, category, defaultValue, minValue, Integer.MAX_VALUE, FeatureFlagSet.of());
    }

    private static GameRule<Integer> registerIntRule(
            String name, GameRuleCategory category, int defaultValue, int minValue, int maxValue, FeatureFlagSet requiredFeatures
    ) {
        return register(name, category, GameRuleType.INT, IntegerArgumentType.integer(minValue, maxValue),
                Codec.intRange(minValue, maxValue), defaultValue, requiredFeatures, GameRuleTypeVisitor::visitInteger,
                value -> value
        );
    }

    private static <T> GameRule<T> register( String name, GameRuleCategory category, GameRuleType type,
            ArgumentType<T> argumentType, Codec<T> codec, T defaultValue,  FeatureFlagSet requiredFeatures,
            GameRules.VisitorCaller<T> acceptor, ToIntFunction<T> commandResultSupplier
    ) {
        return Registry.register(
                BuiltInRegistries.GAME_RULE, JabsFixedCombat.id(name), new GameRule<>(category, type, argumentType, acceptor, codec, commandResultSupplier, defaultValue, requiredFeatures)
        );
    }

}
