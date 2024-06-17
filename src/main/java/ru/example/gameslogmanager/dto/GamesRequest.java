package ru.example.gameslogmanager.dto;

import com.lukaspradel.steamapi.data.json.ownedgames.Game;

import java.util.List;

public class GamesRequest {
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
