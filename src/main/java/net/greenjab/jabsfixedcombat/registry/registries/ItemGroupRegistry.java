package net.greenjab.jabsfixedcombat.registry.registries;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupRegistry {

    public static final CreativeModeTab JABS_FIXED_COMBAT = FabricCreativeModeTab.builder().title(Component.translatable("itemgroup.jabsfixedcombat"))
            .icon( () -> new ItemStack(ItemRegistry.SPEAR))
            .displayItems(
                     (_, entries) -> {

                        entries.accept(ItemRegistry.BROKEN_TOTEM);
                        entries.accept(ItemRegistry.ECHO_TOTEM);
                        entries.accept(ItemRegistry.ECHO_FRUIT);
                         entries.accept(ItemRegistry.SPEAR);
                    }).build();


    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, "jabs_fixed_combat", JABS_FIXED_COMBAT);
    }
}
