package ru.example.gameslogmanager.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Game}
 */
public class GameDTO implements Serializable {
    private Integer id;
    private String title;
    private Set<GenreDTO> genres;
    private String description;
    private Set<Integer> developersId;
    private Set<Integer> publishersId;
    private double rating;
    private double avgTime;
    private String shortDescription;
    private String imageUrl;
    private Integer steamId;
    private String smallImgUrl;
    private Boolean verified;
    private LocalDate realiseDate;
    private String backgroundImgUrl;


    public GameDTO() {
    }

    public GameDTO(Integer id, String title, Set<GenreDTO> genres, String description, Set<Integer> developersId,
                   Set<Integer> publishersId, double rating, double avgTime, String shortDescription, String imageUrl,
                   Integer steamId, String smallImgUrl, Boolean verified, LocalDate realiseDate,
                   String backgroundImgUrl) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.description = description;
        this.developersId = developersId;
        this.publishersId = publishersId;
        this.rating = rating;
        this.avgTime = avgTime;
        this.shortDescription = shortDescription;
        this.imageUrl = imageUrl;
        this.steamId = steamId;
        this.smallImgUrl = smallImgUrl;
        this.verified = verified;
        this.realiseDate = realiseDate;
        this.backgroundImgUrl = backgroundImgUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(double avgTime) {
        this.avgTime = avgTime;
    }

    public Set<Integer> getDevelopersId() {
        return developersId;
    }

    public void setDevelopersId(Set<Integer> developersId) {
        this.developersId = developersId;
    }

    public Set<Integer> getPublishersId() {
        return publishersId;
    }

    public void setPublishersId(Set<Integer> publishersId) {
        this.publishersId = publishersId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSteamId() {
        return steamId;
    }

    public void setSteamId(Integer steamId) {
        this.steamId = steamId;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    public void setSmallImgUrl(String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public LocalDate getRealiseDate() {
        return realiseDate;
    }

    public void setRealiseDate(LocalDate realiseDate) {
        this.realiseDate = realiseDate;
    }

    public String getBackgroundImgUrl() {
        return backgroundImgUrl;
    }

    public void setBackgroundImgUrl(String backgroundImgUrl) {
        this.backgroundImgUrl = backgroundImgUrl;
    }

    @Override
    public String toString() {
        return "GameDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genres=" + genres +
                ", description='" + description + '\'' +
                ", developersId=" + developersId +
                ", publishersId=" + publishersId +
                ", rating=" + rating +
                ", avgTime=" + avgTime +
                ", shortDescription='" + shortDescription + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", steamId=" + steamId +
                ", smallImgUrl='" + smallImgUrl + '\'' +
                ", verified=" + verified +
                ", realiseDate=" + realiseDate +
                ", backgroundImgUrl='" + backgroundImgUrl + '\'' +
                '}';
    }
}