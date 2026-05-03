package de.bemmeutils.autopay.listener;

import de.bemmeutils.autopay.event.EventHandler;
import de.bemmeutils.autopay.event.events.TablistRemoveEvent;
import de.bemmeutils.autopay.utils.Citybuild;
import net.minecraft.client.Minecraft;

public class TablistRemoveListener implements EventHandler {
    @SuppressWarnings("unused")
    public void onTablistRemove(TablistRemoveEvent event) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        if (Minecraft.getMinecraft().thePlayer.getGameProfile().equals(event.getGameProfile()) || Minecraft.getMinecraft().thePlayer.getGameProfile().getId().equals(event.getGameProfile().getId()))
            return;
        if (!Citybuild.getInstance().isOnCityBuild()) return;
        if (event.getGameProfile().getName() == null) return;
    }
}
