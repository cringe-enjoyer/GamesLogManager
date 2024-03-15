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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private GamesList list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public LocalDateTime getUserTime() {
        return userTime;
    }

    public void setUserTime(LocalDateTime userTime) {
        this.userTime = userTime;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GamesList getList() {
        return list;
    }

    public void setList(GamesList list) {
        this.list = list;
    }
}
