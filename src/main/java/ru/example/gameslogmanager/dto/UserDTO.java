package ru.example.gameslogmanager.dto;

public class UserDTO {
    private String login;
    private String password;
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
}
