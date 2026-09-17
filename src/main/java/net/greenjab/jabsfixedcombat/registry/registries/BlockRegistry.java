package net.greenjab.jabsfixedcombat.registry.registries;

import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.registry.block.EndFireBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import java.util.function.Function;

public class BlockRegistry {

    public static final Block END_FIRE = register(
            "end_fire",
            EndFireBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .replaceable()
                    .noCollision()
                    .instabreak()
                    .lightLevel(_ -> 10)
                    .sound(SoundType.WOOL)
                    .pushReaction(PushReaction.DESTROY)
    );

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return register(keyOf(id), factory, settings);
    }
    private static ResourceKey<Block> keyOf(String id) {
        return ResourceKey.create(Registries.BLOCK, JabsFixedCombat.id(id));
    }
    public static Block register(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        Block block = factory.apply(settings.setId(key));
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    public static void registerBlocks() {
        System.out.println("register Blocks");
    }

}
