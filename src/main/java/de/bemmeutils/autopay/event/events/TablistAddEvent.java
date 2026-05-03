package de.bemmeutils.autopay.event.events;

import com.mojang.authlib.GameProfile;

public class TablistAddEvent {
    private final GameProfile gameProfile;

    public TablistAddEvent(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }
}
