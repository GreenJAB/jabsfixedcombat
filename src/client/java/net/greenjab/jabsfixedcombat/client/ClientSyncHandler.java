package net.greenjab.jabsfixedcombat.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.greenjab.jabsfixedcombat.JabsFixedCombat;
import net.greenjab.jabsfixedcombat.network.GameRulePayload;
import net.greenjab.jabsfixedcombat.network.SaturationSyncPayload;

public class ClientSyncHandler {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(GameRulePayload.PACKET_ID, ClientSyncHandler::gamerule);
        ClientPlayNetworking.registerGlobalReceiver(SaturationSyncPayload.ID, (payload, context) ->
                context.client().execute(() ->
                        context.client().player.getFoodData().setSaturation(payload.getSaturation())));
    }

    private static void gamerule(GameRulePayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(()-> JabsFixedCombat.gameRules = payload.rules());
    }
}
