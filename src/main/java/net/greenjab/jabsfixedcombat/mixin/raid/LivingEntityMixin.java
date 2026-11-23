package net.greenjab.jabsfixedcombat.mixin.raid;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.registry.registries.ItemRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin  {

    @ModifyExpressionValue(method = "checkTotemDeathProtection", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack requireUsingTotem(ItemStack original) {
        LivingEntity LE = (LivingEntity)(Object)this;
        if (original.isDamaged()) return new ItemStack(Items.AIR);
        if (!JabsFixedCombat.gameRules.use_totem || LE.isUsingItem()) return original;
        return new ItemStack(Items.AIR);
    }

    @WrapOperation(method = "checkTotemDeathProtection", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private void brokenTotem(ItemStack instance, int amount, Operation<Void> original,
                             @Local InteractionHand hand) {
        if ((LivingEntity)(Object)this instanceof Player player) {
            ItemStack i = player.getItemInHand(hand);
            if (i.is(Items.TOTEM_OF_UNDYING)) {
                i.setDamageValue(1);
                i.set(DataComponents.REPAIR_COST, 0);
                player.stopUsingItem();
            } else {
                ItemStack broken = new ItemStack(Items.TOTEM_OF_UNDYING);
                broken.setDamageValue(1);
                broken.set(DataComponents.REPAIR_COST, 0);
                original.call(instance, amount);
                if (i.isEmpty()) player.setItemInHand(hand, broken);
                else {
                    if (!player.getInventory().add(broken)) {
                        ItemEntity drop = player.createItemStackToDrop(broken, false, true);
                        if (drop != null) player.level().addFreshEntity(drop);
                    }
                }
            }
        }
    }

    @Inject(method = "checkTotemDeathProtection", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;level()Lnet/minecraft/world/level/Level;", shift = At.Shift.AFTER))
    private void echoTeleport(DamageSource killingDamage, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) ItemStack protectionItem) {
        if ((LivingEntity)(Object)this instanceof ServerPlayer SPE && protectionItem.is(ItemRegistry.ECHO_TOTEM)) goToSpawn(SPE);
    }

    @Unique
    private void goToSpawn(ServerPlayer player) {
        TeleportTransition teleportTarget = player.findRespawnPositionAndUseSpawnBlock(true, TeleportTransition.DO_NOTHING);
        ServerLevel serverWorld = teleportTarget.newLevel();
        Vec3 pos = teleportTarget.position();
        if (player.teleportTo(serverWorld, pos.x, pos.y, pos.z, Set.of(), player.getYRot(), player.getXRot(), true)) {
            serverWorld.gameEvent(GameEvent.TELEPORT, pos, GameEvent.Context.of(player));
            serverWorld.playSound(player, pos.x(), pos.y(), pos.z(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS);
            player.resetFallDistance();
        }
    }
}
