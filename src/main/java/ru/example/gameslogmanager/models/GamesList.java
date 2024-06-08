package ru.example.gameslogmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "games_list")
public class GamesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private int id;

    @Column(name = "name")
    private String name;

    @NotNull(message = "User should not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"user\"")
    private User user;

    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UsersGame> usersGames;

    @Column(name = "default_list")
    private boolean isDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UsersGame> getUsersGames() {
        return usersGames;
    }

    public void setUsersGames(List<UsersGame> usersGames) {
        this.usersGames = usersGames;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesList gamesList = (GamesList) o;
        return id == gamesList.id && Objects.equals(name, gamesList.name) && Objects.equals(user, gamesList.user) && Objects.equals(usersGames, gamesList.usersGames);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(user);
        result = 31 * result + Objects.hashCode(usersGames);
        return result;
    }
}