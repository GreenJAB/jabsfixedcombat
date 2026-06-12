package net.greenjab.jabsfixedcombat.registry.registries;

import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.registry.other.FletchingMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class MenuRegistry {

    public static final MenuType<FletchingMenu> FLETCHING_SCREEN_HANDLER =
            Registry.register(
                    BuiltInRegistries.MENU,
                    JabsFixedCombat.id("fletching"),
                    new MenuType<>(FletchingMenu::new, FeatureFlags.VANILLA_SET)
            );

    public static void registerMenus() {
        System.out.println("register Menus");
    }
}
