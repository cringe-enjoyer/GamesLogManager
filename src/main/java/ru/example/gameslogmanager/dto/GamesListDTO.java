package ru.example.gameslogmanager.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link ru.example.gameslogmanager.models.GamesList}
 */
public class GamesListDTO implements Serializable {
    private String name;
    private Integer userId;
    private List<UsersGameDTO> usersGamesList;

    public GamesListDTO() {
    }

    public GamesListDTO(String name, Integer userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<UsersGameDTO> getUsersGamesList() {
        return usersGamesList;
    }

    public void setUsersGamesList(List<UsersGameDTO> usersGamesList) {
        this.usersGamesList = usersGamesList;
    }

    public long getGamesCount() {
        return usersGamesList.size();
    }

    @Override
    public String toString() {
        return "GamesListDTO{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", usersGamesList=" + usersGamesList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesListDTO that = (GamesListDTO) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(usersGamesList, that.usersGamesList);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (usersGamesList != null ? usersGamesList.hashCode() : 0);
        return result;
    }
}