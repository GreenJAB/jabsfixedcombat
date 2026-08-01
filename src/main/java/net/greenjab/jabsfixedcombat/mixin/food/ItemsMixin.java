package net.greenjab.jabsfixedcombat.mixin.food;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.jabsfixedcombat.registry.item.NewBrickItem;
import net.greenjab.jabsfixedcombat.registry.item.NewGlisteringMelonSliceItem;
import net.greenjab.jabsfixedcombat.registry.item.NewTotemItem;
import net.greenjab.jabsfixedcombat.registry.registries.ItemRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.DamageResistant;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Function;

@Mixin(Items.class)
public abstract class ItemsMixin {

    @Shadow private static Item registerItem(String name, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }
    @Shadow private static Item registerItem(String name, Item.Properties properties) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @WrapOperation(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;)Lnet/minecraft/world/item/Item;"), slice = @Slice( from =
    @At(value = "CONSTANT", args = "stringValue=brick"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;BRICK:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item throwableBrick(String name, Operation<Item> original) {
        return registerItem("brick", NewBrickItem::new, new Item.Properties().useCooldown(1));}

    @WrapOperation(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;)Lnet/minecraft/world/item/Item;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=nether_brick"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;NETHER_BRICK:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item throwableNetherBrick(String name, Operation<Item> original) {
        return registerItem("nether_brick", NewBrickItem::new, new Item.Properties().useCooldown(1));}

    @WrapOperation(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", ordinal = 0), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=resin_brick"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;RESIN_BRICK:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item throwableResinBrick(String name, Item.Properties properties, Operation<Item> original) {
        return registerItem("resin_brick", NewBrickItem::new, new Item.Properties().useCooldown(1).trimMaterial(TrimMaterials.RESIN));}

    @WrapOperation(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=totem_of_undying"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;TOTEM_OF_UNDYING:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item useableTotem(String name, Item.Properties properties, Operation<Item> original) {
        return registerItem("totem_of_undying", NewTotemItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).component(DataComponents.DEATH_PROTECTION, DeathProtection.TOTEM_OF_UNDYING));}

    @WrapOperation(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;)Lnet/minecraft/world/item/Item;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=glistering_melon_slice"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;GLISTERING_MELON_SLICE:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item edibleGoldMelon(String name, Operation<Item> original) {
        return registerItem("glistering_melon_slice", NewGlisteringMelonSliceItem::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.8F).build()));}

    @WrapOperation(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;food(Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=glow_berries"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;GLOW_BERRIES:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item.Properties glowingGlowBerries(Item.Properties instance, FoodProperties foodProperties, Operation<Item.Properties> original) {
        return instance.food(Foods.HONEY_BOTTLE, ItemRegistry.GLOW_BERRIES_EFFECT);}

    @ModifyArg(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=rabbit_stew"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;RABBIT_STEW:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static int stackedRabbitStew(int max) {
        return 16;}

    @ModifyArg(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=beetroot_soup"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;BEETROOT_SOUP:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static int stackedBeetrootSoup(int max) {
        return 16;}

    @ModifyArg(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=mushroom_stew"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;MUSHROOM_STEW:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static int stackedMushroomStew(int max) {
        return 16;}

    @ModifyArg(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=suspicious_stew"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;SUSPICIOUS_STEW:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static int stackedSuspiciousSoup(int max) {
        return 16;}

    @WrapOperation(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=potion"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;POTION:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item.Properties stackedPotions(Item.Properties instance, int max, Operation<Item.Properties> original) {
        return original.call(instance, 16);}

    @WrapOperation(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=splash_potion"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;SPLASH_POTION:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item.Properties stackedSplashPotions(Item.Properties instance, int max, Operation<Item.Properties> original) {
        return original.call(instance, 16).useCooldown(3);}

    @WrapOperation(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=lingering_potion"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;LINGERING_POTION:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item.Properties stackedLingeringPotions(Item.Properties instance, int max, Operation<Item.Properties> original) {
        return original.call(instance, 16).useCooldown(3);}

    @ModifyArgs(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;spear(Lnet/minecraft/world/item/ToolMaterial;FFFFFFFFF)Lnet/minecraft/world/item/Item$Properties;"))
    private static void holdSpearsOutForever(Args args) {
        args.set(4, (float)args.get(4)+32000);
        args.set(6, (float)args.get(6)+32000);
        args.set(8, (float)args.get(8)+32000);
    }

    @WrapOperation(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;fireResistant()Lnet/minecraft/world/item/Item$Properties;"))
    private static Item.Properties blastProofNetherite(Item.Properties instance, Operation<Item.Properties> original) {
        return original.call(instance).delayedComponent(DataComponents.DAMAGE_RESISTANT, (context) -> new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)));}

    @WrapOperation(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;)Lnet/minecraft/world/item/Item;", ordinal = 0 ), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=blaze_rod"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;BLAZE_ROD:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
    private static Item fireProofBlazeRod(String name, Operation<Item> original) {
        return registerItem(name, new Item.Properties().fireResistant());}
}
