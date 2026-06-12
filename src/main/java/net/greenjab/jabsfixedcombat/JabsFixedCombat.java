package net.greenjab.jabsfixedcombat;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.greenjab.jabsfixedcombat.network.SyncHandler;
import net.greenjab.jabsfixedcombat.registry.registries.*;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.DispenserBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class JabsFixedCombat implements ModInitializer {
	public static final String MOD_NAME = "Jabs Fixed Combat";
	public static final String NAMESPACE = "jabsfixedcombat";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing " + MOD_NAME);
		SyncHandler.init();

		ItemRegistry.registerItems();
		ItemGroupRegistry.register();
		GameRuleRegistry.registerGameRules();
		MobEffectRegistry.registerMobEffects();
		MenuRegistry.registerMenus();
		TextureModificationRegistry.registerTextureModifications();

		DispenserBlock.registerProjectileBehavior(Items.BRICK);
		DispenserBlock.registerProjectileBehavior(Items.NETHER_BRICK);
		DispenserBlock.registerProjectileBehavior(Items.RESIN_BRICK);
		DispenserBlock.registerProjectileBehavior(Items.TRIDENT);

		FabricLoader.getInstance().getModContainer(NAMESPACE).ifPresent(modContainer ->
			ResourceManagerHelper.registerBuiltinResourcePack(
                JabsFixedCombat.id("tiered_crafting"),
                modContainer,
                Component.nullToEmpty("fixedminecraft.tiered_crafting"),
                ResourcePackActivationType.NORMAL)
		);
	}

	public static ArrayList<ItemStack> getArmor(LivingEntity entity) {
		ArrayList<ItemStack> armor = new ArrayList<>();
		armor.add(entity.getItemBySlot(EquipmentSlot.FEET));
		armor.add(entity.getItemBySlot(EquipmentSlot.LEGS));
		armor.add(entity.getItemBySlot(EquipmentSlot.CHEST));
		armor.add(entity.getItemBySlot(EquipmentSlot.HEAD));
		return armor;
	}

	public static int enchantLevel(ItemStack stack, String name) {
		int level = 0;
		ItemEnchantments itemEnchantmentsComponent = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		for (Holder<Enchantment> e : stack.getEnchantments().keySet()) {
			if (e.getRegisteredName().toLowerCase().contains(name.toLowerCase())) {
				level += itemEnchantmentsComponent.getLevel(e);
			}
		}
		return level;
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(NAMESPACE, path);
	}
}