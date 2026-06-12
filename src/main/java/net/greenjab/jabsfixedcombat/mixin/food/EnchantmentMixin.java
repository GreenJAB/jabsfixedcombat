package net.greenjab.jabsfixedcombat.mixin.food;

import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Inject(method = "doPostPiercingAttack", at = @At(value = "HEAD"), cancellable = true)
    private void staminaCanLunge(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse item, Entity user, CallbackInfo ci) {
        if (user instanceof Player PE) {
            ItemStack weapon = item.itemStack();
            if (!weapon.isEmpty()) {
                int lungeLevel = JabsFixedCombat.enchantLevel(weapon, "lunge");
                if (lungeLevel > 0) {
                    float stamina = PE.getFoodData().getSaturationLevel();
                    if (stamina < lungeLevel * 2) ci.cancel();
                }
            }
        }
    }
}
