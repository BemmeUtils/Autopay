package de.bemmeutils.autopay.listener;

import de.bemmeutils.autopay.Addon;
import de.bemmeutils.autopay.event.EventHandler;
import de.bemmeutils.autopay.event.events.TeamUpdateEvent;
import de.bemmeutils.autopay.utils.Citybuild;
import de.bemmeutils.autopay.utils.CitybuildType;

public class TeamUpdateListener implements EventHandler {
    @SuppressWarnings("unused")
    public void onTeamUpdate(TeamUpdateEvent event) {
        if (!Citybuild.getInstance().isOnGrieferGames()) return;
        if (!event.getTeamName().equals("server_value") || event.getTeamPrefix().isEmpty()) return;
        Citybuild.getInstance().setCitybuildType(CitybuildType.fromServerName(event.getTeamPrefix().replaceAll("§.", "")));
        Citybuild.getInstance().setOnCityBuild(!Citybuild.getInstance().getCitybuildType().equals(CitybuildType.NONE));
        if(Citybuild.getInstance().isOnCityBuild()){
            Addon.setCitybuildJoinTime(System.currentTimeMillis());
        }
    }
}
