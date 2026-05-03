package de.bemmeutils.autopay.event.events;

import net.minecraft.client.multiplayer.ServerData;

public class ServerSwitchEvent {
    private final ServerData serverData;

    public ServerSwitchEvent(ServerData serverData) {
        this.serverData = serverData;
    }

    public ServerData getServerData(){
        return this.serverData;
    }
}
