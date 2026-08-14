package de.bemmeutils.autopay.listener;

import de.bemmeutils.autopay.Addon;
import de.bemmeutils.autopay.event.EventHandler;
import de.bemmeutils.autopay.event.events.CitybuildUpdateEvent;
import de.bemmeutils.autopay.utils.Citybuild;
import de.bemmeutils.autopay.utils.CitybuildType;

public class CitybuildUpdateListener implements EventHandler {
    @SuppressWarnings("unused")
    public void onCitybuildUpdate(CitybuildUpdateEvent event) {
        if (!Citybuild.getInstance().isOnGrieferGames()) return;
        System.out.println(event.getCurrentCitybuild());

        String currentCitybuild = event.getCurrentCitybuild();
        if (currentCitybuild.isEmpty()) {
            Citybuild.getInstance().setCitybuildType(CitybuildType.NONE);
            Citybuild.getInstance().setOnCityBuild(false);
            return;
        }

        Citybuild.getInstance().setCitybuildType(CitybuildType.fromServerName(currentCitybuild));
        System.out.println(Citybuild.getInstance().getCitybuildType());
        Citybuild.getInstance().setOnCityBuild(!Citybuild.getInstance().getCitybuildType().equals(CitybuildType.NONE));
        System.out.println(Citybuild.getInstance().isOnCityBuild());

        if (Citybuild.getInstance().isOnCityBuild()) {
            Addon.setCitybuildJoinTime(System.currentTimeMillis());
        }
    }
}