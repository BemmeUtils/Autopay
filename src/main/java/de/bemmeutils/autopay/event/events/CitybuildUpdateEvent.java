package de.bemmeutils.autopay.event.events;

import de.bemmeutils.autopay.utils.CitybuildServer;
import lombok.Getter;

public class CitybuildUpdateEvent {
    @Getter
    private final CitybuildServer citybuildServer;

    public CitybuildUpdateEvent(CitybuildServer citybuildServer) {
        this.citybuildServer = citybuildServer;
    }
}