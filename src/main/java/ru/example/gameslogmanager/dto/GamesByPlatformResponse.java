package ru.example.gameslogmanager.dto;

import ru.example.gameslogmanager.models.Platform;
import ru.example.gameslogmanager.models.UsersGame;

import java.util.List;
import java.util.Map;

public class GamesByPlatformResponse {
    private Map<Platform, List<UsersGame>> gamesByPlatform;

    public GamesByPlatformResponse() {
    }

    public GamesByPlatformResponse(Map<Platform, List<UsersGame>> gamesByPlatform) {
        this.gamesByPlatform = gamesByPlatform;
    }

    public Map<Platform, List<UsersGame>> getGamesByPlatform() {
        return gamesByPlatform;
    }

    public void setGamesByPlatform(Map<Platform, List<UsersGame>> gamesByPlatform) {
        this.gamesByPlatform = gamesByPlatform;
    }
}
