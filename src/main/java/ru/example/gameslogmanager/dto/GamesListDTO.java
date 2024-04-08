package ru.example.gameslogmanager.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link ru.example.gameslogmanager.models.GamesList}
 */
public class GamesListDTO implements Serializable {
    private String name;
    private UserDTO user;
    private List<UsersGameDTO> usersGamesList;
    private long gamesCount;

    public GamesListDTO() {
    }

    public GamesListDTO(String name, UserDTO user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "user = " + user + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesListDTO that = (GamesListDTO) o;

        if (gamesCount != that.gamesCount) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(usersGamesList, that.usersGamesList);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (usersGamesList != null ? usersGamesList.hashCode() : 0);
        result = 31 * result + (int) (gamesCount ^ (gamesCount >>> 32));
        return result;
    }
}