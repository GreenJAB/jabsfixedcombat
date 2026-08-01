package net.greenjab.jabsfixedcombat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.jabsfixedcombat.registry.registries.GameRuleRegistry;
import net.greenjab.jabsfixedcombat.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Inventory.class)
public abstract class InventoryMixin {

    @Shadow @Final public Player player;

    @WrapOperation(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"))
    private boolean noDropSpecialItems(ItemStack instance, Operation<Boolean> original) {
        if (this.player.level() instanceof ServerLevel level && !level.getGameRules().get(GameRuleRegistry.PARTIAL_KEEP_INVENTORY)) return original.call(instance);
        if (instance.is(ModTags.PARTIAL_KEEP_INVENTORY)) return true;
        return original.call(instance);
    }
}
