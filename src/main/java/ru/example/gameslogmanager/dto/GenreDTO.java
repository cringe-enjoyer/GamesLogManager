package ru.example.gameslogmanager.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Genre}
 */
public class GenreDTO implements Serializable {
    @Size(max = 150)
    private final String name;
    private final Set<GameDTO> games;

    public GenreDTO(String name, Set<GameDTO> games) {
        this.name = name;
        this.games = games;
    }

    public String getName() {
        return name;
    }

    public Set<GameDTO> getGames() {
        return games;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "games = " + games + ")";
    }
}