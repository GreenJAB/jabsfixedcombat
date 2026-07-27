package net.greenjab.jabsfixedcombat.mixin.dragon;

import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.registry.registries.GameRuleRegistry;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonSittingAttackingPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DragonSittingAttackingPhase.class)
public abstract class DragonSittingAttackingPhaseMixin {

    @ModifyConstant(method = "doServerTick", constant = @Constant(intValue = 40))
    private int fasterBreath(int constant){
        if (!JabsFixedCombat.SERVER.getGameRules().get(GameRuleRegistry.BETTER_DRAGON_FIGHT)) return constant;
        return 15;
    }

}
