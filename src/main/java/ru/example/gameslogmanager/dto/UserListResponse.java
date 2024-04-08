package ru.example.gameslogmanager.dto;

import java.util.List;

public class UserListResponse {
    private List<UserDTO> usersDTO;

    public UserListResponse() {
    }

    public UserListResponse(List<UserDTO> usersDTO) {
        this.usersDTO = usersDTO;
    }

    public List<UserDTO> getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(List<UserDTO> usersDTO) {
        this.usersDTO = usersDTO;
    }
}
