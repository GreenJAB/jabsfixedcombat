package net.greenjab.jabsfixedcombat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @ModifyExpressionValue(method = "attack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getEnchantedDamage(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/damagesource/DamageSource;)F",
            ordinal = 0
    ))
    private float impalingEffectsWetMobs(float original, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof LivingEntity) {
            Player PE = (Player) (Object) this;
            ItemEnchantments enchantments = PE.getMainHandItem().getEnchantments();
            int i = 0;
            for (Holder<Enchantment> entry : enchantments.keySet()) {
                if (entry.unwrapKey().isPresent() && entry.unwrapKey().get().equals(Enchantments.IMPALING)) {
                    i = enchantments.getLevel(entry);
                }
            }

            return original + ((entity.is(EntityTypeTags.AQUATIC) || entity.isInWaterOrRain()) ? i * 1.5F : 0.0F);
        }
        return original;
    }
}
