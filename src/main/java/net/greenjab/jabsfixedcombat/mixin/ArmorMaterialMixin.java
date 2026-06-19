package net.greenjab.jabsfixedcombat.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ArmorMaterial.class)
public abstract class ArmorMaterialMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private static int adjustedCopperDurability(int durability,
                                                @Local(argsOnly = true) ResourceKey<EquipmentAsset> assetId) {
        if (assetId == EquipmentAssets.COPPER)
            return 20;
        return durability;
    }
}
