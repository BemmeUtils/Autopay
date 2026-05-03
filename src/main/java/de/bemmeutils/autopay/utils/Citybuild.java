package de.bemmeutils.autopay.utils;

public class Citybuild {
    private static Citybuild instance;

    public boolean isOnCityBuild() {
        return onCityBuild;
    }

    public void setOnCityBuild(boolean onCityBuild) {
        this.onCityBuild = onCityBuild;
    }

    public boolean isOnGrieferGames() {
        return onGrieferGames;
    }

    public void setOnGrieferGames(boolean onGrieferGames) {
        this.onGrieferGames = onGrieferGames;
    }

    public CitybuildType getCitybuildType() {
        return citybuildType;
    }

    public void setCitybuildType(CitybuildType citybuildType) {
        this.citybuildType = citybuildType;
    }

    private boolean onCityBuild = false;
    private boolean onGrieferGames = false;
    private CitybuildType citybuildType;

    private Citybuild(boolean isOnGrieferGames, boolean isOnCitybuild, CitybuildType citybuildType) {
        this.onGrieferGames = isOnGrieferGames;
        this.onCityBuild = isOnCitybuild;
        this.citybuildType = citybuildType;
    }

    public static Citybuild getInstance() {
        if (instance == null) {
            instance = new Citybuild(false, false, CitybuildType.NONE);
        }
        return instance;
    }

}