package net.greenjab.jabsfixedcombat.mixin.mobs;

import net.greenjab.jabsfixedcombat.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.skeleton.WitherSkeleton;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LevelEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected boolean dead;

    @Shadow
    private BlockPos lastPos;

    @Shadow
    protected abstract void dropAllDeathLoot(ServerLevel level, DamageSource source);

    @Inject(method = "hurtServer", at = @At(
            value = "HEAD"), cancellable = true)
    private void witherSkeletonIgnoreWither(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity LE = (LivingEntity)(Object)this;
        if (LE instanceof WitherSkeleton) {
            if (source.getEntity() instanceof WitherBoss) cir.setReturnValue(false);
        }
    }

    @Inject(method = "hurtServer",at = @At( value = "TAIL" ))
    private void exitVehicleOnDamage(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if (damage <= 0) return;
        if (entity.isAlwaysTicking()) return;

        Entity vehicle = entity.getVehicle();
        if (vehicle == null) return;

        if (vehicle.is(ModTags.VEHICLES)) entity.stopRiding();
    }

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void renewableEchoShards(DamageSource source, CallbackInfo ci){

        LivingEntity LE = ((LivingEntity) (Object) this);
        if (!LE.isRemoved() && !this.dead) {
            if (LE instanceof Allay AE) {
                if (source.is(DamageTypes.SONIC_BOOM)) {
                    ServerLevel world = (ServerLevel) AE.level();
                    AE.spawnAtLocation(world, Items.ECHO_SHARD);
                    this.dropAllDeathLoot(world, source);

                    Vex VE = AE.convertTo(
                            EntityType.VEX, ConversionParams.single(AE, true, true), /* method_63655 */ vex -> {
                                vex.finalizeSpawn(world, world.getCurrentDifficultyAt(vex.blockPosition()), EntitySpawnReason.CONVERSION, null);
                                world.levelEvent(null, LevelEvent.SOUND_SKELETON_TO_STRAY, this.lastPos, 0);
                            }
                    );

                    if (VE != null) {
                        VE.finalizeSpawn(
                                world, world.getCurrentDifficultyAt(VE.blockPosition()), EntitySpawnReason.CONVERSION, null
                        );
                        if (!AE.isSilent()) {
                            world.levelEvent(null, LevelEvent.SOUND_ZOMBIE_INFECTED, AE.blockPosition(), 0);
                        }
                    }
                    AE.discard();
                    ci.cancel();
                }
            }
        }
    }

    @ModifyConstant(method = "getVisibilityPercent", constant = @Constant(doubleValue = 0.8))
    private double moreSneaky(double constant){
        LivingEntity LE = (LivingEntity) (Object)this;
        if (LE instanceof Monster) return 0.3;
        return constant;
    }
}
