package net.greenjab.jabsfixedcombat.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.registry.registries.GameRuleRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRules;


public class GameRuleStatus {
    public boolean use_stamina;

    public GameRuleStatus(){
    }

    public void updateRules(GameRules rules) {
        this.use_stamina = rules.get(GameRuleRegistry.STAMINA_DRAIN_SPEED)>0;
    }

    void toPacket(FriendlyByteBuf buf) {
        buf.writeBoolean(use_stamina);
    }

    static GameRuleStatus fromPacket(FriendlyByteBuf buf) {
        GameRuleStatus p = new GameRuleStatus();
        p.use_stamina = buf.readBoolean();
        return p;
    }

    public static void sendData(MinecraftServer server) {
        JabsFixedCombat.gameRules.updateRules(server.getGameRules());
        GameRulePayload payload = new GameRulePayload(JabsFixedCombat.gameRules);
        server.getPlayerList().getPlayers().forEach(player -> ServerPlayNetworking.send(player, payload));
    }
}
