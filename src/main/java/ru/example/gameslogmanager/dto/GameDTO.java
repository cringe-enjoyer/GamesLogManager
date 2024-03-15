package ru.example.gameslogmanager.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link ru.example.gameslogmanager.models.Game}
 */
public class GameDTO implements Serializable {
    private String title;
    private String genre;
    private String description;
    private DeveloperDTO developer;
    private PublisherDTO publisher;
    private double rating;
    private double avgTime;

    public GameDTO() {
    }

    public GameDTO(String title, String genre, String description, DeveloperDTO developer, PublisherDTO publisher, double rating, double avgTime) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.developer = developer;
        this.publisher = publisher;
        this.rating = rating;
        this.avgTime = avgTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DeveloperDTO getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperDTO developer) {
        this.developer = developer;
    }

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDTO entity = (GameDTO) o;
        return Objects.equals(this.title, entity.title) &&
                Objects.equals(this.genre, entity.genre) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.developer, entity.developer) &&
                Objects.equals(this.publisher, entity.publisher) &&
                Objects.equals(this.rating, entity.rating) &&
                Objects.equals(this.avgTime, entity.avgTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, description, developer, publisher, rating, avgTime);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "genre = " + genre + ", " +
                "description = " + description + ", " +
                "developer = " + developer + ", " +
                "publisher = " + publisher + ", " +
                "rating = " + rating + ", " +
                "avgTime = " + avgTime + ")";
    }
}