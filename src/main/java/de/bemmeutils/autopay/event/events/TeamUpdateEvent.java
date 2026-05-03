package de.bemmeutils.autopay.event.events;

public class TeamUpdateEvent {
    private final String teamName;
    private final String teamPrefix;

    public TeamUpdateEvent(String teamName, String teamPrefix) {
        this.teamName = teamName;
        this.teamPrefix = teamPrefix;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamPrefix() {
        return teamPrefix;
    }
}
