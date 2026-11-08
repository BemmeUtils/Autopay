package de.bemmeutils.autopay.listener;

import de.bemmeutils.autopay.Addon;
import de.bemmeutils.autopay.event.EventHandler;
import de.bemmeutils.autopay.event.events.CitybuildUpdateEvent;
import de.bemmeutils.autopay.utils.Citybuild;
import de.bemmeutils.autopay.utils.CitybuildServer;

public class CitybuildUpdateListener implements EventHandler {
    @SuppressWarnings("unused")
    public void onCitybuildUpdate(CitybuildUpdateEvent event) {
        Citybuild.getInstance().setOnGrieferGames(true);

        boolean wasOnCityBuild = Citybuild.getInstance().isOnCityBuild();
        CitybuildServer previous = Citybuild.getInstance().getCitybuildServer();
        Citybuild.getInstance().setCitybuildServer(event.getCitybuildServer());

        Citybuild.getInstance().setOnCityBuild(!event.getCitybuildServer().equals(CitybuildServer.NONE));

        if (Citybuild.getInstance().isOnCityBuild() && (!wasOnCityBuild || previous != event.getCitybuildServer())) {
            Addon.setCitybuildJoinTime(System.currentTimeMillis());
        }
    }
}