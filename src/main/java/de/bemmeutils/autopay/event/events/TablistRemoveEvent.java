package de.bemmeutils.autopay.event.events;

import com.mojang.authlib.GameProfile;

public class TablistRemoveEvent {
    private final GameProfile gameProfile;

    public TablistRemoveEvent(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }
}
