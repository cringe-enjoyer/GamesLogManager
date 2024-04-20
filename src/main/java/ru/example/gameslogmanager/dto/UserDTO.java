package ru.example.gameslogmanager.dto;

import jakarta.validation.constraints.Email;

import java.util.Objects;

/**
 * DTO for {@link ru.example.gameslogmanager.models.User User}
 */
public class UserDTO {
    private String login;
    //TODO: На любой запрос можно получить пароль пользователя
    private String password;
    @Email
    private String email;
    private String steamId;
    private String nickname;

    public UserDTO() {
    }

    public UserDTO(String login, String password, String email, String steamId, String nickname) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.steamId = steamId;
        this.nickname = nickname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (!Objects.equals(login, userDTO.login)) return false;
        if (!Objects.equals(password, userDTO.password)) return false;
        if (!Objects.equals(email, userDTO.email)) return false;
        if (!Objects.equals(steamId, userDTO.steamId)) return false;
        return Objects.equals(nickname, userDTO.nickname);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (steamId != null ? steamId.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        return result;
    }
}
