package ru.example.gameslogmanager.dto;

/**
 * DTO для пользователя без конфиденциальных данных
 */
public class FriendDTO {
    private String email;
    private String steamId;
    private String nickname;

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
