package ru.example.gameslogmanager.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users_game")
public class UsersGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usersgame_id")
    private int id;

    @Column(name = "user_rating")
    private int userRating;

    /**
     * В минутах
     */
    @Column(name = "user_time")
    private Long userTime;

    @Column(name = "user_review")
    private String userReview;

    @Column(name = "user_note")
    private String userNote;

    @ManyToOne
    @JoinColumn(name = "game", referencedColumnName = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "list_id")
    private GamesList list;

    //TODO: Заменить на completeDate
    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "completion_percent")
    private Integer completionPercent;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "platform")
    private Platform platform;

    @Column(name = "public_review")
    private boolean publicReview;

    @Column(name = "from_steam")
    private Boolean fromSteam;

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

    public Long getUserTime() {
        return userTime;
    }

    public void setUserTime(Long userTime) {
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

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Integer getCompletionPercent() {
        return completionPercent;
    }

    public void setCompletionPercent(Integer completionPercent) {
        this.completionPercent = completionPercent;
    }

    public boolean getPublicReview() {
        return publicReview;
    }

    public void setPublicReview(boolean publicReview) {
        this.publicReview = publicReview;
    }

    public Boolean getFromSteam() {
        return fromSteam;
    }

    public void setFromSteam(Boolean fromSteam) {
        this.fromSteam = fromSteam;
    }

    @Override
    public String toString() {
        return "UsersGame{" +
                "id=" + id +
                ", userRating=" + userRating +
                ", userTime=" + userTime +
                ", userReview='" + userReview + '\'' +
                ", userNote='" + userNote + '\'' +
                ", game=" + game +
                ", list=" + list +
                ", dateAdded=" + dateAdded +
                ", completionPercent=" + completionPercent +
                ", updateDate=" + updateDate +
                ", platform=" + platform +
                ", publicReview=" + publicReview +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersGame usersGame = (UsersGame) o;
        return id == usersGame.id && userRating == usersGame.userRating && publicReview == usersGame.publicReview && Objects.equals(userTime, usersGame.userTime) && Objects.equals(userReview, usersGame.userReview) && Objects.equals(userNote, usersGame.userNote) && Objects.equals(game, usersGame.game) && Objects.equals(list, usersGame.list) && Objects.equals(dateAdded, usersGame.dateAdded) && Objects.equals(completionPercent, usersGame.completionPercent) && Objects.equals(updateDate, usersGame.updateDate) && Objects.equals(platform, usersGame.platform) && Objects.equals(fromSteam, usersGame.fromSteam);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userRating;
        result = 31 * result + Objects.hashCode(userTime);
        result = 31 * result + Objects.hashCode(userReview);
        result = 31 * result + Objects.hashCode(userNote);
        result = 31 * result + Objects.hashCode(game);
        result = 31 * result + Objects.hashCode(list);
        result = 31 * result + Objects.hashCode(dateAdded);
        result = 31 * result + Objects.hashCode(completionPercent);
        result = 31 * result + Objects.hashCode(updateDate);
        result = 31 * result + Objects.hashCode(platform);
        result = 31 * result + Boolean.hashCode(publicReview);
        result = 31 * result + Objects.hashCode(fromSteam);
        return result;
    }
}
