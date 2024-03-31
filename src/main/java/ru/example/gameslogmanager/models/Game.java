package ru.example.gameslogmanager.models;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private double rating;

    @Column(name = "average_time")
    private double avgTime;

    @Column(name = "short_description", length = Integer.MAX_VALUE)
    private String shortDescription;

    @Column(name = "image", length = Integer.MAX_VALUE)
    private String image;

    @Column(name = "steam_id")
    private Integer steamId;

    //TODO: Протестировать сохранение
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "game_developer",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "dev_id"))
    private Set<Developer> developers;

    //TODO: Протестировать сохранение
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "game_publisher",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    private Set<Publisher> publishers;

    @ManyToMany
    @JoinTable(name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    public Game() {
    }

    public Game(String title) {
        this.title = title;
    }

    public Game(String title, String description, double rating, double avgTime, String shortDescription, String image,
                Integer steamId, Set<Developer> developers, Set<Publisher> publishers, Set<Genre> genres) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.avgTime = avgTime;
        this.shortDescription = shortDescription;
        this.image = image;
        this.steamId = steamId;
        this.developers = developers;
        this.publishers = publishers;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getSteamId() {
        return steamId;
    }

    public void setSteamId(Integer steamId) {
        this.steamId = steamId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", avgTime=" + avgTime +
                ", shortDescription='" + shortDescription + '\'' +
                ", image='" + image + '\'' +
                ", steamId=" + steamId +
                ", developers=" + developers +
                ", publishers=" + publishers +
                ", genres=" + genres +
                '}';
    }
}
