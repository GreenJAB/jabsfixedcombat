package net.greenjab.jabsfixedcombat.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.greenjab.jabsfixedcombat.network.SaturationSyncPayload;

public class ClientSyncHandler {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(SaturationSyncPayload.ID, (payload, context) ->
                context.client().execute(() ->
                        context.client().player.getFoodData().setSaturation(payload.getSaturation())));
    }
}
