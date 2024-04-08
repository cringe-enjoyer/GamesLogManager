package ru.example.gameslogmanager.dto;

import ru.example.gameslogmanager.models.Genre;
import ru.example.gameslogmanager.models.UsersGame;

import java.util.List;
import java.util.Map;

public class GamesByGenresResponse {
    private Map<Genre, List<UsersGame>> gamesByGenres;

    public GamesByGenresResponse() {
    }

    public GamesByGenresResponse(Map<Genre, List<UsersGame>> gamesByGenres) {
        this.gamesByGenres = gamesByGenres;
    }

    public Map<Genre, List<UsersGame>> getGamesByGenres() {
        return gamesByGenres;
    }

    public void setGamesByGenres(Map<Genre, List<UsersGame>> gamesByGenres) {
        this.gamesByGenres = gamesByGenres;
    }
}
