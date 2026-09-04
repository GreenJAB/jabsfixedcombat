package net.greenjab.jabsfixedcombat.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.registry.registries.GameRuleRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRules;


public class GameRuleStatus {
    public boolean use_stamina;
    public boolean use_totem;
    public boolean eat_duration;
    public boolean modified_beacon;
    public boolean modified_dragon;
    public boolean modified_wither;

    public GameRuleStatus(){
    }

    public void updateRules(GameRules rules) {
        this.use_stamina = rules.get(GameRuleRegistry.STAMINA_DRAIN_SPEED)>0;
        this.use_totem = rules.get(GameRuleRegistry.REQUIRE_TOTEM_USE);
        this.eat_duration = rules.get(GameRuleRegistry.EAT_DURATION_PROPORTIONAL_TO_FOOD);
        this.modified_beacon = rules.get(GameRuleRegistry.MODIFIED_BEACON);
        this.modified_dragon = rules.get(GameRuleRegistry.MODIFIED_DRAGON_FIGHT);
        this.modified_wither = rules.get(GameRuleRegistry.MODIFIED_WITHER_FIGHT);
    }

    void toPacket(FriendlyByteBuf buf) {
        buf.writeBoolean(use_stamina);
        buf.writeBoolean(use_totem);
        buf.writeBoolean(eat_duration);
        buf.writeBoolean(modified_beacon);
        buf.writeBoolean(modified_dragon);
        buf.writeBoolean(modified_wither);
    }

    static GameRuleStatus fromPacket(FriendlyByteBuf buf) {
        GameRuleStatus p = new GameRuleStatus();
        p.use_stamina = buf.readBoolean();
        p.use_totem = buf.readBoolean();
        p.eat_duration = buf.readBoolean();
        p.modified_beacon = buf.readBoolean();
        p.modified_dragon = buf.readBoolean();
        p.modified_wither = buf.readBoolean();
        return p;
    }

    public static void sendData(MinecraftServer server) {
        JabsFixedCombat.gameRules.updateRules(server.getGameRules());
        GameRulePayload payload = new GameRulePayload(JabsFixedCombat.gameRules);
        server.getPlayerList().getPlayers().forEach(player -> ServerPlayNetworking.send(player, payload));
    }
}
