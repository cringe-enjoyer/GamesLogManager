package ru.example.gameslogmanager.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "games_list_users_game")
public class GamesListUsersGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private GamesList list;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_game")
    private UsersGame usersGame;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GamesList getList() {
        return list;
    }

    public void setList(GamesList list) {
        this.list = list;
    }

    public UsersGame getUsersGame() {
        return usersGame;
    }

    public void setUsersGame(UsersGame usersGame) {
        this.usersGame = usersGame;
    }

}