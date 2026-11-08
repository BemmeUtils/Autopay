package de.bemmeutils.autopay.mixin.mixins;

import com.mojang.authlib.GameProfile;
import de.bemmeutils.autopay.cache.GameProfileCache;
import de.bemmeutils.autopay.event.EventBus;
import de.bemmeutils.autopay.event.events.ServerSwitchEvent;
import de.bemmeutils.autopay.event.events.TablistAddEvent;
import de.bemmeutils.autopay.event.events.TablistRemoveEvent;
import de.bemmeutils.autopay.event.events.TeamUpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S3EPacketTeams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlePlayClient {

    @Inject(method = "handlePlayerListItem", at = @At("RETURN"))
    private void onHandlePlayerListItem(S38PacketPlayerListItem packetIn, CallbackInfo callbackInfo) {
        for (S38PacketPlayerListItem.AddPlayerData playerListItem : packetIn.getEntries()) {
            new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(playerListItem);
            GameProfile gameProfile = playerListItem.getProfile();
            if (gameProfile == null || gameProfile.getId().toString().matches(".*-.*-.*-.*-0{12}")) continue;
            switch (packetIn.getAction()) {
                case ADD_PLAYER:
                    GameProfileCache.getInstance().add(gameProfile);
                    EventBus.post(new TablistAddEvent(gameProfile));
                    break;
                case REMOVE_PLAYER:
                    gameProfile = GameProfileCache.getInstance().getOrDefault(gameProfile.getId(), gameProfile);
                    EventBus.post(new TablistRemoveEvent(gameProfile));
                    break;
            }
        }
    }

    @Inject(method = "handleJoinGame", at = @At("TAIL"))
    private void onHandleJoinGame(S01PacketJoinGame packetIn, CallbackInfo callbackInfo) {
        // getCurrentServerData() is null when the client was started with --server/--port
        // (typical on headless/Linux setups) - post the event anyway, listeners handle null.
        EventBus.post(new ServerSwitchEvent(Minecraft.getMinecraft().getCurrentServerData()));
    }

    @Inject(method = "handleTeams", at = @At("TAIL"))
    private void onHandleTeams(S3EPacketTeams packetIn, CallbackInfo callbackInfo) {
        EventBus.post(new TeamUpdateEvent(packetIn.getName(), packetIn.getPrefix()));
    }
}