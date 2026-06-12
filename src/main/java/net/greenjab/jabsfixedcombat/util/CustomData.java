package net.greenjab.jabsfixedcombat.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class CustomData {
    public static void setData(LivingEntity entity, String name, int data) {
        if (entity.level().getScoreboard().getObjective(name) == null) {
            entity.level().getScoreboard().addObjective(
                    name, ObjectiveCriteria.DUMMY, Component.nullToEmpty(name), ObjectiveCriteria.DUMMY.getDefaultRenderType(), false, null);
        }

        entity.level().getScoreboard().getOrCreatePlayerScore(entity, entity.level().getScoreboard().getObjective(name))
                .set(data);
    }

    public static int getData(LivingEntity entity, String name)  {
        if (entity.level().getScoreboard().getObjective(name) != null) {
            return entity.level().getScoreboard().getOrCreatePlayerScore(entity, entity.level().getScoreboard().getObjective(name))
                    .get();
        }
        return 0;
    }
}
