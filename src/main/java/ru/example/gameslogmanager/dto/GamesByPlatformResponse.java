package ru.example.gameslogmanager.dto;

import java.util.List;
import java.util.Map;

public class GamesByPlatformResponse {
    private Map<PlatformDTO, List<UsersGameDTO>> gamesByPlatform;

    public GamesByPlatformResponse() {
    }

    public GamesByPlatformResponse(Map<PlatformDTO, List<UsersGameDTO>> gamesByPlatform) {
        this.gamesByPlatform = gamesByPlatform;
    }

    public Map<PlatformDTO, List<UsersGameDTO>> getGamesByPlatform() {
        return gamesByPlatform;
    }

    public void setGamesByPlatform(Map<PlatformDTO, List<UsersGameDTO>> gamesByPlatform) {
        this.gamesByPlatform = gamesByPlatform;
    }
}
