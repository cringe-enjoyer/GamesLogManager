package ru.example.gameslogmanager.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Developer}
 */
public class DeveloperDTO implements Serializable {
    private String name;
    private String description;

    public DeveloperDTO() {
    }

    public DeveloperDTO(String name, String description) {
        this.name = name;
        this.description = description;
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
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "description = " + description + ")";
    }
}