package ru.example.gameslogmanager.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Developer}
 */
public class DeveloperDTO implements Serializable {
    private String name;
    private String description;
    private List<GameDTO> games = new ArrayList<>();

    public DeveloperDTO() {
    }

    public DeveloperDTO(String name, String description,
                        List<GameDTO> games) {
        this.name = name;
        this.description = description;
        this.games = games;
    }

    public DeveloperDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GameDTO> getGames() {
        return games;
    }

    public void setGames(List<GameDTO> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperDTO entity = (DeveloperDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "DeveloperDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", games=" + games +
                '}';
    }
}