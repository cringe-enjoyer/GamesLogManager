package ru.example.gameslogmanager.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ru.example.gameslogmanager.models.UsersGame}
 */
public class UsersGameDTO implements Serializable {
    private final int userRating;
    private final LocalDateTime userTime;
    private final String userComment;
    private final String userNote;
    private final GameDTO game;

    public UsersGameDTO(int userRating, LocalDateTime userTime, String userComment, String userNote, GameDTO game) {
        this.userRating = userRating;
        this.userTime = userTime;
        this.userComment = userComment;
        this.userNote = userNote;
        this.game = game;
    }

    public int getUserRating() {
        return userRating;
    }

    public LocalDateTime getUserTime() {
        return userTime;
    }

    public String getUserComment() {
        return userComment;
    }

    public String getUserNote() {
        return userNote;
    }

    public GameDTO getGame() {
        return game;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "userRating = " + userRating + ", " +
                "userTime = " + userTime + ", " +
                "userComment = " + userComment + ", " +
                "userNote = " + userNote + ", " +
                "game = " + game + ")";
    }
}