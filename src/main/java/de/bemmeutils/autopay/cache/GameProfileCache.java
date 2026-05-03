package de.bemmeutils.autopay.cache;

import com.mojang.authlib.GameProfile;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class GameProfileCache {

    private static GameProfileCache instance = null;

    public static GameProfileCache getInstance() {
        if (instance == null) {
            instance = new GameProfileCache();
        }
        return instance;
    }

    private static final ConcurrentHashMap<UUID, GameProfile> gameProfileCache = new ConcurrentHashMap<UUID, GameProfile>();

    public void add(GameProfile gameProfile) {
        gameProfileCache.put(gameProfile.getId(), gameProfile);
    }

    public GameProfile get(UUID uuid) {
        if (!gameProfileCache.containsKey(uuid)) return null;
        GameProfile gameProfile = gameProfileCache.get(uuid);
        gameProfileCache.remove(uuid);
        return gameProfile;
    }

    public GameProfile getOrDefault(UUID uuid, GameProfile fallback) {
        GameProfile result = get(uuid);
        return result != null ? result : fallback;
    }

    public void remove(UUID uuid) {
        gameProfileCache.remove(uuid);
    }
}

