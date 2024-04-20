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
    private Integer id;

    @NotNull(message = "User should not be null")
    private Integer userId;

    private Integer friendId;

    @NotNull(message = "Status should not be null")
    private FriendStatus status;

    public FriendsListDTO() {
    }

    public FriendsListDTO(Integer id, Integer userId, Integer friendId, FriendStatus status) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FriendsListDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendId=" + friendId +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendsListDTO that = (FriendsListDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(friendId, that.friendId) && status == that.status;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(userId);
        result = 31 * result + Objects.hashCode(friendId);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }
}