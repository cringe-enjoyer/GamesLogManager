package ru.example.gameslogmanager.dto;

import java.util.List;

public class UserListResponse {
    private List<FriendDTO> friendDTO;

    public UserListResponse() {
    }

    public UserListResponse(List<FriendDTO> friendDTO) {
        this.friendDTO = friendDTO;
    }

    public List<FriendDTO> getFriendDTO() {
        return friendDTO;
    }

    public void setFriendDTO(List<FriendDTO> friendDTO) {
        this.friendDTO = friendDTO;
    }
}
