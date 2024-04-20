package ru.example.gameslogmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "platforms")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "platform", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<UsersGame> usersGames;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}