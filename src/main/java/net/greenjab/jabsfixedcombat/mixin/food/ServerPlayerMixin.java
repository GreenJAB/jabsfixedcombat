package net.greenjab.jabsfixedcombat.mixin.food;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.network.SyncHandler;
import net.greenjab.jabsfixedcombat.registry.registries.GameRuleRegistry;
import net.greenjab.jabsfixedcombat.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    @Shadow public abstract @NonNull ServerLevel level();

    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    void onUpdate(CallbackInfo info) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        SyncHandler.onPlayerUpdate(player);
    }

    @ModifyConstant(method = "checkMovementStatistics", constant = @Constant(floatValue = 0.01f, ordinal = 0))
    public float swimDrainsStamina(float constant) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        if (player.isAutoSpinAttack()) return 0;
        if (player.hasEffect(MobEffects.DOLPHINS_GRACE)) return 0;
        return 0.06f;
    }

    @ModifyConstant(method = "checkMovementStatistics", constant = @Constant(floatValue = 0.01f, ordinal = 2))
    public float walkSwimNoStamina(float constant) { return 0; }
    @ModifyConstant(method = "checkMovementStatistics", constant = @Constant(floatValue = 0.01f, ordinal = 4))
    public float walkSwimNoStamina2(float constant) { return 0; }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void shieldDrainsStamina(CallbackInfo ci) {
        if (!JabsFixedCombat.gameRules.use_stamina) return;
        ServerPlayer SPE = (ServerPlayer) (Object)this;
        if (SPE.isBlocking()) SPE.causeFoodExhaustion(0.03f);
    }

    @ModifyConstant(method = "jumpFromGround", constant = @Constant(floatValue = 0.05f))
    private float noStaminaNormalJump(float constant) {
        return 0;
    }

    @Inject(method = "swing", at = @At("TAIL"))
    private void missCooldown(InteractionHand hand, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        if (player.getLastHurtMobTimestamp() != this.tickCount) player.attackStrengthTicker = (int)(player.getCurrentItemAttackStrengthDelay()/2.0);
    }

    @Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "RETURN"))
    private void itemsOnGroundForLonger(ItemStack itemStack, boolean randomly, boolean thrownFromHand, CallbackInfoReturnable<ItemEntity> cir,
                                        @Local ItemEntity entity) {
        if (!thrownFromHand && entity != null) {
            int ticks = this.level().getGameRules().get(GameRuleRegistry.ITEM_DEATH_DESPAWN_TIME)*20*60;
            if (ticks == 0) entity.setUnlimitedLifetime();
            else entity.age = 6000-ticks;
        }
    }

    @ModifyExpressionValue(method = "restoreFrom", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private <T> T noDropSpecialItems(T original, @Local(argsOnly = true) ServerPlayer oldPlayer) {
        if (!oldPlayer.level().getGameRules().get(GameRuleRegistry.PARTIAL_KEEP_INVENTORY)) return original;
        if ((boolean) original || oldPlayer.isSpectator()) {
            return original;
        } else {
            for (int i = 0; i < this.getInventory().getContainerSize(); i++) {
                if (oldPlayer.getInventory().getItem(i).is(ModTags.PARTIAL_KEEP_INVENTORY))
                    this.getInventory().setItem(i, oldPlayer.getInventory().getItem(i));
            }
        }
        return original;
    }
}
