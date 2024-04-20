package ru.example.gameslogmanager.dto;

import java.util.List;
import java.util.Map;

public class GamesByGenresResponse {
    private Map<GenreDTO, List<UsersGameDTO>> gamesByGenres;

    public GamesByGenresResponse() {
    }

    public GamesByGenresResponse(Map<GenreDTO, List<UsersGameDTO>> gamesByGenres) {
        this.gamesByGenres = gamesByGenres;
    }

    public Map<GenreDTO, List<UsersGameDTO>> getGamesByGenres() {
        return gamesByGenres;
    }

    public void setGamesByGenres(Map<GenreDTO, List<UsersGameDTO>> gamesByGenres) {
        this.gamesByGenres = gamesByGenres;
    }
}
