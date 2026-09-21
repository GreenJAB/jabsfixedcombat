package net.greenjab.jabsfixedcombat.mixin.food;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.jabsfixedcombat.registry.registries.GameRuleRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Inventory.class)
public abstract class InventoryMixin  {
    @WrapOperation(method = "dropAll", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;createItemStackToDrop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;"
    ))
    private ItemEntity onGroundForLonger(Player instance, ItemStack stack, boolean randomly, boolean thrownFromHand, Operation<ItemEntity> original) {
        ItemEntity entity = original.call(instance, stack, randomly, thrownFromHand);
        if (entity!=null) {
            int ticks = ((ServerLevel) instance.level()).getGameRules().get(GameRuleRegistry.ITEM_DEATH_DESPAWN_TIME) * 20 * 60;
            if (ticks == 0) entity.setUnlimitedLifetime();
            else entity.age = 6000 - ticks;
        }
        return entity;
    }
}
