package ru.example.gameslogmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "steam_id")
    private String steamId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "friend", orphanRemoval = true)
    private Set<FriendsList> friendsLists = new LinkedHashSet<>();

    public Set<FriendsList> getFriendsLists() {
        return friendsLists;
    }

    public void setFriendsLists(Set<FriendsList> friendsLists) {
        this.friendsLists = friendsLists;
    }

    public User() {
    }

    public User(String login, String password, String email, String steamId, String nickname) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.steamId = steamId;
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = this.userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(steamId, user.steamId)) return false;
        if (!Objects.equals(nickname, user.nickname)) return false;
        if (!Objects.equals(role, user.role)) return false;
        return Objects.equals(friendsLists, user.friendsLists);
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (steamId != null ? steamId.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (friendsLists != null ? friendsLists.hashCode() : 0);
        return result;
    }
}
