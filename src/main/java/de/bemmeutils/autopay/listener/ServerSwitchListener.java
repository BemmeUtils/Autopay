package de.bemmeutils.autopay.listener;

import de.bemmeutils.autopay.event.EventHandler;
import de.bemmeutils.autopay.event.events.ServerSwitchEvent;
import de.bemmeutils.autopay.utils.Citybuild;
import de.bemmeutils.autopay.utils.CitybuildType;

import java.util.Locale;

public class ServerSwitchListener implements EventHandler {
    @SuppressWarnings("unused")
    public void onServerSwitch(ServerSwitchEvent event) {
        Citybuild.getInstance().setOnGrieferGames(event.getServerData().serverIP.toLowerCase(Locale.ROOT).contains("griefergames"));
        Citybuild.getInstance().setOnCityBuild(false);
        Citybuild.getInstance().setCitybuildType(CitybuildType.NONE);
    }
}
