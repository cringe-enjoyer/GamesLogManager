package ru.example.gameslogmanager.dto;

import com.lukaspradel.steamapi.data.json.playerachievements.Achievement;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link ru.example.gameslogmanager.models.UsersGame}
 */
public class UsersGameDTO implements Serializable {
    private int userRating;
    private long userTime;
    private String userReview;
    private String userNote;
    private GameDTO game;
    private GamesListDTO list;
    private LocalDate dateAdded;
    private Integer completionPercent;
    private LocalDate updateDate;
    private PlatformDTO platform;
    private boolean publicReview;
    private List<Achievement> achievements;

    public UsersGameDTO() {
    }

    public UsersGameDTO(int userRating, long userTime, String userReview, String userNote, GameDTO game,
                        GamesListDTO list, LocalDate dateAdded) {
        this.userRating = userRating;
        this.userTime = userTime;
        this.userReview = userReview;
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

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
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
    public Integer getCompletionPercent() {
        return completionPercent;
    }

    public void setCompletionPercent(Integer completionPercent) {
        this.completionPercent = completionPercent;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public PlatformDTO getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformDTO platform) {
        this.platform = platform;
    }

    public boolean isPublicReview() {
        return publicReview;
    }

    public void setPublicReview(boolean publicReview) {
        this.publicReview = publicReview;
    }

    @Override
    public String toString() {
        return "UsersGameDTO{" +
                "userRating=" + userRating +
                ", userTime=" + userTime +
                ", userReview='" + userReview + '\'' +
                ", userNote='" + userNote + '\'' +
                ", game=" + game +
                ", list=" + list +
                ", dateAdded=" + dateAdded +
                ", completionPercent=" + completionPercent +
                ", updateDate=" + updateDate +
                ", platform=" + platform +
                ", achievements=" + achievements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersGameDTO that = (UsersGameDTO) o;
        return userRating == that.userRating && userTime == that.userTime && publicReview == that.publicReview && Objects.equals(userReview, that.userReview) && Objects.equals(userNote, that.userNote) && Objects.equals(game, that.game) && Objects.equals(list, that.list) && Objects.equals(dateAdded, that.dateAdded) && Objects.equals(completionPercent, that.completionPercent) && Objects.equals(updateDate, that.updateDate) && Objects.equals(platform, that.platform) && Objects.equals(achievements, that.achievements);
    }

    @Override
    public int hashCode() {
        int result = userRating;
        result = 31 * result + Long.hashCode(userTime);
        result = 31 * result + Objects.hashCode(userReview);
        result = 31 * result + Objects.hashCode(userNote);
        result = 31 * result + Objects.hashCode(game);
        result = 31 * result + Objects.hashCode(list);
        result = 31 * result + Objects.hashCode(dateAdded);
        result = 31 * result + Objects.hashCode(completionPercent);
        result = 31 * result + Objects.hashCode(updateDate);
        result = 31 * result + Objects.hashCode(platform);
        result = 31 * result + Boolean.hashCode(publicReview);
        result = 31 * result + Objects.hashCode(achievements);
        return result;
    }
}