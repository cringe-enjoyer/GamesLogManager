package ru.example.gameslogmanager.dto;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Publisher}
 */
public class PublisherDTO implements Serializable {
    private String name;
    private String description;
    private Set<GameDTO> games = new LinkedHashSet<>();


    public PublisherDTO() {
    }

    public PublisherDTO(String name, String description,
                        Set<GameDTO> games) {
        this.name = name;
        this.description = description;
        this.games = games;
    }

    public PublisherDTO(String name) {
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

    public Set<GameDTO> getGames() {
        return games;
    }

    public void setGames(Set<GameDTO> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublisherDTO entity = (PublisherDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "PublisherDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", games=" + games +
                '}';
    }
}