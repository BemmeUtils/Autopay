package de.bemmeutils.autopay.listener;

import de.bemmeutils.autopay.event.EventHandler;
import de.bemmeutils.autopay.event.events.ServerSwitchEvent;
import de.bemmeutils.autopay.utils.Citybuild;
import de.bemmeutils.autopay.utils.CitybuildServer;

import java.util.Locale;

public class ServerSwitchListener implements EventHandler {
    @SuppressWarnings("unused")
    public void onServerSwitch(ServerSwitchEvent event) {
        new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(event);

        boolean onGG = event.getServerData() != null
                && event.getServerData().serverIP != null
                && event.getServerData().serverIP.toLowerCase(Locale.ROOT).contains("griefergames");
        Citybuild.getInstance().setOnGrieferGames(onGG);
        Citybuild.getInstance().setOnCityBuild(false);
        Citybuild.getInstance().setCitybuildServer(CitybuildServer.NONE);
    }
}
