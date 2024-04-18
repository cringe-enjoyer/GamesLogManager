package ru.example.gameslogmanager.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Genre}
 */
public class GenreDTO implements Serializable {
    @Size(max = 150)
    private String name;

    public GenreDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", ";
    }
}