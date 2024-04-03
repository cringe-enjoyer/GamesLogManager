package ru.example.gameslogmanager.dto;

import jakarta.validation.constraints.NotNull;
import ru.example.gameslogmanager.models.FriendStatus;
import ru.example.gameslogmanager.models.FriendsList;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link FriendsList}
 */
public class FriendsListDTO implements Serializable {
    @NotNull(message = "User should not be null")
    private UserDTO user;
    private UserDTO friend;
    @NotNull(message = "Status should not be null")
    private FriendStatus status;

    public FriendsListDTO(UserDTO user, UserDTO friend, FriendStatus status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getFriend() {
        return friend;
    }

    public void setFriend(UserDTO friend) {
        this.friend = friend;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "user = " + user + ", " +
                "friend = " + friend + ", " +
                "status = " + status + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendsListDTO that = (FriendsListDTO) o;

        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(friend, that.friend)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (friend != null ? friend.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}