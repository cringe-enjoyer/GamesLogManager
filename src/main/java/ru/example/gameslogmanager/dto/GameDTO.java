package ru.example.gameslogmanager.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Game}
 */
public class GameDTO implements Serializable {
    private String title;
    private Set<GenreDTO> genres;
    private String description;
    private Set<DeveloperDTO> developers;
    private Set<PublisherDTO> publishers;
    private double rating;
    private double avgTime;
    private String shortDescription;
    private String image;
    private Integer steamId;


    public GameDTO() {
    }

    public GameDTO(String title, Set<GenreDTO> genres, String description, Set<DeveloperDTO> developers,
                   Set<PublisherDTO> publishers, double rating, double avgTime,
                   String shortDescription, String image, Integer steamId) {
        this.title = title;
        this.genres = genres;
        this.description = description;
        this.developers = developers;
        this.publishers = publishers;
        this.rating = rating;
        this.avgTime = avgTime;
        this.shortDescription = shortDescription;
        this.image = image;
        this.steamId = steamId;
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

    public Set<DeveloperDTO> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperDTO> developers) {
        this.developers = developers;
    }

    public Set<PublisherDTO> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<PublisherDTO> publishers) {
        this.publishers = publishers;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSteamId() {
        return steamId;
    }

    public void setSteamId(Integer steamId) {
        this.steamId = steamId;
    }

    @Override
    public String toString() {
        return "GameDTO{" +
                "title='" + title + '\'' +
                ", genres=" + genres +
                ", description='" + description + '\'' +
                ", developers=" + developers +
                ", publishers=" + publishers +
                ", rating=" + rating +
                ", avgTime=" + avgTime +
                ", shortDescription='" + shortDescription + '\'' +
                ", image='" + image + '\'' +
                ", steamId=" + steamId +
                '}';
    }
}