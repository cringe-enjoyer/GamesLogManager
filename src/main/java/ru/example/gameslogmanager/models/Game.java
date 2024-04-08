package ru.example.gameslogmanager.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
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

    @Column(name = "image_url", length = Integer.MAX_VALUE)
    private String imageUrl;

    @Column(name = "steam_id")
    private Integer steamId;

    @Column(name = "small_img_url", length = Integer.MAX_VALUE)
    private String smallImgUrl;

    @Column(name = "background_img_url")
    private String backgroundImgUrl;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "realise_date")
    private LocalDate realiseDate;

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

    public Game(String title, String description, double rating, double avgTime, String shortDescription, String imageUrl,
                Integer steamId, Set<Developer> developers, Set<Publisher> publishers, Set<Genre> genres) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.avgTime = avgTime;
        this.shortDescription = shortDescription;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public LocalDate getRealiseDate() {
        return realiseDate;
    }

    public void setRealiseDate(LocalDate realiseDate) {
        this.realiseDate = realiseDate;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    public void setSmallImgUrl(String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }

    public String getBackgroundImgUrl() {
        return backgroundImgUrl;
    }

    public void setBackgroundImgUrl(String backgroundImgUrl) {
        this.backgroundImgUrl = backgroundImgUrl;
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
                ", imageUrl='" + imageUrl + '\'' +
                ", steamId=" + steamId +
                ", smallImgUrl='" + smallImgUrl + '\'' +
                ", backgroundImgUrl='" + backgroundImgUrl + '\'' +
                ", verified=" + verified +
                ", realiseDate=" + realiseDate +
                ", developers=" + developers +
                ", publishers=" + publishers +
                ", genres=" + genres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;
        return id == game.id && Double.compare(rating, game.rating) == 0 && Double.compare(avgTime, game.avgTime) == 0 && Objects.equals(title, game.title) && Objects.equals(description, game.description) && Objects.equals(shortDescription, game.shortDescription) && Objects.equals(imageUrl, game.imageUrl) && Objects.equals(steamId, game.steamId) && Objects.equals(smallImgUrl, game.smallImgUrl) && Objects.equals(backgroundImgUrl, game.backgroundImgUrl) && Objects.equals(verified, game.verified) && Objects.equals(realiseDate, game.realiseDate) && Objects.equals(developers, game.developers) && Objects.equals(publishers, game.publishers) && Objects.equals(genres, game.genres);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Double.hashCode(rating);
        result = 31 * result + Double.hashCode(avgTime);
        result = 31 * result + Objects.hashCode(shortDescription);
        result = 31 * result + Objects.hashCode(imageUrl);
        result = 31 * result + Objects.hashCode(steamId);
        result = 31 * result + Objects.hashCode(smallImgUrl);
        result = 31 * result + Objects.hashCode(backgroundImgUrl);
        result = 31 * result + Objects.hashCode(verified);
        result = 31 * result + Objects.hashCode(realiseDate);
        result = 31 * result + Objects.hashCode(developers);
        result = 31 * result + Objects.hashCode(publishers);
        result = 31 * result + Objects.hashCode(genres);
        return result;
    }
}
