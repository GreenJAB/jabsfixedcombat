package net.greenjab.jabsfixedcombat.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentLayerRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class EquipmentLayerRendererMixin {

    @Unique private static final EquipmentClientInfo copperExposedModel = createHumanoidAndHorseModel("copper_exposed");
    @Unique private static final EquipmentClientInfo copperWeatheredModel = createHumanoidAndHorseModel("copper_weathered");
    @Unique private static final EquipmentClientInfo copperOxidizedModel = createHumanoidAndHorseModel("copper_oxidized");

    @ModifyExpressionValue(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/EquipmentAssetManager;get(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/client/resources/model/EquipmentClientInfo;"
    ))
    private EquipmentClientInfo useNewArmorModel(EquipmentClientInfo original, @Local(argsOnly = true) ResourceKey<EquipmentAsset> equipmentAssetId,
                                                 @Local(argsOnly = true) ItemStack itemStack) {
        if (equipmentAssetId.toString().toLowerCase().contains("copper")) {
            if (itemStack.getMaxDamage()==0) return original;
            float durability = itemStack.getDamageValue() / (itemStack.getMaxDamage() + 0.0f);
            if (durability>0.75f) return copperOxidizedModel;
            if (durability>0.5f) return copperWeatheredModel;
            if (durability>0.25f) return copperExposedModel;
        }
        return original;
    }

    @Unique
    private static EquipmentClientInfo createHumanoidAndHorseModel(String id) {
        return EquipmentClientInfo.builder().addHumanoidLayers(Identifier.withDefaultNamespace(id))
                .addLayers(EquipmentClientInfo.LayerType.HORSE_BODY, EquipmentClientInfo.Layer.leatherDyeable(Identifier.withDefaultNamespace(id), false)).build();
    }

}
