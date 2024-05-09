package ru.example.gameslogmanager.dto;

public class SteamImportRequest {
    private UserDTO userDTO;

    private String listName;

    public SteamImportRequest() {
    }

    public SteamImportRequest(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public SteamImportRequest(UserDTO userDTO, String listName) {
        this.userDTO = userDTO;
        this.listName = listName;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
