package ru.example.gameslogmanager.dto;

import com.lukaspradel.steamapi.data.json.playerachievements.Achievement;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link ru.example.gameslogmanager.models.UsersGame}
 */
public class UsersGameDTO implements Serializable {
    private int userRating;
    private long userTime;
    private String userComment;
    private String userNote;
    private GameDTO game;
    private GamesListDTO list;
    private LocalDate dateAdded;
    private List<Achievement> achievements;


    public UsersGameDTO() {
    }

    public UsersGameDTO(int userRating, long userTime, String userComment, String userNote, GameDTO game,
                        GamesListDTO list, LocalDate dateAdded) {
        this.userRating = userRating;
        this.userTime = userTime;
        this.userComment = userComment;
        this.userNote = userNote;
        this.game = game;
        this.list = list;
        this.dateAdded = dateAdded;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public long getUserTime() {
        return userTime;
    }

    public void setUserTime(long userTime) {
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

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    public GamesListDTO getList() {
        return list;
    }

    public void setList(GamesListDTO list) {
        this.list = list;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @Override
    public String toString() {
        return "UsersGameDTO{" +
                "userRating=" + userRating +
                ", userTime=" + userTime +
                ", userComment='" + userComment + '\'' +
                ", userNote='" + userNote + '\'' +
                ", game=" + game +
                ", list=" + list +
                ", dateAdded=" + dateAdded +
                '}';
    }
}