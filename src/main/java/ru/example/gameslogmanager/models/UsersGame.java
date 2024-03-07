package ru.example.gameslogmanager.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_game")
public class UsersGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usersgame_id")
    private int id;

    @Column(name = "user_rating")
    private int userRating;

    @Column(name = "user_time")
    //TODO: посмотреть как лучше время хранить
    private LocalDateTime userTime;

    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "user_note")
    private String userNote;

    @ManyToOne
    @JoinColumn(name = "game", referencedColumnName = "game_id")
    private Game game;
}
