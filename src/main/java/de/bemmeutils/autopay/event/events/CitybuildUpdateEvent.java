package de.bemmeutils.autopay.event.events;

public class CitybuildUpdateEvent {
    private final String currentCitybuild;

    public CitybuildUpdateEvent(String currentCitybuild) {
        this.currentCitybuild = currentCitybuild;
    }

    public String getCurrentCitybuild() {
        return currentCitybuild;
    }
}