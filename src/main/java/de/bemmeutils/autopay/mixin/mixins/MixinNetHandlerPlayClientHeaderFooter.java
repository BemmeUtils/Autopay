package de.bemmeutils.autopay.mixin.mixins;

import de.bemmeutils.autopay.event.EventBus;
import de.bemmeutils.autopay.event.events.CitybuildUpdateEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClientHeaderFooter {

    @Inject(method = "handlePlayerListHeaderFooter", at = @At("TAIL"))
    private void onHandlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn, CallbackInfo ci) {
        String[] lines = packetIn.getHeader().getFormattedText().split("\n");

        String currentCitybuild;
        if (lines.length != 3) {
            currentCitybuild = "";
        } else {
            String cbLine = lines[2].replaceAll("§.", "");
            if (!cbLine.startsWith("Aktueller Server: ")) {
                currentCitybuild = "";
            } else {
                currentCitybuild = cbLine.substring("Aktueller Server: ".length());
            }
        }

        EventBus.post(new CitybuildUpdateEvent(currentCitybuild));
    }
}