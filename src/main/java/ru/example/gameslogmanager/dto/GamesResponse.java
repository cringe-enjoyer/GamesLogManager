package ru.example.gameslogmanager.dto;

import java.util.List;

/**
 * Содержит список игр
 */
public class GamesResponse {
    private List<GameDTO> games;

    public GamesResponse() {
    }

    public GamesResponse(List<GameDTO> games) {
        this.games = games;
    }

    public List<GameDTO> getGames() {
        return games;
    }

    public void setGames(List<GameDTO> games) {
        this.games = games;
    }
}
