package ru.example.gameslogmanager.dto;

import jakarta.validation.constraints.Size;
import ru.example.gameslogmanager.models.UsersGame;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Platform}
 */
public class PlatformDTO implements Serializable {
    @Size(max = 100)
    private String name;
    private Set<UsersGame> usersGames = new LinkedHashSet<>();

    public PlatformDTO() {
    }

    public PlatformDTO(String name, Set<UsersGame> usersGames) {
        this.name = name;
        this.usersGames = usersGames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UsersGame> getUsersGames() {
        return usersGames;
    }

    public void setUsersGames(Set<UsersGame> usersGames) {
        this.usersGames = usersGames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformDTO entity = (PlatformDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.usersGames, entity.usersGames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, usersGames);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "usersGames = " + usersGames + ")";
    }
}