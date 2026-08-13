package net.greenjab.jabsfixedcombat.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.registry.registries.MenuRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;

public class JabsFixedCombatClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientSyncHandler.init();

		MenuScreens.register(MenuRegistry.FLETCHING_SCREEN_HANDLER, FletchingScreen::new);

		FabricLoader.getInstance().getModContainer("jabsfixedcombat").ifPresent(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(
                JabsFixedCombat.id( "almost_vanilla_potions"),
                modContainer,
                Component.translatable("jabsfixedcombat.almost_vanilla_potions"),
                ResourcePackActivationType.NORMAL
        ));
	}
}