package ru.example.gameslogmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Класс выделяющий основную информацию из данных об игре
 * <a href="https://store.steampowered.com/api/appdetails?appids={appId}">
 *     https://store.steampowered.com/api/appdetails?appids={appId}
 *     <a/>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SteamGameInfoDTO implements Serializable {
    @JsonProperty("steam_appid")
    private int steamAppid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("is_free")
    private boolean isFree;
    @JsonProperty("detailed_description")
    private String detailedDescription;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("header_image")
    private String headerImage;
    @JsonProperty("capsule_image")
    private String capsuleImage;
    @JsonProperty("developers")
    private List<String> developers;
    @JsonProperty("publishers")
    private List<String> publishers;
    @JsonProperty("genres")
    private List<SteamGameGenreDTO> genres;

    @JsonProperty("type")
    private String type;

    @JsonProperty("about_the_game")
    private String aboutTheGame;

    @JsonProperty("release_date")
    private ReleaseDateDTO releaseDate;

    @JsonProperty("background_raw")
    private String backgroundRaw;

    public int getSteamAppid() {
        return steamAppid;
    }

    public void setSteamAppid(int steamAppid) {
        this.steamAppid = steamAppid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getCapsuleImage() {
        return capsuleImage;
    }

    public void setCapsuleImage(String capsuleImage) {
        this.capsuleImage = capsuleImage;
    }

    public List<String> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<String> developers) {
        this.developers = developers;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public List<SteamGameGenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<SteamGameGenreDTO> genres) {
        this.genres = genres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAboutTheGame() {
        return aboutTheGame;
    }

    public void setAboutTheGame(String aboutTheGame) {
        this.aboutTheGame = aboutTheGame;
    }

    public ReleaseDateDTO getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(ReleaseDateDTO releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackgroundRaw() {
        return backgroundRaw;
    }

    public void setBackgroundRaw(String backgroundRaw) {
        this.backgroundRaw = backgroundRaw;
    }

    @Override
    public String toString() {
        return "SteamGameInfo{" +
                "steamAppid=" + steamAppid +
                ", name='" + name + '\'' +
                ", isFree=" + isFree +
                ", detailedDescription='" + detailedDescription + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", headerImage='" + headerImage + '\'' +
                ", capsuleImage='" + capsuleImage + '\'' +
                ", developers='" + developers + '\'' +
                ", publishers='" + publishers + '\'' +
                '}';
    }
}
