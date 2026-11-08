package de.bemmeutils.autopay.utils;

import lombok.Getter;
import lombok.Setter;

public class Citybuild {
    private static Citybuild instance;

    public void setOnGrieferGames(boolean onGrieferGames) {
        this.onGrieferGames = onGrieferGames;
    }

    @Getter
    @Setter
    private volatile boolean onCityBuild;
    private volatile boolean onGrieferGames = false;
    @Getter
    @Setter
    private volatile CitybuildServer citybuildServer;

    private Citybuild(boolean isOnGrieferGames, boolean isOnCitybuild, CitybuildServer citybuildServer) {
        this.onGrieferGames = isOnGrieferGames;
        this.onCityBuild = isOnCitybuild;
        this.citybuildServer = citybuildServer;
    }

    public static Citybuild getInstance() {
        if (instance == null) {
            instance = new Citybuild(false, false, CitybuildServer.NONE);
        }
        return instance;
    }

}