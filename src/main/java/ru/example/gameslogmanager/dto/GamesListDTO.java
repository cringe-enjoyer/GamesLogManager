package ru.example.gameslogmanager.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link ru.example.gameslogmanager.models.GamesList}
 */
public class GamesListDTO implements Serializable {
    private Integer id;
    private String name;
    private Integer userId;
    private List<UsersGameDTO> usersGamesList;
    private boolean isDefault;

    public GamesListDTO() {
    }

    public GamesListDTO(Integer id, String name, Integer userId, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.isDefault = isDefault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", usersGamesList=" + usersGamesList +
                ", isDefault=" + isDefault +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesListDTO that = (GamesListDTO) o;
        return isDefault == that.isDefault && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(userId, that.userId) && Objects.equals(usersGamesList, that.usersGamesList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(userId);
        result = 31 * result + Objects.hashCode(usersGamesList);
        result = 31 * result + Boolean.hashCode(isDefault);
        return result;
    }
}